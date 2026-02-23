package com.swag.pages;

import com.swag.framework.BasePage;
import com.swag.pages.components.HeaderComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class InventoryPage extends BasePage {

    private final By title = By.cssSelector(".title"); // "Products"
    private final By sortSelect = By.cssSelector("select.product_sort_container");
    private final HeaderComponent header;

    public InventoryPage(WebDriver driver) {
        super(driver);
        header = new HeaderComponent(driver);
        waitVisible(title);
    }

    public String getTitle() {
        return text(title);
    }

    public InventoryPage sortByVisibleText(String option) {
        Select select = new Select(waitVisible(sortSelect));
        select.selectByVisibleText(option);
        return this;
    }

    public ProductDetailsPage openProductByName(String productName) {
        By product = By.xpath("//div[contains(@class,'inventory_item_name') and text()='" + productName + "']");
        click(product);
        return new ProductDetailsPage(driver);
    }

    public CartPage goToCart() {
        header.openCart();
        return new CartPage(driver);
    }

    public HeaderComponent header() {
        return header;
    }
}
