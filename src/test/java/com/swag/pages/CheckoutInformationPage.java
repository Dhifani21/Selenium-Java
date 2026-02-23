package com.swag.pages;

import com.swag.framework.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutInformationPage extends BasePage {

    private final By title = By.xpath("//span[text()='Checkout: Your Information']");
    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final By postalCode = By.id("postal-code");
    private final By continueBtn = By.id("continue");

    public CheckoutInformationPage(WebDriver driver) {
        super(driver);
        waitVisible(title);
    }

    public CheckoutOverviewPage fillAndContinue(String fName, String lName, String zip) {
        type(firstName, fName);
        type(lastName, lName);
        type(postalCode, zip);
        click(continueBtn);
        return new CheckoutOverviewPage(driver);
    }
}