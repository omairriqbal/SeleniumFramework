package com.qa.Pages;

import Utils.HelperClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CataloguePage extends HelperClass {

    WebDriver driver;

    public CataloguePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".cartSection h3")
    List<WebElement> cartList;
    By cartSection = By.cssSelector(".cartSection h3");

    @FindBy(css = ".mb-3")
    List<WebElement> products;
    By productsList = By.cssSelector(".mb-3");

    @FindBy(css = ".totalRow button")
    List<WebElement> proceedBtn;

    By addToCart = By.cssSelector(".card-body button:last-of-type");
    By toastMessage = By.id("toast-container");
    By cartModule = By.cssSelector(".cartSection");
    By searchCountry = By.cssSelector("[placeholder='Select Country']");


    @FindBy(id = "toast-container")
    WebElement toastMsg;
    @FindBy(css = "[routerlink*='cart']")
    WebElement cartButton;

    public List<WebElement> getProductsList() {
        waitForElementToAppear(productsList);
        return products;
    }

    public WebElement getProductName(String productName) {
        WebElement product = getProductsList().stream().filter(item ->
                item.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
        return product;
    }

    public void addProductToCart(String productName) {
        WebElement prod = getProductName(productName);
        prod.findElement(addToCart).click();
        waitForElementToAppear(toastMessage);
        waitForElementToDisappear(toastMsg);
    }

    public CheckoutPage clickCartButton() {
        cartButton.click();
        waitForElementToAppear(cartModule);
        return new CheckoutPage(driver);
    }

    public Boolean verifyCart(String productName) {
        Boolean check = cartList.stream().anyMatch(cartItem ->
                cartItem.getText().equalsIgnoreCase(productName));
        return check;

    }
}

