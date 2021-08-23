package preprocessingmining.com.example.preprocessingmining.util;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Component
public class DataUtil {

    public static Date convert(String date) {
        try {
            return DateUtils.parseDate(date,
                    "yyyy-MM-dd HH:mm:ss",
                    "dd/MM-yyyy",
                    "dd/MM/yyyy",
                    "MM/dd/yyyy",
                    "dd-MM-yyyy",
                    "dd-MM/yyyy HH:mm:ss",
                    "dd-MM/yyyy HH:mm",
                    "dd-MM-yyyy HH:mm",
                    "dd-MM-yy",
                    "yyyy/dd-MM mm:HH",
                    "MM-dd-yyyy",
                    "yyyy-MM-dd",
                    "dd/MM/yy",
                    "dd/MM/yyyy",
                    "MM/dd/yyyy",
                    "MM/dd/yy",
                    "dd/MM-yyyy",
                    "dd-MM-yy");
        } catch (ParseException e) {
            return null;
        }
    }

    public static String format(Date date){
        if(date == null) return null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return  simpleDateFormat.format(date);
    }

    public static String tranform(String date){
        if(date == null) return null;
        return format(convert(date));
    }

    public static Optional<Date> media(Date x, Date y) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            x = simpleDateFormat.parse(simpleDateFormat.format(x));
            y = simpleDateFormat.parse(simpleDateFormat.format(y));
            return Optional.of(new Date((x.getTime() + y.getTime()) / 2));
        } catch (ParseException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
