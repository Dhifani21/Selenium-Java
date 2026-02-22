# 2. Locators - Finding Web Elements

## Overview
Locators are used to identify HTML elements on a web page. Selenium provides multiple locator strategies to find elements.

## 8 Main Locator Strategies

## 1. ID Locator

### What is it?
Locates element by its unique ID attribute. This is the fastest and most reliable method.

### Syntax
```java
WebElement element = driver.findElement(By.id("elementId"));
```

### HTML Example
```html
<input type="text" id="username" name="user" />
```

### Code Example
```java
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class IDLocatorTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com");
        
        // Find element by ID
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("test_user");
        
        driver.quit();
    }
}
```

---

## 2. Name Locator

### What is it?
Locates element by its name attribute. Often used for form fields.

### Syntax
```java
WebElement element = driver.findElement(By.name("elementName"));
```

### HTML Example
```html
<input type="password" name="password" />
```

### Code Example
```java
WebElement password = driver.findElement(By.name("password"));
password.sendKeys("mypassword");
```

---

## 3. Class Name Locator

### What is it?
Locates element by its CSS class name. Useful when ID is not available.

### Syntax
```java
WebElement element = driver.findElement(By.className("className"));
```

### HTML Example
```html
<button class="btn btn-primary">Submit</button>
```

### Code Example
```java
// Find element with specific class
WebElement submitBtn = driver.findElement(By.className("btn-primary"));
submitBtn.click();

// Note: Cannot use multiple class names directly
// For multiple classes, use CSS selector
WebElement element = driver.findElement(By.cssSelector(".btn.btn-primary"));
```

---

## 4. Tag Name Locator

### What is it?
Locates element by its HTML tag name. Returns first matching element.

### Syntax
```java
WebElement element = driver.findElement(By.tagName("tagName"));
```

### HTML Example
```html
<h1>Welcome to my website</h1>
<h2>Subheading</h2>
```

### Code Example
```java
// Find first h1 element
WebElement heading = driver.findElement(By.tagName("h1"));
System.out.println(heading.getText());

// Find all h2 elements
List<WebElement> subHeadings = driver.findElements(By.tagName("h2"));
for (WebElement heading : subHeadings) {
    System.out.println(heading.getText());
}
```

---

## 5. Link Text Locator

### What is it?
Locates hyperlinks by exact link text. Only works for `<a>` tags.

### Syntax
```java
WebElement element = driver.findElement(By.linkText("link text"));
```

### HTML Example
```html
<a href="https://example.com">Click Here</a>
<a href="https://google.com">Search Google</a>
```

### Code Example
```java
// Find link with exact text
WebElement link = driver.findElement(By.linkText("Click Here"));
link.click();
```

---

## 6. Partial Link Text Locator

### What is it?
Locates hyperlinks by partial link text. Useful when full text is unknown.

### Syntax
```java
WebElement element = driver.findElement(By.partialLinkText("partial text"));
```

### HTML Example
```html
<a href="/login">Login to your account</a>
```

### Code Example
```java
// Find link containing "Login"
WebElement loginLink = driver.findElement(By.partialLinkText("Login"));
loginLink.click();
```

---

## 7. CSS Selector Locator

### What is it?
Locates element using CSS selectors. Very powerful and flexible.

### Syntax
```java
WebElement element = driver.findElement(By.cssSelector("css selector"));
```

### Common CSS Selector Patterns

#### By ID
```java
WebElement element = driver.findElement(By.cssSelector("#elementId"));
```

#### By Class
```java
WebElement element = driver.findElement(By.cssSelector(".className"));
```

#### By Attribute
```java
WebElement element = driver.findElement(By.cssSelector("[attribute='value']"));
WebElement element = driver.findElement(By.cssSelector("input[type='text']"));
```

#### By Combination
```java
WebElement element = driver.findElement(By.cssSelector("form.login-form input#username"));
```

#### Child Element
```java
WebElement element = driver.findElement(By.cssSelector("div.container > input"));
```

#### nth-child
```java
WebElement element = driver.findElement(By.cssSelector("table tr:nth-child(2) td:nth-child(1)"));
```

### HTML Example
```html
<form class="login-form">
    <input type="text" id="email" placeholder="Email" />
    <input type="password" id="pass" placeholder="Password" />
    <button type="submit" class="btn-submit">Sign In</button>
</form>
```

### Code Example
```java
// By ID
WebElement email = driver.findElement(By.cssSelector("#email"));

// By class
WebElement submitBtn = driver.findElement(By.cssSelector(".btn-submit"));

// By attribute
WebElement passwordInput = driver.findElement(
    By.cssSelector("input[type='password']"));

// By combination
WebElement emailInput = driver.findElement(
    By.cssSelector("form.login-form input[type='text']"));
```

---

## 8. XPath Locator

### What is it?
Locates element using XML Path Language. Most flexible locator strategy.

### Syntax
```java
WebElement element = driver.findElement(By.xpath("xpath expression"));
```

### Types of XPath

#### Absolute XPath
Complete path from root to element. Not recommended (fragile).

```java
WebElement element = driver.findElement(
    By.xpath("/html/body/div/form/input"));
```

#### Relative XPath
Partial path. Much more flexible (recommended).

```java
WebElement element = driver.findElement(
    By.xpath("//input[@id='username']"));
```

### Common XPath Patterns

#### By ID
```java
WebElement element = driver.findElement(By.xpath("//*[@id='elementId']"));
WebElement element = driver.findElement(By.xpath("//input[@id='username']"));
```

#### By Name
```java
WebElement element = driver.findElement(By.xpath("//*[@name='password']"));
WebElement element = driver.findElement(By.xpath("//input[@name='password']"));
```

#### By Class
```java
WebElement element = driver.findElement(
    By.xpath("//*[@class='className']"));
```

#### By Text
```java
WebElement element = driver.findElement(
    By.xpath("//button[contains(text(), 'Submit')]"));
WebElement element = driver.findElement(
    By.xpath("//a[text()='Exact Link Text']"));
```

#### By Multiple Attributes
```java
WebElement element = driver.findElement(
    By.xpath("//input[@type='text' and @name='username']"));
```

#### By Partial Attribute
```java
WebElement element = driver.findElement(
    By.xpath("//input[starts-with(@id, 'user')]"));
WebElement element = driver.findElement(
    By.xpath("//button[contains(@class, 'btn')]"));
```

#### Child/Parent Navigation
```java
// Child element
WebElement element = driver.findElement(
    By.xpath("//form/input"));

// Parent element
WebElement parent = driver.findElement(
    By.xpath("//input[@id='username']/.."));

// Following sibling
WebElement element = driver.findElement(
    By.xpath("//label[text()='Username']/following-sibling::input"));
```

#### nth-child
```java
WebElement element = driver.findElement(
    By.xpath("//table/tr[2]/td[1]"));
```

#### OR condition
```java
WebElement element = driver.findElement(
    By.xpath("//input[@type='email' or @type='text']"));
```

### HTML Example
```html
<form id="loginForm">
    <input type="text" id="username" name="username" placeholder="Username" />
    <input type="password" id="password" name="password" placeholder="Password" />
    <button type="submit" class="btn-login">Login</button>
    <a href="/forgot">Forgot Password?</a>
</form>
```

### Code Example
```java
// By ID
WebElement username = driver.findElement(
    By.xpath("//input[@id='username']"));

// By name
WebElement password = driver.findElement(
    By.xpath("//input[@name='password']"));

// By text
WebElement loginBtn = driver.findElement(
    By.xpath("//button[text()='Login']"));

// By partial text
WebElement forgotLink = driver.findElement(
    By.xpath("//a[contains(text(), 'Forgot')]"));

// By multiple attributes
WebElement email = driver.findElement(
    By.xpath("//input[@type='text' and @name='username']"));

// By parent-child relationship
WebElement inputInForm = driver.findElement(
    By.xpath("//form[@id='loginForm']//input[@type='text']"));
```

---

## Finding Multiple Elements

When you need to find multiple elements:

```java
import java.util.List;

List<WebElement> elements = driver.findElements(By.tagName("input"));
System.out.println("Number of input fields: " + elements.size());

for (WebElement element : elements) {
    System.out.println(element.getAttribute("name"));
}
```

---

## Locator Strategy Comparison

| Locator | Speed | Reliability | Use Case |
|---------|-------|-------------|----------|
| ID | Very Fast | Very Reliable | When ID is available |
| Name | Fast | Reliable | Form fields |
| Class | Fast | Moderate | CSS-based styling |
| Tag | Fast | Low | Generic elements |
| Link Text | Fast | Reliable | Links with text |
| Partial Link | Fast | Moderate | Partial link text |
| CSS Selector | Fast | Very Reliable | Complex selectors |
| XPath | Slower | Very Reliable | Complex scenarios |

---

## Best Practices

✓ Use ID if available (fastest)  
✓ Use CSS Selector for speed and flexibility  
✓ Use XPath for complex scenarios  
✓ Avoid absolute XPath (fragile)  
✓ Use relative selectors  
✓ Keep selectors simple and readable  
✓ Avoid relying on element text (can change)  

## Tips for Finding Locators

1. **Inspect Element**: Right-click on element → Inspect
2. **Use Console**: Test XPath/CSS in browser console: `$x("xpath")` or `$$("css")`
3. **Browser DevTools**: Use Elements tab to analyze HTML structure
4. **Chrome DevTools**: Press F12 and use the element picker

## Common Mistakes

❌ Using absolute XPath  
❌ Hardcoding element indices  
❌ Relying on element order  
❌ Using fragile selectors  
✓ Use relative selectors and meaningful attributes