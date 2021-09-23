package com.matthewcasperson.demo.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public record GraphEventDate(String dateTime, String timeZone) {
    public Date getFormattedDateTime() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS", Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
        return formatter.parse(dateTime);
    }
}
