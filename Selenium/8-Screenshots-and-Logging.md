# 8. Screenshots and Logging

## Overview
Screenshots and logging are essential for debugging test failures and documenting test execution.

## Taking Screenshots

### 1. Basic Screenshot

#### Syntax
```java
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import java.io.File;
import org.apache.commons.io.FileUtils;

TakesScreenshot screenshot = (TakesScreenshot) driver;
File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
FileUtils.copyFile(srcFile, new File("screenshot.png"));
```

#### Code Example
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;
import org.apache.commons.io.FileUtils;

public class ScreenshotTest {
    public static void main(String[] args) throws Exception {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com");
        
        // Take screenshot
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
        
        // Save to file
        File destFile = new File("screenshot.png");
        FileUtils.copyFile(srcFile, destFile);
        
        System.out.println("Screenshot saved: " + destFile.getAbsolutePath());
        
        driver.quit();
    }
}
```

---

### 2. Screenshot with Timestamp

#### Code Example
```java
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import org.apache.commons.io.FileUtils;

public class ScreenshotWithTimestamp {
    public static void main(String[] args) throws Exception {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com");
        
        // Generate timestamp
        SimpleDateFormat dateFormat = 
            new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timestamp = dateFormat.format(new Date());
        
        // Take screenshot with timestamp
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
        File destFile = new File("screenshots/screenshot_" + timestamp + ".png");
        
        // Create directory if not exists
        destFile.getParentFile().mkdirs();
        
        FileUtils.copyFile(srcFile, destFile);
        
        System.out.println("Screenshot saved: " + destFile.getAbsolutePath());
        
        driver.quit();
    }
}
```

---

### 3. Screenshot of Specific Element

#### Code Example
```java
import org.openqa.selenium.WebElement;
import java.io.File;
import org.apache.commons.io.FileUtils;

WebElement element = driver.findElement(By.id("myElement"));

// Get screenshot of specific element
File srcFile = element.getScreenshotAs(OutputType.FILE);
FileUtils.copyFile(srcFile, new File("element_screenshot.png"));
```

---

## Logging with Log4j

### 1. Setup Log4j

#### Add Dependency (pom.xml)
```xml
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```

---

### 2. Create log4j.properties

```properties
# Define the root logger with appender file
log4j.rootLogger = INFO, FILE, CONSOLE

# Define the file appender
log4j.appender.FILE=org.apache.log4j.FileAppender
log4j.appender.FILE.File=./logs/selenium.log
log4j.appender.FILE.layout=org.apache.log4j.PatternLayout
log4j.appender.FILE.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n

# Define the console appender
log4j.appender.CONSOLE=org.apache.log4j.ConsoleAppender
log4j.appender.CONSOLE.layout=org.apache.log4j.PatternLayout
log4j.appender.CONSOLE.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n
```

---

### 3. Using Log4j in Tests

#### Code Example
```java
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoggingTest {
    private static Logger logger = Logger.getLogger(LoggingTest.class);
    
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        logger.info("Browser launched");
        
        try {
            driver.get("https://example.com");
            logger.info("Navigated to: " + driver.getCurrentUrl());
            
            WebElement searchBox = driver.findElement(By.id("search"));
            logger.debug("Search box found");
            
            searchBox.sendKeys("Selenium");
            logger.info("Entered search text: Selenium");
            
            searchBox.submit();
            logger.info("Search submitted");
            
        } catch (Exception e) {
            logger.error("Test failed with error: ", e);
        } finally {
            driver.quit();
            logger.info("Browser closed");
        }
    }
}
```

---

## Complete Screenshot and Logging Example

```java
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;

public class ScreenshotLoggingTest {
    private static Logger logger = Logger.getLogger(ScreenshotLoggingTest.class);
    private WebDriver driver;
    private String screenshotPath = "screenshots/";
    
    public void setUp() {
        driver = new ChromeDriver();
        logger.info("===== Test Started =====");
        logger.info("Browser launched successfully");
    }
    
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed");
            logger.info("===== Test Ended =====");
        }
    }
    
    public String takeScreenshot(String screenshotName) {
        try {
            // Generate timestamp
            SimpleDateFormat dateFormat = 
                new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String timestamp = dateFormat.format(new Date());
            
            // Take screenshot
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            
            // Create directory if not exists
            File dir = new File(screenshotPath);
            dir.mkdirs();
            
            // Save screenshot
            String fileName = screenshotPath + screenshotName + "_" + 
                            timestamp + ".png";
            FileUtils.copyFile(srcFile, new File(fileName));
            
            logger.info("Screenshot saved: " + fileName);
            return fileName;
            
        } catch (Exception e) {
            logger.error("Error taking screenshot: ", e);
            return null;
        }
    }
    
    public void testLogin() {
        try {
            driver.get("https://example.com/login");
            logger.info("Navigated to login page");
            takeScreenshot("login_page");
            
            WebElement username = driver.findElement(By.id("username"));
            logger.debug("Username field found");
            username.sendKeys("testuser");
            logger.info("Entered username");
            
            WebElement password = driver.findElement(By.id("password"));
            logger.debug("Password field found");
            password.sendKeys("password123");
            logger.info("Entered password");
            
            WebElement loginBtn = driver.findElement(By.id("loginBtn"));
            logger.debug("Login button found");
            loginBtn.click();
            logger.info("Login button clicked");
            
            Thread.sleep(2000);
            takeScreenshot("login_success");
            
            logger.info("Test passed");
            
        } catch (Exception e) {
            logger.error("Test failed: ", e);
            takeScreenshot("login_failure");
        }
    }
    
    public static void main(String[] args) {
        ScreenshotLoggingTest test = new ScreenshotLoggingTest();
        test.setUp();
        test.testLogin();
        test.tearDown();
    }
}
```

---

## Error Handling with Screenshots

```java
public class ErrorHandlingTest {
    private static Logger logger = Logger.getLogger(ErrorHandlingTest.class);
    private WebDriver driver;
    
    public void testWithErrorHandling() {
        driver = new ChromeDriver();
        logger.info("Test started");
        
        try {
            driver.get("https://example.com");
            
            WebElement element = driver.findElement(By.id("nonexistent"));
            element.click();
            
        } catch (org.openqa.selenium.NoSuchElementException e) {
            logger.error("Element not found: " + e.getMessage());
            takeScreenshot("error_element_not_found");
        } catch (org.openqa.selenium.TimeoutException e) {
            logger.error("Timeout while waiting for element: " + e.getMessage());
            takeScreenshot("error_timeout");
        } catch (Exception e) {
            logger.error("Unexpected error: " + e.getMessage());
            takeScreenshot("error_unexpected");
        } finally {
            driver.quit();
            logger.info("Test completed");
        }
    }
    
    private void takeScreenshot(String name) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, 
                new File("screenshots/" + name + ".png"));
        } catch (Exception e) {
            logger.error("Failed to take screenshot: ", e);
        }
    }
}
```

---

## Logging Best Practices

```java
public class LoggingBestPractices {
    private static Logger logger = Logger.getLogger(LoggingBestPractices.class);
    
    public void bestPractices() {
        // 1. Log test start
        logger.info("Test: testLoginFunctionality - STARTED");
        
        // 2. Log navigation
        logger.info("Navigating to: https://example.com/login");
        
        // 3. Log element interactions
        logger.debug("Finding element: username input field");
        logger.info("Entering username: testuser");
        
        // 4. Log verification
        logger.info("Verifying: User logged in successfully");
        
        // 5. Log warnings
        logger.warn("Element took longer to load than expected");
        
        // 6. Log errors
        logger.error("Test failed: Element not found", 
            new NoSuchElementException("Element not found"));
        
        // 7. Log test end
        logger.info("Test: testLoginFunctionality - PASSED");
    }
}
```

---

## Screenshot and Logging Utility Class

```java
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;

public class TestUtil {
    private static Logger logger = Logger.getLogger(TestUtil.class);
    
    public static String takeScreenshot(WebDriver driver, String screenshotName) {
        try {
            SimpleDateFormat dateFormat = 
                new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String timestamp = dateFormat.format(new Date());
            
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            
            File dir = new File("screenshots");
            dir.mkdirs();
            
            String fileName = "screenshots/" + screenshotName + "_" + 
                            timestamp + ".png";
            FileUtils.copyFile(srcFile, new File(fileName));
            
            logger.info("Screenshot saved: " + fileName);
            return fileName;
            
        } catch (Exception e) {
            logger.error("Error taking screenshot: ", e);
            return null;
        }
    }
    
    public static void logTestStart(String testName) {
        logger.info("===== " + testName + " STARTED =====");
    }
    
    public static void logTestEnd(String testName, String status) {
        logger.info("===== " + testName + " " + status + " =====");
    }
}
```

---

## Key Points to Remember

✓ Take screenshots on test failures  
✓ Use descriptive log messages  
✓ Include timestamps in logs and screenshots  
✓ Log all important actions  
✓ Use appropriate log levels  
✓ Clean up screenshots periodically  

## Log Levels

- **DEBUG**: Detailed diagnostic information  
- **INFO**: General informational messages  
- **WARN**: Warning messages  
- **ERROR**: Error messages  
- **FATAL**: Fatal error messages  

## Common Issues

❌ Screenshot directory not created  
❌ Log file permissions issues  
❌ Disk space for screenshots  

✓ Create directory structure  
✓ Check file permissions  
✓ Implement cleanup mechanism