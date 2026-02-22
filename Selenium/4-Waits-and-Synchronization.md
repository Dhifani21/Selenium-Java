# 4. Waits and Synchronization

## Overview
Waits are crucial in Selenium because web elements may not load immediately. Synchronization ensures your tests run reliably.

## Types of Waits

### 1. Implicit Wait

#### What is it?
Tells WebDriver to wait for a specified duration before throwing `NoSuchElementException`.

#### Syntax
```java
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
```

#### Code Example
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import java.time.Duration;

public class ImplicitWaitTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        
        // Set implicit wait - applies to all elements
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        driver.get("https://example.com");
        
        // Will wait up to 10 seconds for this element
        driver.findElement(By.id("dynamicElement"));
        
        driver.quit();
    }
}
```

#### Advantages
- Simple to implement
- Applies to all element finding operations
- Good for quick automation

#### Disadvantages
- Waits same time for every element (inefficient)
- Cannot handle conditional waits
- Less control over wait conditions

---

### 2. Explicit Wait

#### What is it?
Waits for a specific condition to be true before proceeding. More flexible than implicit wait.

#### Syntax
```java
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("elementId")));
```

#### Code Example
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class ExplicitWaitTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        
        // Create WebDriverWait object
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        driver.get("https://example.com");
        
        // Wait for element to be present in DOM
        WebElement element = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.id("dynamicElement"))
        );
        
        // Wait for element to be visible
        element = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("dynamicElement"))
        );
        
        // Wait for element to be clickable
        element = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("button"))
        );
        
        element.click();
        
        driver.quit();
    }
}
```

#### Common Expected Conditions

```java
// Element presence - element exists in DOM
wait.until(ExpectedConditions.presenceOfElementLocated(By.id("element")));

// Element visible - element is visible on page
wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("element")));

// Element clickable - element is visible and enabled
wait.until(ExpectedConditions.elementToBeClickable(By.id("button")));

// Element selected
wait.until(ExpectedConditions.elementToBeSelected(checkbox));

// Text in element
wait.until(ExpectedConditions.textToBePresentInElement(
    element, "Expected Text"));

// Text in element value attribute
wait.until(ExpectedConditions.textToBePresentInElementValue(
    inputElement, "Expected Value"));

// Invisibility of element
wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("element")));

// Number of elements
wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
    By.tagName("li"), 5));

// URL contains
wait.until(ExpectedConditions.urlContains("example.com"));

// Title contains
wait.until(ExpectedConditions.titleContains("Welcome"));

// Presence of all elements
wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
    By.tagName("option")));

// Staleness of element
wait.until(ExpectedConditions.stalenessOf(element));
```

---

### 3. Fluent Wait

#### What is it?
More flexible than explicit wait. Allows custom polling frequency and exceptions to ignore.

#### Syntax
```java
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;

FluentWait<WebDriver> wait = new FluentWait<>(driver)
    .withTimeout(Duration.ofSeconds(10))
    .pollingEvery(Duration.ofMillis(500))
    .ignoring(NoSuchElementException.class);

WebElement element = wait.until(
    ExpectedConditions.presenceOfElementLocated(By.id("element"))
);
```

#### Code Example
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class FluentWaitTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        
        // Create FluentWait
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(10))      // Max time to wait
            .pollingEvery(Duration.ofMillis(500))     // Check every 500ms
            .ignoring(NoSuchElementException.class);  // Ignore this exception
        
        driver.get("https://example.com");
        
        // Wait for element
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("dynamicElement")));
        
        driver.quit();
    }
}
```

#### Advantages
- Custom polling frequency
- Can ignore specific exceptions
- Very flexible

---

## Wait Strategies Comparison

| Strategy | Speed | Reliability | Use Case |
|----------|-------|-------------|----------|
| Implicit Wait | Medium | Low | Simple scenarios |
| Explicit Wait | Fast | High | Dynamic elements |
| Fluent Wait | Fast | Very High | Complex conditions |

---

## Complete Synchronization Example

```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;

public class SynchronizationTest {
    private WebDriver driver;
    private WebDriverWait explicitWait;
    
    public void setUp() {
        driver = new ChromeDriver();
        
        // Set implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        
        // Create explicit wait
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    public void waitForElementAndClick() {
        driver.get("https://example.com");
        
        // Wait for button to be clickable
        WebElement button = explicitWait.until(
            ExpectedConditions.elementToBeClickable(By.id("submitBtn"))
        );
        button.click();
    }
    
    public void waitForMultipleElements() {
        // Wait for multiple elements
        explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
            By.tagName("li")
        ));
    }
    
    public void customWait() {
        // Use FluentWait for custom polling
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(15))
            .pollingEvery(Duration.ofMillis(250))
            .ignoring(NoSuchElementException.class);
        
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.id("customElement")
        ));
    }
    
    public void tearDown() {
        driver.quit();
    }
    
    public static void main(String[] args) {
        SynchronizationTest test = new SynchronizationTest();
        test.setUp();
        test.waitForElementAndClick();
        test.waitForMultipleElements();
        test.customWait();
        test.tearDown();
    }
}
```

---

## Wait Configuration Best Practices

```java
public class WaitHelper {
    private WebDriver driver;
    private WebDriverWait wait;
    
    public WaitHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Optional: Also set implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
    
    // Helper method to wait for element presence
    public WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    
    // Helper method to wait for element visibility
    public WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    // Helper method to wait for element to be clickable
    public WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    // Helper method to wait for element to disappear
    public void waitForElementToDisappear(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    
    // Helper method to wait for URL
    public void waitForUrlContains(String urlPart) {
        wait.until(ExpectedConditions.urlContains(urlPart));
    }
}

// Usage
WaitHelper waitHelper = new WaitHelper(driver);
WebElement element = waitHelper.waitForElementVisible(By.id("myElement"));
waitHelper.waitForElementToDisappear(By.id("loader"));
```

---

## Key Points to Remember

✓ Use explicit wait for dynamic elements  
✓ Avoid mixing implicit and explicit waits  
✓ Set reasonable timeout values  
✓ Use appropriate expected conditions  
✓ Create helper methods for reusable waits  
✓ Handle exceptions properly  

## Common Mistakes

❌ Setting very high timeout values  
❌ Using only implicit waits  
❌ Not waiting for elements before interaction  
❌ Waiting for wrong condition  

✓ Use explicit waits for better control  
✓ Set timeout to expected load time  
✓ Always verify element readiness