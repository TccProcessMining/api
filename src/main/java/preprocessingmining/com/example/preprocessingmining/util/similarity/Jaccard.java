package preprocessingmining.com.example.preprocessingmining.util.similarity;

import org.apache.commons.text.similarity.JaccardSimilarity;
import org.springframework.stereotype.Component;

@Component
public class Jaccard extends BaseSimilarity {
    private final JaccardSimilarity jaccardSimilarity;

    public Jaccard() {
        super();
        this.jaccardSimilarity = new org.apache.commons.text.similarity.JaccardSimilarity();
    }

    private double getResult(StringBuilder str1, StringBuilder str2) {
        return jaccardSimilarity.apply(str1, str2);
    }

    @Override
    protected int normalizeValue(double value) {
        return this.percertToScale.convert(value);
    }

    @Override
    protected int normalizeValue(int value) {
        return 0;
    }

    @Override
    public int calculate(StringBuilder str1, StringBuilder str2) {
        double result = this.getResult(str1, str2);
        return this.normalizeValue(result);
    }
}
