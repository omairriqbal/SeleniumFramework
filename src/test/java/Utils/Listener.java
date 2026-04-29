package Utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.qa.tests.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class Listener implements ITestListener {
    ExtentTest extentTest;
    ExtentReports extentReports = ExtentReportNG.getExtentReport();
    private final BaseTest baseTest = new BaseTest();
    ThreadLocal<ExtentTest> threadLocal = new ThreadLocal<ExtentTest>();

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test started: " + result.getName());
        extentTest = extentReports.createTest(result.getMethod().getMethodName());
        threadLocal.set(extentTest);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (result.wasRetried()) {
            threadLocal.get().skip("Test skipped because it is being retried: "
                    + result.getMethod().getMethodName());
        } else {
            threadLocal.get().skip("Test skipped: "
                    + result.getMethod().getMethodName());
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test failed: " + result.getName());


        String path;
        WebDriver driver;
        try {
            driver = (WebDriver) result.getTestClass()
                    .getRealClass()
                    .getField("driver")
                    .get(result.getInstance());
            path = baseTest.getScreenShot(result.getMethod().getMethodName(), driver);
            System.out.println("Path = " + path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String screenshotName = null;
        try {
            screenshotName = baseTest.getScreenShot(result.getMethod().getMethodName(), driver);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        threadLocal.get().fail(result.getMethod().getMethodName(),
                MediaEntityBuilder.createScreenCaptureFromPath(screenshotName).build());
        threadLocal.get().fail(result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
        extentReports.flush();
    }
}