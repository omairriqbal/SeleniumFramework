package com.qa.tests;

import Utils.RetryListener;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class ErrorValidationClass extends BaseTest {
    @Test (retryAnalyzer = RetryListener.class)
    public void main() throws InterruptedException, IOException {

         loginPage.loginApp("umair@gmail.com", "@Selenium");
        System.out.println("text "+ loginPage.wrongCredentials());
        Assert.assertEquals("Incorrect email password.",loginPage.wrongCredentials());

    }
}
