package com.org.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigFileReader {

    private static Properties prop;

     
    public static Properties ReadProperties() {
        try {
            InputStream input = new FileInputStream("src/test/resources/configuration.properties");
            prop = new Properties();
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return prop;
    }

}