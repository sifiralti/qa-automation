package com.qa.listeners;

import com.qa.utils.ScreenshotUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String path = ScreenshotUtils.takePng(testName);
        if (path != null) {
            System.out.println("[SCREENSHOT] " + testName + " -> " + path);
        }
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("=== Suite START: " + context.getSuite().getName() + " ===");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("=== Suite END: " + context.getSuite().getName() + " ===");
    }
}
