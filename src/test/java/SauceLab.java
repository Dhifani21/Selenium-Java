import com.swag.framework.DriverFactory;
import com.swag.pages.*;
import com.swag.pages.components.HeaderComponent;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent;

public class SauceLab {
    public static void main(String[] args) {
        try {
            // Initialize WebDriver via DriverFactory (Chrome with your options)
            DriverFactory.initDriver();
            WebDriver driver = DriverFactory.getDriver();
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(4));

            // Navigate to Sauce Demo
            driver.get("https://www.saucedemo.com");

            // ---- Login Page ----
            LoginPage login = new LoginPage(driver);
            System.out.println("Title is : Swag Labs (visible? " + login.isLoaded() + ")");

            // Perform Login
            InventoryPage inventory = login.loginAs("standard_user", "secret_sauce");

            // Handle any potential alert 
            try {
                Alert alert = shortWait.until(alertIsPresent());
                System.out.println("Alert text: " + alert.getText());
                driver.switchTo().alert();
                alert.accept();
                System.out.println("Alert accepted.");
            } catch (Exception e) {
                System.out.println("No JS alert appeared after login");
            }

            // ---- Inventory (Products) Page ----
            System.out.println("Title is visible ? : " + "Products".equals(inventory.getTitle()));
            inventory.sortByVisibleText("Price (low to high)");

            // Open specific product by name (same as your script)
            ProductDetailsPage details =
                    inventory.openProductByName("Test.allTheThings() T-Shirt (Red)");

            // ---- Product Details Page ----
            System.out.println("Verify if the product details page is displayed: " + details.isImageDisplayed());
            System.out.println("Description of Product: " + details.getDescription());
            System.out.println("Cost of the product: " + details.getPrice());

            // Add to cart and go to cart
            details.addToCart();
            CartPage cart = details.goToCart();

            // ---- Cart Page ----
            System.out.println("Verify if the Cart page is displayed: " + cart.isLoaded());
            System.out.println("Verify if 1 product is added to the cart: " + cart.getTotalQuantity());

            // ---- Checkout: Your Information ----
            CheckoutInformationPage info = cart.clickCheckout();
            CheckoutOverviewPage overview = info.fillAndContinue("Selenium", "Testing", "600043");

            // ---- Checkout Overview ----
            System.out.println("Payment Information : " + overview.getPaymentInfo());
            System.out.println("Shipping Information : " + overview.getShippingInfo());
            System.out.println("Total Price : " + overview.getTotal());

            // Finish the order
            CheckoutCompletePage complete = overview.clickFinish();
            System.out.println("Order Placed Successfully ? " + complete.isOrderSuccess());

            // ---- Logout via Header ----
            HeaderComponent header = new HeaderComponent(driver);
            header.logout();

            // Verify back on login page
            LoginPage loginAgain = new LoginPage(driver);
            System.out.println("User Logged Out? : " + loginAgain.isLoaded());

        } finally {
            // Quit the driver
            DriverFactory.quitDriver();
        }
    }
}