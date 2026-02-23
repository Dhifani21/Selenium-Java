package com.swag.pages;

import com.swag.framework.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutOverviewPage extends BasePage {

    private final By paymentInfo = By.xpath("//div[@class='summary_value_label' and @data-test='payment-info-value']");
    private final By shippingInfo = By.xpath("//div[@class='summary_value_label' and @data-test='shipping-info-value']");
    private final By totalLabel = By.xpath("//div[@class='summary_total_label' and @data-test='total-label']");
    private final By finishBtn = By.id("finish");

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
        waitVisible(paymentInfo);
    }

    public String getPaymentInfo() {
        return text(paymentInfo);
    }

    public String getShippingInfo() {
        return text(shippingInfo);
    }

    public String getTotal() {
        return text(totalLabel);
    }

    public CheckoutCompletePage clickFinish() {
        click(finishBtn);
        return new CheckoutCompletePage(driver);
    }
}