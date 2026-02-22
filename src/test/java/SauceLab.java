import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

import java.time.Duration;

public class SauceLab {
    public static void main(String [] args) throws Exception {   // ← allow creating temp profile
        WebDriverManager.chromedriver().setup();    //setup chrome driver

        ChromeOptions options = new ChromeOptions();

        // Run in Incognito 
        options.addArguments("--incognito");

        // Disable notifications
        options.addArguments("--disable-notifications");

        // Remove 'controlled by automated software' infobar
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        // Hide the “Chrome is being automated” bar
        options.addArguments("--disable-infobars");

        WebDriver driver = new ChromeDriver(options);

        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10)); //explicit wait
        driver.manage().window().maximize();
        
        //Go to Website
        driver.get("https://www.saucedemo.com");
        WebElement title = wait.until(visibilityOfElementLocated(By.xpath("//div[text()='Swag Labs']")));
        System.out.println("Title is : " + title.getText());
        
        //Login
        WebElement username = driver.findElement(By.id("user-name"));
        username.sendKeys("standard_user");
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("secret_sauce");
        WebElement loginBtn = driver.findElement(By.id("login-button"));
        loginBtn.click();

        try {
            Alert alert = new WebDriverWait(driver, Duration.ofSeconds(4)).until(alertIsPresent());
            System.out.println("Alert text: " + alert.getText());
            driver.switchTo().alert();
            alert.accept(); // or alert.dismiss();
            System.out.println("Alert accepted.");
        } catch (Exception e) {
            System.out.println("No JS alert appeared after login");
        }
        
        //Home Page
        WebElement title1 = wait.until(visibilityOfElementLocated(By.xpath("//div[text()='Swag Labs']")));
        System.out.println("Title is visible ? : "+ title1.isDisplayed());
        
        //Apply Filter
        Select select = new Select(driver.findElement(By.xpath("//select[@class='product_sort_container']")));
        select.selectByVisibleText("Price (low to high)");
        
        //Select a Product
        WebElement prod1 = driver.findElement(By.xpath("//div[text()='Test.allTheThings() T-Shirt (Red)']"));
        
        Actions actions = new Actions(driver);
        actions.moveToElement(prod1).perform();
        prod1.click();
        
        // Product Details Page
        WebElement prodImage = driver.findElement(By.xpath("//img[@alt='Test.allTheThings() T-Shirt (Red)']"));
        System.out.println("Verify if the product details page is displayed: " + prodImage.isDisplayed());
        
        WebElement prodDesc = driver.findElement(By.xpath("//div[@class='inventory_details_desc large_size']"));
        System.out.println("Description of Product: " + prodDesc.getText());
        
        WebElement rate = driver.findElement(By.xpath("//div[@class='inventory_details_price']"));
        System.out.println("Cost of the product: " + rate.getText());
        
        WebElement AddToCart = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart")));
        AddToCart.click();
        
        //Navigate to Cart
        WebElement cart = driver.findElement(By.xpath("//div[@id='shopping_cart_container']"));
        cart.click();
        
        //Cart Page
        WebElement yourCart = driver.findElement(By.xpath("//span[text()='Your Cart']"));
        System.out.println("Verify if the Cart page is displayed: " + yourCart.isDisplayed());  
        
        WebElement cartQuantity = driver.findElement(By.xpath("//div[@class='cart_quantity' and text()='1']"));
        System.out.println("Verify if 1 product is added to the cart: " + cartQuantity.getText());
        
        //Navigate to Checkout Page
        WebElement checkOut = driver.findElement(By.id("checkout"));
        checkOut.click();
        
        //Checkout Page
        WebElement title2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Checkout: Your Information']")));
        String titl = "Checkout: Your Information";
        System.out.println("Page is displayed correct ? : " + titl.equals(title2.getText()));
        
        //Fill Details in Checkout Page
        WebElement firstName = driver.findElement(By.id("first-name"));
        firstName.sendKeys("Selenium");
        WebElement lastName = driver.findElement(By.id("last-name"));
        lastName.sendKeys("Testing");
        WebElement zipCode = driver.findElement(By.id("postal-code"));
        zipCode.sendKeys("600043");
        WebElement continueBtn = driver.findElement(By.name("continue"));
        continueBtn.click();
        
        //Get Payment Information
        WebElement paymentInfo = driver.findElement(By.xpath("//div[@class='summary_value_label' and @data-test='payment-info-value']"));
        System.out.println("Payment Information : " + paymentInfo.getText());
        
        //Get Shipping Information
        WebElement shippingInfo = driver.findElement(By.xpath("//div[@class='summary_value_label' and @data-test='shipping-info-value']"));
        System.out.println("Shipping Information : " + shippingInfo.getText());
        
        //Get Price Total
        WebElement priceTotal = driver.findElement(By.xpath("//div[@class='summary_total_label' and @data-test='total-label']"));
        System.out.println("Total Price : " + priceTotal.getText());
        
        //Complete the Order
        WebElement finishBtn = driver.findElement(By.id("finish"));
        finishBtn.click();
        
        //Order Placed
        WebElement successTxt = driver.findElement(By.xpath("//h2[contains(text(),'Thank you for your order!')]"));
        System.out.println("Order Placed Successfully ? " + successTxt.isDisplayed());
        
        //LogOut
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-burger-menu-btn")));
        menu.click();
        WebElement logout = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Logout']")));
        logout.click();

        WebElement loginButtonVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        System.out.println("User Logged Out? : " + loginButtonVisible.isDisplayed());

    }
}