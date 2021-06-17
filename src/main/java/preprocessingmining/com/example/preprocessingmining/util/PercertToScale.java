package preprocessingmining.com.example.preprocessingmining.util;

import org.springframework.stereotype.Component;

@Component
public class PercertToScale {
    /*
     * Recive a double number 0..1
     * and convert to 1..5
     * */
    public int convert(double percent) {
        return (int) Math.ceil(percent * 5);
    }

    public int convertM(double percent) {
        int aux = (int) percent * 5 == 0 ? 1 : (int) percent * 5;
        return convert(percent) / aux;
    }
}
