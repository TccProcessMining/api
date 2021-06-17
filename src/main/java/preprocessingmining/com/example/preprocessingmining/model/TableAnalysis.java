package preprocessingmining.com.example.preprocessingmining.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class TableAnalysis {
    private String name;
    private List<ColumnAnalysis> columns;
    private HashMap<String, Integer> correlationTables;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ColumnAnalysis> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnAnalysis> columns) {
        this.columns = columns;
    }

    public HashMap<String, Integer> getCorrelationTables() {
        return correlationTables;
    }

    public void setCorrelationTables(HashMap<String, Integer> correlationTables) {
        this.correlationTables = correlationTables;
    }

    public void addColumns(ColumnAnalysis columnAnalysis) {
        if(this.columns == null) this.setColumns(new ArrayList<>());
        this.columns.add(columnAnalysis);
    }
}
