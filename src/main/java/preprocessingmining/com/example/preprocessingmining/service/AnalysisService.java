package preprocessingmining.com.example.preprocessingmining.service;


import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.apache.spark.api.java.function.ForeachFunction;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.api.java.function.ReduceFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import preprocessingmining.com.example.preprocessingmining.model.*;
import preprocessingmining.com.example.preprocessingmining.model.DTO.AnalyseDate;
import preprocessingmining.com.example.preprocessingmining.model.DTO.UpdateValue;
import preprocessingmining.com.example.preprocessingmining.util.DataUtil;
import preprocessingmining.com.example.preprocessingmining.util.similarity.Jaccard;
import scala.collection.JavaConverters;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class AnalysisService {
    @Autowired
    private DataOfProjectService dataOfProjectService;
    @Autowired
    private Jaccard jaccard;
    @Autowired
    private Gson gson;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private FildsService fildsService;
    @Autowired
    private FileAnalysisService fileAnalysisService;

    @Async
    public void analysis(@NotNull String projectId) {
        final var dataOfProjects = dataOfProjectService.listByProjectId(projectId);
        var analysis = new Analysis();
        dataOfProjects.forEach(dataOfProject -> {
            SparkSession session = SparkSession.builder().appName("join").master("local[*]").getOrCreate();

            try {
                writeBytesToFile("./temp.csv", dataOfProject.getFile().array());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Dataset<Row> table = session.read().format("csv").option("header", "true").load("./temp.csv");
            final var columns = table.columns();
            var headers = table.columns();
            var headers_combinations = Sets.combinations(Set.of(headers), 2);
            var tableAnalysis = new TableAnalysis();
            tableAnalysis.setName(dataOfProject.getName());
            for (String column : columns) {
                final var values = table.select(column).distinct().as(Encoders.STRING()).collectAsList();
                var columnAnalysis = new ColumnAnalysis();
                columnAnalysis.setName(column);
                for (String baseValue : values) {
                    for (String analysisValue : values) {
                        if (baseValue != null && analysisValue != null) {
                            final var result = jaccard.calculateD(new StringBuilder(baseValue),
                                    new StringBuilder(analysisValue));
                            columnAnalysis.addCorrelationValues(baseValue + ":" + analysisValue, result);
                        }
                    }
                }
                tableAnalysis.addValueColumns(columnAnalysis);
            }
            headers_combinations.forEach(headers_combination -> {
                final var headers_name = headers_combination.toArray();
                final var result = jaccard.calculateD(new StringBuilder(headers_name[0].toString()),
                        new StringBuilder(headers_name[1].toString()));
                tableAnalysis.addCorrelationColumns(headers_name[0].toString() + ":" + headers_name[1].toString(), result);
            });

            analysis.addTable(tableAnalysis);
        });
        final var fileAnalysis = new FileAnalysis();
        fileAnalysis.setId(UUID.randomUUID().toString());
        fileAnalysis.setProject_id(projectId);
        fileAnalysis.setType(TypeAnalysis.Activitys);
        fileAnalysis.setAnalysis(gson.toJson(analysis));
        fileAnalysisService.save(fileAnalysis);
    }

    public void applyActivitys(@NotNull String projectId) {
        var fileAnalysis = fileAnalysisService.findByProjectId(projectId, TypeAnalysis.Activitys);
        var analysis = gson.fromJson(fileAnalysis.getAnalysis(), Analysis.class);
        analysis.getTables().forEach(tableAnalysis -> {
            var name = tableAnalysis.getName();
            tableAnalysis.getCorrelationColumns().forEach((key, value) -> {
                if (value > 0.9) {
                    mergeColumns(projectId, name, key.split(":"));
                }
            });
        });
    }

    private static void writeBytesToFile(String fileOutput, byte[] bytes)
            throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileOutput)) {
            fos.write(bytes);
        }
    }

    public void fixData(@NotNull String projectId) {//FIXME: Save on database
        final var dataOfProjects = dataOfProjectService.listByProjectId(projectId);

        List<Field> fields = projectService.getFilds(projectId);

        dataOfProjects.forEach(dataOfProject -> {
            SparkSession session = SparkSession.builder().appName("join").master("local[*]").getOrCreate();

            try {
                writeBytesToFile("./temp.csv", dataOfProject.getFile().array());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Dataset<Row> table = session.read().format("csv").option("header", "true").load("./temp.csv");
            fields.forEach(field -> {
                if (dataOfProject.getId().equals(field.getFile_id())) {
                    final var columnName = field.getField();
                    final var table1 = table.map((MapFunction<Row, Row>) row -> {
                        final var index = row.fieldIndex(columnName);
                        var rowToList = JavaConverters.seqAsJavaList(row.toSeq()).toArray(); // ROW ->  List
                        final var tranform = DataUtil.tranform((String) rowToList[index]);
                        rowToList[index] = tranform;
                        return RowFactory.create(rowToList);
                    }, table.encoder());
                }
            });
        });
    }

    public void removeColumn(@NotNull String projectId, @NotNull String comlumnName) {//FIXME: Save on database
        final var dataOfProjects = dataOfProjectService.listByProjectId(projectId);
        dataOfProjects.forEach(dataOfProject -> {
            SparkSession session = SparkSession.builder().appName("join").master("local[*]").getOrCreate();

            try {
                writeBytesToFile("./temp.csv", dataOfProject.getFile().array());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Dataset<Row> table = session.read().format("csv").option("header", "true").load("./temp.csv");
            table.drop(comlumnName);

        });
    }

    public void mergeColumns(@NotNull String projectId, @NotNull String name, @NotNull String[] comlumns) {//FIXME: Save on database
        final var dataOfProjects = dataOfProjectService.listByProjectId(projectId);
        dataOfProjects.forEach(dataOfProject -> {
            SparkSession session = SparkSession.builder().appName("join").master("local[*]").getOrCreate();

            try {
                writeBytesToFile("./temp.csv", dataOfProject.getFile().array());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Dataset<Row> table = session.read().format("csv").option("header", "true").load("./temp.csv");
            table.withColumn(name, functions.array(comlumns[0], comlumns[1]));
        });
    }

    public void updateValue(@NotNull String projectId, @NotNull UpdateValue updateValue) {//FIXME: Save on database
        final var dataOfProjects = dataOfProjectService.listByProjectId(projectId);

        String identifierColumn = fildsService.identifierColumn(projectId, updateValue.getFileId());

        dataOfProjects.forEach(dataOfProject -> {
            SparkSession session = SparkSession.builder().appName("join").master("local[*]").getOrCreate();
            try {
                writeBytesToFile("./temp.csv", dataOfProject.getFile().array());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Dataset<Row> table = session.read().format("csv").option("header", "true").load("./temp.csv");
            if (dataOfProject.getId().equals(updateValue.getFileId())) {
                final var columnName = updateValue.getColumn();
                final var table1 = table.map((MapFunction<Row, Row>) row -> {
                    final var index = row.fieldIndex(columnName);
                    var rowToList = JavaConverters.seqAsJavaList(row.toSeq()).toArray(); // ROW ->  List
                    if (updateValue.getId().equals(rowToList[row.fieldIndex(identifierColumn)]))
                        rowToList[index] = updateValue.getValue();
                    return new GenericRowWithSchema(rowToList, row.schema());
                }, table.encoder());
            }
        });
    }

    @Async
    public void analyseDates(@NotNull String projectId) {
        final var dataOfProjects = dataOfProjectService.listByProjectId(projectId);

        dataOfProjects.forEach(dataOfProject -> {
            SparkSession session = SparkSession.builder().appName("join").master("local[*]").getOrCreate();
            try {
                writeBytesToFile("./temp.csv", dataOfProject.getFile().array());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Dataset<Row> table = session.read().format("csv").option("header", "true").load("./temp.csv");
            var ativityColumn = fildsService.ativityColumn(projectId, dataOfProject.getId());
            var caseIDColumn = fildsService.caseIdColumn(projectId, dataOfProject.getId());

            var ativitys = table.select(ativityColumn).distinct().as(Encoders.STRING()).collectAsList();
            //[(A,B),(B,A)]

            Set<Set<Object>> relationshipActivitys = Sets.combinations(Set.of(ativitys.toArray()), 2);
            var save = new HashMap<String, AnalyseDate>();

            relationshipActivitys.forEach(ativitysValues -> {
                        final var ativitysNames = ativitysValues.toArray();
                        final var filter = table.where(table.col(ativityColumn).equalTo(ativitysNames[0]).or(
                                table.col(ativityColumn).equalTo(ativitysNames[1])));


                        var withColumn = filter.withColumn("diff", functions.lit(0l));

                        var reduce = withColumn.reduce((ReduceFunction<Row>) (v1, v2) -> {
                            final var index = v1.fieldIndex("diff");
                            var rowToList = JavaConverters.seqAsJavaList(v1.toSeq()).toArray();
                            if (v1.getAs(caseIDColumn).equals(v2.getAs(caseIDColumn))) {
                                rowToList[index] = (long) rowToList[index] + Math.abs(DataUtil.convert(v1.getAs("data inicial")).getTime() - DataUtil.convert(v2.getAs("data inicial")).getTime());
                            }

                            return new GenericRowWithSchema(rowToList, v2.schema());// RowFactory.create(rowToList);
                        });
                        var analyseDate = new AnalyseDate();
                        analyseDate.setProbability(withColumn.count() / (double) table.count());
                        if (withColumn.count() > 0l) {
                            analyseDate.setTime((long) reduce.getAs("diff") / withColumn.count());
                            save.put(ativitysValues.toString(), analyseDate);
                        } else {
                            analyseDate.setTime(0l);
                            save.put(ativitysValues.toString(), analyseDate);
                        }
                    }
            );
            final var fileAnalysis = new FileAnalysis();
            fileAnalysis.setFile_id(dataOfProject.getId());
            fileAnalysis.setProject_id(dataOfProject.getProjectId());
            fileAnalysis.setType(TypeAnalysis.Dates);
            fileAnalysis.setAnalysis(gson.toJson(save));
            fileAnalysisService.save(fileAnalysis);
        });
    }

    public void applyDates(@NotNull String projectId) {
        final var dataOfProjects = dataOfProjectService.listByProjectId(projectId);
        dataOfProjects.forEach(dataOfProject -> {
            SparkSession session = SparkSession.builder().appName("join").master("local[*]").getOrCreate();
            try {
                writeBytesToFile("./temp.csv", dataOfProject.getFile().array());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Dataset<Row> table = session.read().format("csv").option("header", "true").load("./temp.csv");
            var analysisDates = fileAnalysisService.findByFileId(dataOfProject.getId(), TypeAnalysis.Dates).getAnalysis();
            Map<String, AnalyseDate> map = new HashMap<>();
            final Map<String, AnalyseDate> finalMap = (Map<String, AnalyseDate>) gson.fromJson(analysisDates, map.getClass());
            final var ativityColumn = fildsService.ativityColumn(projectId, dataOfProject.getId());
            final var identifierColumn = fildsService.identifierColumn(projectId, dataOfProject.getId());
            final var caseIDColumn = fildsService.caseIdColumn(projectId, dataOfProject.getId());
            var dataNaN = table.filter(table.col("data inicial").isNaN());//FIXME:TROCAR PARA O CAMPO DA DATA

            dataNaN.foreach((ForeachFunction<Row>) row -> {
                AtomicReference<AnalyseDate> analyseDate = new AtomicReference<>();
                AtomicReference<String> activityRelation = new AtomicReference<>("");
                finalMap.forEach((key, value) -> {
                    var valueActivity = row.getAs(ativityColumn).toString();
                    if (key.contains(valueActivity) &&
                            analyseDate.get().getProbability() < value.getProbability())
                        analyseDate.set(value);
                    activityRelation.set(key.split(valueActivity + ":")[0]);
                });
                var select = table.select(table.col(caseIDColumn).equalTo(row.getAs(caseIDColumn)).desc())
                        .select(table.col(ativityColumn).equalTo(activityRelation));

            });
        });
    }

}