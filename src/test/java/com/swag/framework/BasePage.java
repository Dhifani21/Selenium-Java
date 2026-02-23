package com.swag.framework;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void click(By locator) {
        waitClickable(locator).click();
    }

    protected void type(By locator, String value) {
        WebElement el = waitVisible(locator);
        el.clear();
        el.sendKeys(value);
    }

    protected String text(By locator) {
        return waitVisible(locator).getText().trim();
    }
}