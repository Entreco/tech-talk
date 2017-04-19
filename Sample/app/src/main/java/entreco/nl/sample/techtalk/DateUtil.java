package entreco.nl.sample.techtalk;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class DateUtil {

    private static final SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public static String toDateString(Date date) {
        return simpleDateFormat.format(date);
    }

    public static Date fromDateString(@NonNull final String dateString) throws ParseException {
        return simpleDateFormat.parse(dateString);
    }
}
