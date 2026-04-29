package com.qa.tests;

import com.qa.Pages.CataloguePage;
import com.qa.Pages.CheckoutPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class EcommerceSite extends BaseTest {

    String productsName = "ZARA COAT 3";
    String country = "India";


    @Test (dataProvider ="getData" , groups = {"Purchase"})
    public void purchaseMethod(String email, String password,String productName) throws InterruptedException, IOException {

        CataloguePage cataloguePage=  loginPage.loginApp(email, password);

        cataloguePage.addProductToCart(productName);
        CheckoutPage checkoutPage = cataloguePage.clickCartButton();
        Assert.assertEquals(true, cataloguePage.verifyCart(productName));

        checkoutPage.fillCheckoutDetails(country);


        String finalText = checkoutPage.verifyText.getText().trim();
        Assert.assertTrue(finalText.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
        Thread.sleep(2000);

    }

    @DataProvider
   Object[] [] getData(){
        return new Object[][] {{"umair@gmail.com", "@Selenium7","ZARA COAT 3"},{"umair@gmail.com", "@Selenium7","ADIDAS ORIGINAL"}};

    }
}
