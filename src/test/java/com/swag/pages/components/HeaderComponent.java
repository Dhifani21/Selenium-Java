package com.swag.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class HeaderComponent {

    private final WebDriverWait wait;

    private final By cartIcon = By.id("shopping_cart_container");
    private final By menuBtn = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link"); // reliable selector

    public HeaderComponent(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();
    }

    public void openMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(menuBtn)).click();
    }

    public void logout() {
        openMenu();
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }
}
