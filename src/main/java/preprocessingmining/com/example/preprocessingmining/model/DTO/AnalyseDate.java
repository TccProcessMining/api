package preprocessingmining.com.example.preprocessingmining.model.DTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class AnalyseDate {

    private Date d1, d2;

    public AnalyseDate(Date d1) {
        this.d1 = d1;
    }

    public Date getD1() {
        return d1;
    }

    public void setD1(Date d1) {
        this.d1 = d1;
    }

    public Date getD2() {
        return d2;
    }

    public void setD2(Date d2) {
        this.d2 = d2;
    }
}
