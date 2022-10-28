package com.epam.hospital.util.date;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    public static final SimpleDateFormat RU_LOCALE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    public static final SimpleDateFormat ENG_LOCALE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    public static final SimpleDateFormat HTML_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    public static final SimpleDateFormat MYSQL_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String format(Date date, DateFormatterType dateFormatType) {
        switch (dateFormatType) {
            case MYSQL:
                return DateFormatter.MYSQL_FORMATTER.format(date);
            case HTML:
                return DateFormatter.HTML_FORMATTER.format(date);
            case LOCALE_RU:
                return DateFormatter.RU_LOCALE_FORMATTER.format(date);
            case LOCALE_ENG:
                return DateFormatter.ENG_LOCALE_FORMATTER.format(date);
            default:
                throw new IllegalArgumentException("Unknown date format type.");
        }
    }
}
