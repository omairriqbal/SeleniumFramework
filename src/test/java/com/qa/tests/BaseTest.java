package com.qa.tests;

import com.qa.Pages.LoginPage;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {

   public WebDriver driver;
    LoginPage loginPage;

    WebDriver initializeDrivers() throws IOException {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/GlobalData.properties");
        properties.load(fileInputStream);
        String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") :properties.getProperty("browser");
//                properties.getProperty("Browser");


        if (browserName.equalsIgnoreCase("Edge")) {
            driver = new EdgeDriver();

        } else if (browserName.equalsIgnoreCase("Chrome")) {
            driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }
    public String getScreenShot(String testCaseName, WebDriver driver) throws IOException {

        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        String screenshotName = testCaseName + ".png";

        String destinationPath = System.getProperty("user.dir")
                + File.separator + "reports"
                + File.separator + screenshotName;

        FileUtils.copyFile(source, new File(destinationPath));

        return screenshotName;   // important: only main.png
    }

    @BeforeMethod (alwaysRun = true)
   public LoginPage launchApp() throws IOException {
        driver = initializeDrivers();
        loginPage = new LoginPage(driver);
        loginPage.goTo();
        return loginPage;
    }
    @AfterMethod(alwaysRun = true)
  public void tearDown(ITestResult result) throws IOException {
        driver.close();
    }
}
