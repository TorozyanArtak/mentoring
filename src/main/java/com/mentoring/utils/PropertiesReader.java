package com.mentoring.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
    private static PropertiesReader instance;
    private final Properties properties;

    private PropertiesReader() {
        properties = new Properties();
        try {
            String propertiesPath = "src/main/resources/config.properties";
            FileInputStream fis = new FileInputStream(propertiesPath);
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file: " + e.getMessage());
        }
    }

    public static PropertiesReader getInstance() {
        if (instance == null) {
            synchronized (PropertiesReader.class) {
                if (instance == null) {
                    instance = new PropertiesReader();
                }
            }
        }
        return instance;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}

