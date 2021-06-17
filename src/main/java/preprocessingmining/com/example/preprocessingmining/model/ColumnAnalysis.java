package preprocessingmining.com.example.preprocessingmining.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;
@AllArgsConstructor
@NoArgsConstructor
public class ColumnAnalysis {
    private String name;
    private HashMap<String, Integer> correlationValues;
    private HashMap<String, Integer> correlationColumns;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Integer> getCorrelationValues() {
        return correlationValues;
    }

    public void setCorrelationValues(HashMap<String, Integer> correlationValues) {
        this.correlationValues = correlationValues;
    }

    public HashMap<String, Integer> getCorrelationColumns() {
        return correlationColumns;
    }

    public void setCorrelationColumns(HashMap<String, Integer> correlationColumns) {
        this.correlationColumns = correlationColumns;
    }

    public void addCorrelationValues(String key, Integer value){
        if(this.correlationValues == null) setCorrelationValues(new HashMap<>());
        this.correlationValues.put(key, value);
    }
}
