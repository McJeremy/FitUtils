package com.xuzz.fitutils;

import java.util.Properties;

public class PropertiesParser {
    private Properties prop;

    public PropertiesParser(Properties prop) {
        this.prop = prop;
    }

    public String getString(String key) {
        String value = prop.getProperty(key);
        return value;
    }

    public String getString(String key, String defaultValue) {
        String value = prop.getProperty(key);
        return value == null ? defaultValue : value;
    }

    public int getInt(String key) {
        String value = prop.getProperty(key);
        return Integer.parseInt(value);
    }

    public int getInt(String key, int defaultValue) {
        String value = prop.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public float getFloat(String key) {
        String value = prop.getProperty(key);
        return Float.parseFloat(value);
    }

    public float getFloat(String key, float defaultValue) {
        String value = prop.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

}
