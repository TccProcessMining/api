package preprocessingmining.com.example.preprocessingmining.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class Analysis {
    private List<TableAnalysis> tables;

    public List<TableAnalysis> getTables() {
        return tables;
    }

    public void setTables(List<TableAnalysis> tables) {
        this.tables = tables;
    }

    public void addTable(TableAnalysis tableAnalysis) {
        if(this.tables == null) this.setTables(new ArrayList<>());
        this.tables.add(tableAnalysis);
    }
}
