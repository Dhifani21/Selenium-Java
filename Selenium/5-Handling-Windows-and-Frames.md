# 5. Handling Windows and Frames

## Overview
Web applications often use multiple windows and frames. Selenium provides methods to switch between them.

## Handling Windows (Tabs)

### 1. Get Current Window Handle

#### Syntax
```java
String currentWindow = driver.getWindowHandle();
```

#### Code Example
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WindowHandleTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com");
        
        // Get current window handle
        String currentWindow = driver.getWindowHandle();
        System.out.println("Current window: " + currentWindow);
    }
}
```

---

### 2. Get All Window Handles

#### Syntax
```java
Set<String> allWindows = driver.getWindowHandles();
```

#### Code Example
```java
Set<String> allWindows = driver.getWindowHandles();
System.out.println("Number of windows: " + allWindows.size());

for (String window : allWindows) {
    System.out.println("Window: " + window);
}
```

---

### 3. Switch to Window

#### Syntax
```java
driver.switchTo().window(windowHandle);
```

#### Code Example
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SwitchWindowTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        
        // Store parent window handle
        String parentWindow = driver.getWindowHandle();
        
        driver.get("https://example.com");
        
        // Click link that opens new window
        WebElement link = driver.findElement(By.linkText("Open New Window"));
        link.click();
        
        Thread.sleep(2000);
        
        // Get all windows
        Set<String> allWindows = driver.getWindowHandles();
        
        // Switch to new window
        for (String window : allWindows) {
            if (!window.equals(parentWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
        
        System.out.println("Current URL: " + driver.getCurrentUrl());
        
        // Switch back to parent window
        driver.switchTo().window(parentWindow);
        
        driver.quit();
    }
}
```

---

## Handling Frames/IFrames

### 1. What are Frames?

Frames divide a web page into multiple sections. iFrames are inline frames embedded in a page.

### 2. Switch to Frame by Index

#### Syntax
```java
driver.switchTo().frame(index);
```

#### Code Example
```java
// Switch to first frame (index 0)
driver.switchTo().frame(0);

// Switch to third frame (index 2)
driver.switchTo().frame(2);
```

---

### 3. Switch to Frame by Name or ID

#### Syntax
```java
driver.switchTo().frame("frameName");
driver.switchTo().frame("frameId");
```

#### Code Example
```java
// Switch by name
driver.switchTo().frame("myFrame");

// Switch by ID
driver.switchTo().frame("frameId");
```

---

### 4. Switch to Frame by WebElement

#### Syntax
```java
WebElement frameElement = driver.findElement(By.id("frameId"));
driver.switchTo().frame(frameElement);
```

#### Code Example
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class SwitchFrameTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com");
        
        // Find iframe
        WebElement iframe = driver.findElement(By.id("myFrame"));
        
        // Switch to iframe
        driver.switchTo().frame(iframe);
        
        // Now find elements inside iframe
        WebElement element = driver.findElement(By.id("elementInFrame"));
        element.click();
        
        // Switch back to main content
        driver.switchTo().defaultContent();
        
        driver.quit();
    }
}
```

---

### 5. Switch Back to Main Content

#### Syntax
```java
driver.switchTo().defaultContent();
```

#### Code Example
```java
// Switch to frame
driver.switchTo().frame(0);

// Do some actions in frame
WebElement element = driver.findElement(By.id("frameElement"));
element.click();

// Switch back to main page
driver.switchTo().defaultContent();

// Now interact with main page elements
WebElement mainElement = driver.findElement(By.id("mainElement"));
mainElement.click();
```

---

### 6. Switch Back to Parent Frame

#### Syntax
```java
driver.switchTo().parentFrame();
```

#### Code Example
```java
// Switch to nested frame
driver.switchTo().frame(0);
driver.switchTo().frame(0); // Nested frame

// Switch back to parent frame
driver.switchTo().parentFrame();
```

---

## Complete Window and Frame Example

```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.Set;

public class WindowAndFrameTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        
        // ==== WINDOW HANDLING ====
        
        String parentWindow = driver.getWindowHandle();
        
        driver.get("https://example.com");
        
        // Click link that opens new window
        WebElement newWindowLink = driver.findElement(
            By.linkText("Open in new window"));
        newWindowLink.click();
        
        Thread.sleep(2000);
        
        // Get all windows
        Set<String> allWindows = driver.getWindowHandles();
        System.out.println("Total windows: " + allWindows.size());
        
        // Switch to new window
        for (String window : allWindows) {
            if (!window.equals(parentWindow)) {
                driver.switchTo().window(window);
                System.out.println("New window URL: " + driver.getCurrentUrl());
                
                // Do something in new window
                WebElement element = driver.findElement(By.id("content"));
                System.out.println("Content: " + element.getText());
                
                break;
            }
        }
        
        // Close new window
        driver.close();
        
        // Switch back to parent window
        driver.switchTo().window(parentWindow);
        System.out.println("Back to: " + driver.getCurrentUrl());
        
        
        // ==== FRAME HANDLING ====
        
        // Find and switch to iframe
        WebElement iframe = driver.findElement(By.id("contentFrame"));
        driver.switchTo().frame(iframe);
        
        // Find element inside iframe
        WebElement frameElement = driver.findElement(By.id("frameContent"));
        System.out.println("Frame content: " + frameElement.getText());
        
        // Switch back to main content
        driver.switchTo().defaultContent();
        
        // Interact with main page
        WebElement mainElement = driver.findElement(By.id("mainContent"));
        mainElement.click();
        
        driver.quit();
    }
}
```

---

## Handling Multiple Frames

```java
public class MultipleFramesTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com");
        
        // Get all frames
        java.util.List<WebElement> frames = 
            driver.findElements(By.tagName("iframe"));
        
        System.out.println("Total frames: " + frames.size());
        
        // Iterate through frames
        for (int i = 0; i < frames.size(); i++) {
            // Switch back to main content
            driver.switchTo().defaultContent();
            
            // Get frames again (to avoid stale element)
            frames = driver.findElements(By.tagName("iframe"));
            
            // Switch to frame
            driver.switchTo().frame(frames.get(i));
            
            // Find elements in this frame
            java.util.List<WebElement> elements = 
                driver.findElements(By.tagName("p"));
            
            System.out.println("Frame " + i + " has " + 
                             elements.size() + " paragraphs");
        }
        
        // Switch back to main content
        driver.switchTo().defaultContent();
        
        driver.quit();
    }
}
```

---

## Handling Alert Boxes

### 1. Accept Alert

```java
import org.openqa.selenium.Alert;

// Click button that triggers alert
WebElement alertButton = driver.findElement(By.id("alertBtn"));
alertButton.click();

// Switch to alert
Alert alert = driver.switchTo().alert();

// Get alert text
String alertText = alert.getText();
System.out.println("Alert: " + alertText);

// Accept (Click OK)
alert.accept();
```

---

### 2. Dismiss Alert

```java
// Dismiss (Click Cancel)
alert.dismiss();
```

---

### 3. Send Keys to Alert

```java
// For prompt alert
Alert alert = driver.switchTo().alert();
alert.sendKeys("Your input here");
alert.accept();
```

---

## Alert Handling Example

```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Alert;
import org.openqa.selenium.chrome.ChromeDriver;

public class AlertTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com");
        
        // Handle simple alert
        WebElement alertBtn = driver.findElement(By.id("alertButton"));
        alertBtn.click();
        
        Thread.sleep(1000);
        
        Alert alert = driver.switchTo().alert();
        System.out.println("Alert text: " + alert.getText());
        alert.accept();
        
        
        // Handle confirmation alert
        WebElement confirmBtn = driver.findElement(By.id("confirmButton"));
        confirmBtn.click();
        
        Thread.sleep(1000);
        
        alert = driver.switchTo().alert();
        alert.dismiss(); // Click Cancel
        
        
        // Handle prompt alert
        WebElement promptBtn = driver.findElement(By.id("promptButton"));
        promptBtn.click();
        
        Thread.sleep(1000);
        
        alert = driver.switchTo().alert();
        alert.sendKeys("Test Input");
        alert.accept();
        
        driver.quit();
    }
}
```

---

## Key Points to Remember

✓ Store parent window handle before opening new windows  
✓ Use Set to get all window handles  
✓ Switch back to default content before switching frames  
✓ Use unique locators to identify frames  
✓ Handle alerts immediately after triggering them  
✓ Use try-finally to ensure cleanup  

## Common Issues

❌ NoSuchElementException in frame - Not switched to frame  
❌ StaleElementException - Frame content changed  
❌ Can't find alert - Alert not triggered yet  

✓ Always verify frame exists  
✓ Wait for alert to appear  
✓ Re-fetch elements after navigation