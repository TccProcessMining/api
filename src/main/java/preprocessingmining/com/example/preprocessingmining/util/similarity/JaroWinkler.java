package preprocessingmining.com.example.preprocessingmining.util.similarity;

import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.springframework.stereotype.Component;

@Component
public class JaroWinkler extends BaseSimilarity {

    private final JaroWinklerSimilarity jaroWinklerSimilarity;

    public JaroWinkler() {
        super();
        this.jaroWinklerSimilarity = new org.apache.commons.text.similarity.JaroWinklerSimilarity();
    }


    private Double getResult(StringBuilder str1, StringBuilder str2) {
        return jaroWinklerSimilarity.apply(str1, str2);
    }

    @Override
    protected int normalizeValue(int value) {
        return 0;
    }

    @Override
    protected int normalizeValue(double value) {
        return this.percertToScale.convertM(value);
    }

    public int calculate(StringBuilder str1, StringBuilder str2) {
        double result = this.getResult(str1, str2);
        return  this.normalizeValue(result);
    }
}
