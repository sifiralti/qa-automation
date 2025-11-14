package com.qa.core;

import com.qa.utils.ConfigReader;
import org.testng.annotations.BeforeClass;

public class BaseApiTest {
    @BeforeClass
    public void baseApiSetup() {
        String env = System.getProperty("env", "dev");
        ConfigReader.load(env);
    }
}
