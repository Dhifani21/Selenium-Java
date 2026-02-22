# 1. WebDriver Basics

## Overview
WebDriver is a tool for automated testing of web applications. It provides a platform and language-neutral wire protocol as a way to control the behavior of web browsers.

## Prerequisites
- Java JDK 8 or higher installed
- IDE (IntelliJ IDEA, Eclipse, or VS Code)
- Maven or Gradle for dependency management
- Chrome/Firefox/Edge browser installed

## Step 1: Install Selenium Dependencies

### Maven (pom.xml)
```xml
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>4.15.0</version>
</dependency>
```

### Gradle (build.gradle)
```gradle
dependencies {
    implementation 'org.seleniumhq.selenium:selenium-java:4.15.0'
}
```

## Step 2: Download WebDriver

### ChromeDriver
1. Visit: https://chromedriver.chromium.org/
2. Download version matching your Chrome browser version
3. Extract and note the path

### Other Drivers
- **Firefox**: https://github.com/mozilla/geckodriver/releases
- **Edge**: https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/

## Step 3: Basic WebDriver Setup

### Simple Example
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BasicWebDriver {
    public static void main(String[] args) {
        // Set path to chromedriver
        System.setProperty("webdriver.chrome.driver", 
            "/path/to/chromedriver");
        
        // Initialize ChromeDriver
        WebDriver driver = new ChromeDriver();
        
        // Navigate to website
        driver.get("https://www.google.com");
        
        // Get page title
        System.out.println("Page Title: " + driver.getTitle());
        
        // Close the browser
        driver.quit();
    }
}
```

## Step 4: WebDriver Methods

### Navigation Methods
```java
driver.get("https://www.example.com");           // Go to URL
driver.navigate().to("https://www.example.com"); // Alternative way
driver.navigate().back();                        // Go back
driver.navigate().forward();                     // Go forward
driver.navigate().refresh();                     // Refresh page
```

### Window Management
```java
driver.manage().window().maximize();  // Maximize window
driver.manage().window().minimize();  // Minimize window
driver.manage().window().fullscreen(); // Full screen

// Set specific dimensions
driver.manage().window().setSize(new Dimension(1024, 768));

// Get window size
Dimension size = driver.manage().window().getSize();
System.out.println("Width: " + size.getWidth());
System.out.println("Height: " + size.getHeight());
```

### Browser Information
```java
String title = driver.getTitle();        // Get page title
String url = driver.getCurrentUrl();     // Get current URL
String pageSource = driver.getPageSource(); // Get page HTML source
```

## Step 5: Different Browser Setup

### Chrome
```java
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

ChromeOptions options = new ChromeOptions();
options.addArguments("--start-maximized");
options.addArguments("--headless"); // Run in background
options.addArguments("--disable-notifications");

WebDriver driver = new ChromeDriver(options);
```

### Firefox
```java
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

FirefoxOptions options = new FirefoxOptions();
options.addArguments("--headless");

WebDriver driver = new FirefoxDriver(options);
```

### Edge
```java
import org.openqa.selenium.edge.EdgeDriver;

WebDriver driver = new EdgeDriver();
```

## Step 6: Browser Options/Capabilities

### Common Chrome Options
```java
ChromeOptions options = new ChromeOptions();

// Run headless (no UI)
options.addArguments("--headless");

// Disable notifications
options.addArguments("--disable-notifications");

// Disable GPU acceleration
options.addArguments("--disable-gpu");

// Run in incognito mode
options.addArguments("--incognito");

// Set user data directory
options.addArguments("user-data-dir=/path/to/profile");

// Accept insecure certificates
options.setAcceptInsecureCerts(true);

// Set window size
options.addArguments("--window-size=1920,1080");

WebDriver driver = new ChromeDriver(options);
```

## Step 7: Close Browser

```java
// Closes current window
driver.close();

// Closes all windows and ends session
driver.quit();
```

## Complete Example

```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverBasics {
    public static void main(String[] args) {
        // Setup Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        
        // Initialize driver
        WebDriver driver = new ChromeDriver(options);
        
        try {
            // Navigate to website
            driver.get("https://www.google.com");
            
            // Print page details
            System.out.println("Title: " + driver.getTitle());
            System.out.println("URL: " + driver.getCurrentUrl());
            
            // Wait to see the page
            Thread.sleep(3000);
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Always close the browser
            driver.quit();
        }
    }
}
```

## Key Points to Remember

✓ Always use `driver.quit()` to close all windows  
✓ Use `driver.close()` only to close current window  
✓ Set WebDriver path before creating driver instance  
✓ Handle exceptions properly  
✓ Use try-finally to ensure driver closes  
✓ Chrome and Firefox are most commonly used  

## Common Errors & Solutions

### Error: "chromedriver" executable needs to be in PATH
**Solution:** Set the property explicitly:
```java
System.setProperty("webdriver.chrome.driver", "/full/path/to/chromedriver");
```

### Error: Chrome version mismatch
**Solution:** Download ChromeDriver matching your Chrome version

### Error: Port in use
**Solution:** Wait a moment before running again or use different port

## Next Steps
- Learn about locators (how to find elements)
- Practice with different websites
- Explore WebDriver wait strategies