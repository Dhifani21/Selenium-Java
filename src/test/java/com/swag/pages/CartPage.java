package com.swag.pages;

import com.swag.framework.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {

    private final By title = By.cssSelector(".title"); // "Your Cart"
    private final By quantityCells = By.cssSelector(".cart_quantity");
    private final By checkoutBtn = By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
        waitVisible(title);
    }

    public boolean isLoaded() {
        return "Your Cart".equalsIgnoreCase(text(title));
    }

    public int getTotalQuantity() {
        int sum = 0;
        List<WebElement> cells = driver.findElements(quantityCells);
        for (WebElement c : cells) {
            try {
                sum += Integer.parseInt(c.getText().trim());
            } catch (NumberFormatException ignored) {}
        }
        return sum;
    }

    public CheckoutInformationPage clickCheckout() {
        click(checkoutBtn);
        return new CheckoutInformationPage(driver);
    }
}