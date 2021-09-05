package preprocessingmining.com.example.preprocessingmining.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TableAnalysis {
    private String name;
    private List<ColumnAnalysis> valueColumns;
    private HashMap<String, Double> correlationColumns;
    private HashMap<String, Integer> correlationTables;


    public void addValueColumns(ColumnAnalysis columnAnalysis) {
        if(this.valueColumns == null) this.setValueColumns(new ArrayList<>());
        this.valueColumns.add(columnAnalysis);
    }
    public void addCorrelationColumns(String key, Double value) {
        if(this.correlationColumns == null) this.setCorrelationColumns(new HashMap<>());
        this.correlationColumns.put(key, value);
    }
}
