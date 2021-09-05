package preprocessingmining.com.example.preprocessingmining.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Analysis {
    private List<TableAnalysis> tables;

    public void addTable(TableAnalysis tableAnalysis) {
        if(this.tables == null) this.setTables(new ArrayList<>());
        this.tables.add(tableAnalysis);
    }
}
