package com.swag.pages;

import com.swag.framework.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class LoginPage extends BasePage {

    private final By swagTitle = By.xpath("//div[text()='Swag Labs']");
    private final By username = By.id("user-name");
    private final By password = By.name("password");
    private final By loginBtn = By.id("login-button");

    public LoginPage(WebDriver driver) {
        super(driver);
        wait.until(visibilityOfElementLocated(swagTitle));
    }

    public boolean isLoaded() {
        return waitVisible(swagTitle).isDisplayed();
    }

    public InventoryPage loginAs(String user, String pass) {
        type(username, user);
        type(password, pass);
        click(loginBtn);
        return new InventoryPage(driver);
    }
}