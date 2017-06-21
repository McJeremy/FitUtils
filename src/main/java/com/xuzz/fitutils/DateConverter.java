package com.xuzz.fitutils;

import org.apache.commons.beanutils.Converter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DateConverter implements Converter {
    private static Set<String> patterns = new HashSet();

    static {
        patterns.add("yyyy-MM-dd");
        patterns.add("yyyy-MM-dd HH:mm");
        patterns.add("yyyy-MM-dd HH:mm:ss");
        patterns.add("yyyy/MM/dd HH:mm:ss");
    }

    public Object convert(Class type, Object value) {
        SimpleDateFormat df = new SimpleDateFormat();

        if (value == null)
            return null;
        if ((value instanceof String)) {
            Object dateObj = null;
            Iterator it = patterns.iterator();
            while (it.hasNext()) {
                try {
                    String pattern = (String) it.next();
                    df.applyPattern(pattern);
                    dateObj = df.parse((String) value);
                } catch (ParseException localParseException) {
                }
            }
            return dateObj;
        }
        if ((value instanceof Date))
            return value;
        if ((value instanceof Timestamp)) {
            Timestamp t = (Timestamp) value;
            try {
                return t.toString();
            } catch (Exception e) {
                e.printStackTrace();

                return null;
            }
        }
        return null;
    }
}