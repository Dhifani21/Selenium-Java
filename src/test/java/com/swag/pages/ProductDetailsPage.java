package com.swag.pages;

import com.swag.framework.BasePage;
import com.swag.pages.components.HeaderComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailsPage extends BasePage {

    private final By productImage = By.cssSelector("img.inventory_details_img");
    private final By productDesc = By.cssSelector(".inventory_details_desc");
    private final By productPrice = By.cssSelector(".inventory_details_price");
    private final By addToCartBtn = By.cssSelector("button[id^='add-to-cart']"); // generic PDP add-to-cart
    private final HeaderComponent header;

    public ProductDetailsPage(WebDriver driver) {
        super(driver);
        header = new HeaderComponent(driver);
        waitVisible(productImage);
    }

    public boolean isImageDisplayed() {
        return waitVisible(productImage).isDisplayed();
    }

    public String getDescription() {
        return text(productDesc);
    }

    public String getPrice() {
        return text(productPrice);
    }

    public ProductDetailsPage addToCart() {
        click(addToCartBtn);
        return this;
    }

    public CartPage goToCart() {
        header.openCart();
        return new CartPage(driver);
    }

    public HeaderComponent header() {
        return header;
    }
}