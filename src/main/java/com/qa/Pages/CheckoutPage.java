package com.qa.Pages;

import Utils.HelperClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CheckoutPage extends HelperClass {

    WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".ta-item")
    List<WebElement> countriesList;

    @FindBy(css = "[placeholder='Select Country']")
    WebElement searchCountries;
    By searchCountry = By.cssSelector("[placeholder='Select Country']");

    @FindBy(css = ".totalRow button")
    WebElement proceedBtn;

    @FindBy(css = ".btnn.action__submit.ng-star-inserted")
    WebElement proceedToPay;
    @FindBy(css = ".hero-primary")
    public WebElement verifyText;

    public void fillCheckoutDetails(String country){
        proceedBtn.click();
        waitForElementToAppear(searchCountry);
        searchCountries.sendKeys(country);
        WebElement countries = countriesList.stream().filter(search ->
                search.getText().equalsIgnoreCase(country)).findFirst().orElse(null);
        countries.click();
        proceedToPay.click();
    }

}

