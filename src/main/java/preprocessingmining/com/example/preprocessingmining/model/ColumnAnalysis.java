package preprocessingmining.com.example.preprocessingmining.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ColumnAnalysis {
    private String name;
    private HashMap<String, Double> correlationValues;


    public void addCorrelationValues(String key, Double value){
        if(this.correlationValues == null) setCorrelationValues(new HashMap<>());
        this.correlationValues.put(key, value);
    }
}
