package com.qa.core;

import com.qa.utils.ConfigReader;
import com.qa.utils.DriverFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseUiTest {

    @BeforeClass
    public void baseUiSetup() {
        String env = System.getProperty("env", "dev");
        ConfigReader.load(env);
        DriverFactory.init(); // headless istiyorsan DriverFactory'de flag açarsın
    }

    @AfterClass(alwaysRun = true)
    public void baseUiTeardown() {
        DriverFactory.quit();
    }
}
