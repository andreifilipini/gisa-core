package com.gisa.gisacore.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;


public class DateUtil {

    public static final DateTimeFormatter simpleDate = new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter();

    public static final DateTimeFormatter simpleTime = new DateTimeFormatterBuilder().appendPattern("HH:mm").toFormatter();

    public static LocalDate toSimplelLocalDate(String date) {
        return LocalDate.parse(date, simpleDate);
    }

    public static LocalTime toSimplelLocalTime(String time) {
        return LocalTime.parse(time, simpleTime);
    }
}
