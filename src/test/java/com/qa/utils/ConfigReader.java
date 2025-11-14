package com.qa.utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties props = new Properties();

    // Uygulama yüklenirken otomatik env oku (Jenkins: -Denv=qa, local: dev)
    static {
        String env = System.getProperty("env", "dev").trim();
        load(env);
    }

    // Eski kodun çağırdığı public load(String env) metodunu geri getiriyoruz
    public static synchronized void load(String env) {
        String fileName = "configs/config-" + env + ".properties";
        props.clear();

        try (InputStream input =
                     ConfigReader.class.getClassLoader().getResourceAsStream(fileName)) {

            if (input == null) {
                throw new RuntimeException("Config file not found: " + fileName);
            }
            props.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config file: " + fileName, e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
