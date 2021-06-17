package preprocessingmining.com.example.preprocessingmining.service;


import com.google.gson.Gson;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import preprocessingmining.com.example.preprocessingmining.model.Analysis;
import preprocessingmining.com.example.preprocessingmining.model.ColumnAnalysis;
import preprocessingmining.com.example.preprocessingmining.model.TableAnalysis;
import preprocessingmining.com.example.preprocessingmining.util.similarity.Jaccard;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class AnalysisService  {
    @Autowired
    private DataOfProjectService dataOfProjectService;
    @Autowired
    private Jaccard jaccard;
    @Autowired
    private Gson gson;
    @Async
    public void analysis(String projectId) {
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
            for(String column : columns){
                final var values = table.select(column).distinct().as(Encoders.STRING()).collectAsList();
                var columnAnalysis = new ColumnAnalysis();
                columnAnalysis.setName(column);
                for(String baseValue : values){
                    for(String analysisValue : values){
                        final var result = jaccard.calculate(new StringBuilder(baseValue),
                                                                    new StringBuilder(analysisValue));
                        columnAnalysis.addCorrelationValues(baseValue+":"+analysisValue,result);
                    }
                }
                tableAnalysis.addColumns(columnAnalysis);
            }
            analysis.addTable(tableAnalysis);
        });
        String json = gson.toJson(analysis);
        System.out.println(json);
    }
    private static void writeBytesToFile(String fileOutput, byte[] bytes)
            throws IOException {

        try (FileOutputStream fos = new FileOutputStream(fileOutput)) {
            fos.write(bytes);
        }

    }
}
