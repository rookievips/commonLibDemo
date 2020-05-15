package com.chen.api.http.volley;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";

    public static String formatDate(Date date) {
        SimpleDateFormat sf = new SimpleDateFormat(PATTERN_RFC1123, Locale.US);
        return sf.format(date);
    }

    public static Date parseDate(String dateStr) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(PATTERN_RFC1123, Locale.US);
        return sf.parse(dateStr);
    }

}
