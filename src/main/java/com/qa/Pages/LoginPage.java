package com.qa.Pages;

import Utils.HelperClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage  extends HelperClass {

    WebDriver driver;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="userEmail")
    WebElement userEmail;

    @FindBy(id="userPassword")
    WebElement userPassword;

    @FindBy(id="login")
    WebElement loginButton;

    @FindBy(css="[class*='flyInOut']")
    WebElement errorMessage;

    public CataloguePage loginApp(String email, String password){
        userEmail.sendKeys(email);
        userPassword.sendKeys(password);
        loginButton.click();

        return new CataloguePage(driver);

    }
   public void goTo(){
        driver.get("https://rahulshettyacademy.com/client/");
    }

   public String wrongCredentials(){
        waitForWebElementToAppear(errorMessage);
       System.out.println("message = "+errorMessage.getText());
        return errorMessage.getText();
    }
}

