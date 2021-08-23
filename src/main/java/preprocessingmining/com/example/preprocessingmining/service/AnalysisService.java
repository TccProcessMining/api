package preprocessingmining.com.example.preprocessingmining.service;


import com.google.gson.Gson;
import org.apache.spark.api.java.function.ForeachFunction;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import preprocessingmining.com.example.preprocessingmining.model.Analysis;
import preprocessingmining.com.example.preprocessingmining.model.ColumnAnalysis;
import preprocessingmining.com.example.preprocessingmining.model.DTO.AnalyseDate;
import preprocessingmining.com.example.preprocessingmining.model.DTO.UpdateValue;
import preprocessingmining.com.example.preprocessingmining.model.Field;
import preprocessingmining.com.example.preprocessingmining.model.TableAnalysis;
import preprocessingmining.com.example.preprocessingmining.util.DataUtil;
import preprocessingmining.com.example.preprocessingmining.util.Permutation;
import preprocessingmining.com.example.preprocessingmining.util.similarity.Jaccard;
import scala.collection.JavaConverters;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AnalysisService {
    @Autowired
    private DataOfProjectService dataOfProjectService;
    @Autowired
    private Jaccard jaccard;
    @Autowired
    private Gson gson;
    @Autowired
    private DataUtil dataUtil;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private FildsService fildsService;

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
            var header = table.head().toSeq();
            var tableAnalysis = new TableAnalysis();
            tableAnalysis.setName(dataOfProject.getName());
            for (String column : columns) {
                final var values = table.select(column).distinct().as(Encoders.STRING()).collectAsList();
                var columnAnalysis = new ColumnAnalysis();
                columnAnalysis.setName(column);
                for (String baseValue : values) {
                    for (String analysisValue : values) {
                        if (baseValue != null && analysisValue != null) {
                            final var result = jaccard.calculate(new StringBuilder(baseValue),
                                    new StringBuilder(analysisValue));
                            columnAnalysis.addCorrelationValues(baseValue + ":" + analysisValue, result);
                        }
                    }
                }
                tableAnalysis.addColumns(columnAnalysis);
            }
            analysis.addTable(tableAnalysis);
        });
        String json = gson.toJson(analysis);
        System.out.println(json);//FIXME: Save on database

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
                    return RowFactory.create(rowToList);
                }, table.encoder());
            }
        });
    }

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
            var relationshipActivitys = Permutation.generatePerm(ativitys);
            var save = new HashMap<List<String>, Long>();
            relationshipActivitys.forEach(ativitysValues -> {
                        var analyseDate = new HashMap<String, AnalyseDate>();
                        final var where = table.where(
                                table.col(ativityColumn).equalTo(ativitysValues.get(0)).or(
                                        table.col(ativityColumn).equalTo(ativitysValues.get(1))));
                        where.foreach((ForeachFunction<Row>) row -> {
                            if (analyseDate.get(caseIDColumn) != null)
                                analyseDate.get(caseIDColumn).setD2(DataUtil.convert(row.getAs("data")));//FIXME:TROCAR PARA A VARIAVEL DE DATA
                            else
                                analyseDate.put(row.getAs(caseIDColumn), new AnalyseDate(DataUtil.convert(row.getAs("data"))));
                        });
                        AtomicLong diff = new AtomicLong();
                        analyseDate.forEach((key, value) -> {
                            diff.addAndGet(Math.abs(value.getD1().getTime() - value.getD2().getTime()));
                        });
                        save.put(ativitysValues, diff.longValue()/analyseDate.size());
                    }
            );
            String json = gson.toJson(save);
            System.out.println(json);//FIXME: Save on database
        });
    }


}
