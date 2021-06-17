package preprocessingmining.com.example.preprocessingmining.util.similarity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import preprocessingmining.com.example.preprocessingmining.util.PercertToScale;

@Component
@NoArgsConstructor
@AllArgsConstructor
public  abstract class BaseSimilarity {
    @Autowired
    protected PercertToScale percertToScale;

    protected abstract int normalizeValue(int value);

    protected abstract int normalizeValue(double value);

    protected abstract int calculate(StringBuilder str1, StringBuilder str2);

}