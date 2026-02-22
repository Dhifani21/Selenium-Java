# 9. Best Practices

## Overview
Best practices help write maintainable, reliable, and efficient Selenium tests.

## 1. Page Object Model (POM)

### What is POM?
Design pattern that separates test logic from page locators.

### Benefits
- Maintainability
- Reusability
- Readability
- Reduced duplication

### Basic POM Structure

#### HomePage.java
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

public class HomePage {
    private WebDriver driver;
    
    // Page locators
    private By searchBox = By.id("search");
    private By searchButton = By.id("searchBtn");
    private By logo = By.className("logo");
    
    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }
    
    // Page actions/methods
    public void enterSearchText(String text) {
        WebElement search = driver.findElement(searchBox);
        search.clear();
        search.sendKeys(text);
    }
    
    public void clickSearchButton() {
        WebElement button = driver.findElement(searchButton);
        button.click();
    }
    
    public void performSearch(String searchText) {
        enterSearchText(searchText);
        clickSearchButton();
    }
    
    public boolean isLogoDisplayed() {
        return driver.findElement(logo).isDisplayed();
    }
}
```

#### LoginPage.java
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

public class LoginPage {
    private WebDriver driver;
    
    // Page locators
    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.id("loginBtn");
    private By errorMessage = By.className("error");
    
    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    
    // Page methods
    public void enterUsername(String username) {
        WebElement user = driver.findElement(usernameField);
        user.clear();
        user.sendKeys(username);
    }
    
    public void enterPassword(String password) {
        WebElement pass = driver.findElement(passwordField);
        pass.clear();
        pass.sendKeys(password);
    }
    
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }
    
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
    
    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }
    
    public boolean isErrorMessageDisplayed() {
        try {
            return driver.findElement(errorMessage).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
}
```

#### Test Using POM
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginTest {
    private WebDriver driver;
    private LoginPage loginPage;
    
    public void setUp() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        driver.get("https://example.com/login");
    }
    
    public void testSuccessfulLogin() {
        loginPage.login("testuser", "password123");
        // Verify login successful
    }
    
    public void testInvalidLogin() {
        loginPage.login("invaliduser", "wrongpassword");
        assert loginPage.isErrorMessageDisplayed();
        System.out.println("Error: " + loginPage.getErrorMessage());
    }
    
    public void tearDown() {
        driver.quit();
    }
}
```

---

## 2. Exception Handling

### Code Example
```java
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.StaleElementReferenceException;

public class ExceptionHandlingTest {
    private WebDriver driver;
    
    public void testWithProperException() {
        driver = new ChromeDriver();
        
        try {
            driver.get("https://example.com");
            
            // May throw NoSuchElementException
            WebElement element = driver.findElement(By.id("element"));
            element.click();
            
        } catch (NoSuchElementException e) {
            System.out.println("Element not found: " + e.getMessage());
        } catch (TimeoutException e) {
            System.out.println("Timeout waiting for element: " + e.getMessage());
        } catch (StaleElementReferenceException e) {
            System.out.println("Element reference is stale: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        } finally {
            // Always close browser
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
```

---

## 3. Test Data Management

### Using External Data (CSV/Excel)

```java
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class TestDataReader {
    public static List<String[]> readCSV(String filePath) {
        List<String[]> data = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(
            new FileReader(filePath))) {
            
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                data.add(values);
            }
            
        } catch (Exception e) {
            System.out.println("Error reading CSV: " + e.getMessage());
        }
        
        return data;
    }
    
    // Usage in tests
    public void testWithDataFromCSV() {
        List<String[]> testData = readCSV("testdata.csv");
        
        for (String[] data : testData) {
            String username = data[0];
            String password = data[1];
            // Run test with this data
        }
    }
}
```

---

## 4. Test Organization and Structure

### Recommended Folder Structure
```
project/
├── src/
│   └── test/
│       ├── java/
│       │   ├── pages/
│       │   │   ├── HomePage.java
│       │   │   ├── LoginPage.java
│       │   │   └── DashboardPage.java
│       │   ├── tests/
│       │   │   ├── LoginTest.java
│       │   │   ├── SearchTest.java
│       │   │   └── CheckoutTest.java
│       │   ├── utils/
│       │   │   ├── TestUtil.java
│       │   │   ├── ConfigReader.java
│       │   │   └── ScreenshotUtil.java
│       │   └── base/
│       │       └── BaseTest.java
│       └── resources/
│           ├── config.properties
│           ├── testdata.csv
│           └── log4j.properties
└── pom.xml
```

---

## 5. Base Test Class

```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.log4j.Logger;

public class BaseTest {
    protected WebDriver driver;
    protected Logger logger = Logger.getLogger(BaseTest.class);
    
    public void setUp() {
        // Initialize driver
        driver = new ChromeDriver();
        logger.info("Browser launched");
        
        // Set implicit wait
        driver.manage().timeouts().implicitlyWait(
            java.time.Duration.ofSeconds(10));
        
        // Maximize window
        driver.manage().window().maximize();
        logger.info("Window maximized");
    }
    
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed");
        }
    }
    
    public void navigateTo(String url) {
        driver.get(url);
        logger.info("Navigated to: " + url);
    }
}
```

### Using Base Test Class
```java
public class LoginTest extends BaseTest {
    private LoginPage loginPage;
    
    public void setUp() {
        super.setUp();
        navigateTo("https://example.com/login");
        loginPage = new LoginPage(driver);
    }
    
    public void testValidLogin() {
        loginPage.login("testuser", "password123");
        assert loginPage.isLoginSuccessful();
        logger.info("Test passed");
    }
    
    public void tearDown() {
        super.tearDown();
    }
}
```

---

## 6. Configuration Management

### config.properties
```properties
# Browser Configuration
browser=chrome
url=https://example.com
implicit.wait=10
explicit.wait=20

# Test Data
username=testuser
password=password123

# Logging
log.level=INFO
log.path=./logs/
```

### ConfigReader.java
```java
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    
    static {
        try {
            FileInputStream file = new FileInputStream(
                "src/test/resources/config.properties");
            properties = new Properties();
            properties.load(file);
        } catch (Exception e) {
            System.out.println("Error loading config: " + e.getMessage());
        }
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}

// Usage
String browser = ConfigReader.getProperty("browser");
String url = ConfigReader.getProperty("url");
```

---

## 7. Synchronization Best Practices

```java
public class WaitHelper {
    private WebDriver driver;
    private WebDriverWait wait;
    
    public WaitHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 
            java.time.Duration.ofSeconds(10));
    }
    
    public WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    public void waitForElementToDisappear(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    
    public void waitForUrlContains(String urlPart) {
        wait.until(ExpectedConditions.urlContains(urlPart));
    }
}
```

---

## 8. Performance Tips

### 1. Use Efficient Locators
```java
// GOOD - Specific ID
WebElement element = driver.findElement(By.id("username"));

// BAD - Absolute XPath (fragile)
WebElement element = driver.findElement(
    By.xpath("/html/body/div[1]/form/input"));

// GOOD - Relative XPath
WebElement element = driver.findElement(
    By.xpath("//input[@id='username']"));
```

### 2. Minimize Network Calls
```java
// Take one screenshot, not multiple
String screenshot = takeScreenshot("testname");

// Reuse driver instance
// Don't create new driver for each test
```

### 3. Parallel Test Execution
```xml
<!-- In pom.xml for TestNG -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.22.0</version>
    <configuration>
        <parallel>methods</parallel>
        <threadCount>4</threadCount>
    </configuration>
</plugin>
```

---

## 9. Common Pitfalls to Avoid

```java
// ❌ WRONG - Hard waits
Thread.sleep(5000);

// ✓ CORRECT - Explicit waits
WebDriverWait wait
