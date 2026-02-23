package com.swag.pages;

import com.swag.framework.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage extends BasePage {

    private final By completeHeader = By.cssSelector(".complete-header"); // "Thank you for your order!"
    private final By completeText = By.cssSelector(".complete-text");
    private final By backHomeBtn = By.id("back-to-products");

    public static final String EXPECTED_HEADER = "Thank you for your order!";

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
        waitVisible(completeHeader);
    }

    public String getHeaderText() {
        return text(completeHeader);
    }

    public String getBodyText() {
        return text(completeText);
    }

    public boolean isOrderSuccess() {
        return EXPECTED_HEADER.equalsIgnoreCase(getHeaderText());
    }

    public InventoryPage clickBackHome() {
        click(backHomeBtn);
        return new InventoryPage(driver);
    }
}