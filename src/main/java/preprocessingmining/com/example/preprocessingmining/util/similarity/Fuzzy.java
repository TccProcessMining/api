package preprocessingmining.com.example.preprocessingmining.util.similarity;

import org.apache.commons.text.similarity.FuzzyScore;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Fuzzy extends BaseSimilarity {
    private final FuzzyScore fuzzyScore;

    public Fuzzy() {
        super();
        this.fuzzyScore = new FuzzyScore(Locale.forLanguageTag("pt-BR"));
    }

    private int getResult(StringBuilder str1, StringBuilder str2) {

        return fuzzyScore.fuzzyScore(str1, str2);
    }

    @Override
    protected int normalizeValue(double value) {
        return 0;
    }

    @Override
    protected int normalizeValue(int value) {
        return value;
    }

    private int normalizeValue(int value, int length) {
        return ((value / length) * 5) / 2;
    }

    @Override
    public int calculate(StringBuilder str1, StringBuilder str2) {
        int result = this.getResult(str1, str2);
        int length = (str1.length() + str2.length()) / 2;

        return this.normalizeValue(result, length);
    }
}
