# 3. WebElement Interactions

## Overview
WebElement interactions refer to the actions you can perform on web elements like clicking, typing, submitting forms, etc.

## Basic Interactions

### 1. Click Element

#### What is it?
Performs a click action on a web element.

#### Syntax
```java
WebElement element = driver.findElement(By.id("elementId"));
element.click();
```

#### Code Example
```java
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ClickElementTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com");
        
        // Click a button
        WebElement submitBtn = driver.findElement(By.id("submitBtn"));
        submitBtn.click();
        
        // Click a link
        WebElement link = driver.findElement(By.linkText("Click Here"));
        link.click();
        
        driver.quit();
    }
}
```

---

### 2. SendKeys (Type Text)

#### What is it?
Types text into an element (usually text input fields).

#### Syntax
```java
WebElement element = driver.findElement(By.id("elementId"));
element.sendKeys("text to type");
```

#### Code Example
```java
// Type in text field
WebElement username = driver.findElement(By.id("username"));
username.sendKeys("john_doe");

// Type in password field
WebElement password = driver.findElement(By.id("password"));
password.sendKeys("SecurePassword123");

// Type multiple lines
WebElement textarea = driver.findElement(By.id("comments"));
textarea.sendKeys("Line 1\nLine 2\nLine 3");

// Type with special characters
WebElement email = driver.findElement(By.name("email"));
email.sendKeys("test@example.com");
```

---

### 3. Clear Text

#### What is it?
Clears existing text from an input field.

#### Syntax
```java
WebElement element = driver.findElement(By.id("elementId"));
element.clear();
```

#### Code Example
```java
WebElement searchBox = driver.findElement(By.id("searchBox"));
searchBox.sendKeys("Initial text");
Thread.sleep(2000);

// Clear the text
searchBox.clear();

// Type new text
searchBox.sendKeys("New search text");
```

#### Handling Stubborn Fields
```java
// Method 1: Select all and delete
WebElement field = driver.findElement(By.id("field"));
field.sendKeys(Keys.CONTROL + "a");
field.sendKeys(Keys.DELETE);

// Method 2: Using JavaScript
JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("arguments[0].value='';", field);
```

---

### 4. Submit Form

#### What is it?
Submits a form element (equivalent to clicking submit button).

#### Syntax
```java
WebElement form = driver.findElement(By.id("formId"));
form.submit();
```

#### Code Example
```java
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FormSubmitTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com/login");
        
        // Find form
        WebElement form = driver.findElement(By.id("loginForm"));
        
        // Fill form fields
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("testuser");
        
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("password123");
        
        // Submit form
        form.submit();
        
        Thread.sleep(3000);
        driver.quit();
    }
}
```

---

## Getting Element Information

### 1. Get Text

#### Syntax
```java
String text = element.getText();
```

#### Code Example
```java
WebElement heading = driver.findElement(By.id("pageTitle"));
String headingText = heading.getText();
System.out.println("Heading: " + headingText);

// Get text from all paragraphs
List<WebElement> paragraphs = driver.findElements(By.tagName("p"));
for (WebElement p : paragraphs) {
    System.out.println(p.getText());
}
```

---

### 2. Get Attribute

#### Syntax
```java
String value = element.getAttribute("attributeName");
```

#### Code Example
```java
// Get value attribute
WebElement input = driver.findElement(By.id("email"));
String value = input.getAttribute("value");
System.out.println("Input value: " + value);

// Get href from link
WebElement link = driver.findElement(By.tagName("a"));
String href = link.getAttribute("href");
System.out.println("Link URL: " + href);

// Get placeholder
String placeholder = input.getAttribute("placeholder");
System.out.println("Placeholder: " + placeholder);

// Get class attribute
String classAttribute = input.getAttribute("class");
System.out.println("Classes: " + classAttribute);

// Get id attribute
String id = input.getAttribute("id");
System.out.println("Element ID: " + id);
```

---

### 3. Get CSS Value

#### Syntax
```java
String value = element.getCssValue("cssProperty");
```

#### Code Example
```java
WebElement button = driver.findElement(By.id("submitBtn"));

// Get background color
String bgColor = button.getCssValue("background-color");
System.out.println("Background Color: " + bgColor);

// Get font size
String fontSize = button.getCssValue("font-size");
System.out.println("Font Size: " + fontSize);

// Get font family
String fontFamily = button.getCssValue("font-family");
System.out.println("Font Family: " + fontFamily);

// Get color
String textColor = button.getCssValue("color");
System.out.println("Text Color: " + textColor);
```

---

## Element State Verification

### 1. IsDisplayed

#### What is it?
Checks if element is visible on the page.

#### Syntax
```java
boolean isDisplayed = element.isDisplayed();
```

#### Code Example
```java
WebElement element = driver.findElement(By.id("myElement"));

if (element.isDisplayed()) {
    System.out.println("Element is visible");
} else {
    System.out.println("Element is not visible");
}

// Wait for element to be displayed
try {
    if (element.isDisplayed()) {
        element.click();
    }
} catch (NoSuchElementException e) {
    System.out.println("Element not found");
}
```

---

### 2. IsEnabled

#### What is it?
Checks if element is enabled (not disabled).

#### Syntax
```java
boolean isEnabled = element.isEnabled();
```

#### Code Example
```java
WebElement button = driver.findElement(By.id("submitBtn"));

if (button.isEnabled()) {
    System.out.println("Button is enabled");
    button.click();
} else {
    System.out.println("Button is disabled");
}
```

---

### 3. IsSelected

#### What is it?
Checks if element is selected (for checkboxes, radio buttons, options).

#### Syntax
```java
boolean isSelected = element.isSelected();
```

#### Code Example
```java
// Check if checkbox is selected
WebElement checkbox = driver.findElement(By.id("agreeCheckbox"));

if (checkbox.isSelected()) {
    System.out.println("Checkbox is selected");
} else {
    System.out.println("Checkbox is not selected");
    checkbox.click(); // Select it
}

// Check radio button
WebElement radioBtn = driver.findElement(By.id("option1"));
if (!radioBtn.isSelected()) {
    radioBtn.click();
}

// Check dropdown option
List<WebElement> options = driver.findElements(By.tagName("option"));
for (WebElement option : options) {
    if (option.isSelected()) {
        System.out.println("Selected: " + option.getText());
    }
}
```

---

## Get Element Size and Location

### 1. Get Size

#### Syntax
```java
Dimension size = element.getSize();
int width = size.getWidth();
int height = size.getHeight();
```

#### Code Example
```java
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

WebElement button = driver.findElement(By.id("submitBtn"));
Dimension size = button.getSize();

System.out.println("Width: " + size.getWidth());
System.out.println("Height: " + size.getHeight());
```

---

### 2. Get Location

#### Syntax
```java
Point location = element.getLocation();
int x = location.getX();
int y = location.getY();
```

#### Code Example
```java
WebElement element = driver.findElement(By.id("myElement"));
Point location = element.getLocation();

System.out.println("X coordinate: " + location.getX());
System.out.println("Y coordinate: " + location.getY());
```

---

### 3. Get Rectangle (Size + Location)

#### Syntax
```java
Rectangle rect = element.getRect();
```

#### Code Example
```java
import org.openqa.selenium.Rectangle;

WebElement element = driver.findElement(By.id("myElement"));
Rectangle rect = element.getRect();

System.out.println("X: " + rect.getX());
System.out.println("Y: " + rect.getY());
System.out.println("Width: " + rect.getWidth());
System.out.println("Height: " + rect.getHeight());
```

---

## Complete Interaction Example

```java
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

public class ElementInteractionTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com");
        
        // Find element
        WebElement email = driver.findElement(By.id("email"));
        
        // Get element information
        System.out.println("Placeholder: " + email.getAttribute("placeholder"));
        System.out.println("Type: " + email.getAttribute("type"));
        
        // Check state
        if (email.isDisplayed() && email.isEnabled()) {
            // Clear and type
            email.clear();
            email.sendKeys("test@example.com");
            
            Thread.sleep(1000);
        }
        
        // Get element details
        Dimension size = email.getSize();
        Point location = email.getLocation();
        
        System.out.println("Size - Width: " + size.getWidth() + 
                          ", Height: " + size.getHeight());
        System.out.println("Location - X: " + location.getX() + 
                          ", Y: " + location.getY());
        
        // Get CSS values
        System.out.println("Font Size: " + email.getCssValue("font-size"));
        System.out.println("Color: " + email.getCssValue("color"));
        
        // Get text
        System.out.println("Text: " + email.getText());
        
        driver.quit();
    }
}
```

---

## Key Points to Remember

✓ Use `click()` for buttons and links  
✓ Use `sendKeys()` for typing in fields  
✓ Use `clear()` before typing in populated fields  
✓ Use `submit()` for form submission  
✓ Use `isDisplayed()` to check visibility  
✓ Use `isEnabled()` to check if clickable  
✓ Use `getText()` to get element text  
✓ Use `getAttribute()` to get attribute values  
✓ Always verify element state before interaction  

## Common Issues

❌ NoSuchElementException - Element not found  
❌ StaleElementReferenceException - Element no longer in DOM  
❌ ElementNotInteractableException - Element not visible or clickable  

✓ Use waits to ensure element is ready  
✓ Refresh element reference after navigation  
✓ Ensure element is visible before interaction