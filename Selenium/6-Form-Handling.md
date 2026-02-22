# 6. Form Handling

## Overview
Forms are critical components in web applications. This guide covers all form operations including input, dropdowns, checkboxes, and radio buttons.

## Basic Form Operations

### 1. Filling Text Input Fields

#### Code Example
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class TextInputTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com/form");
        
        // Fill text input
        WebElement firstName = driver.findElement(By.id("firstName"));
        firstName.clear();
        firstName.sendKeys("John");
        
        // Fill email
        WebElement email = driver.findElement(By.id("email"));
        email.clear();
        email.sendKeys("john@example.com");
        
        // Fill textarea
        WebElement comments = driver.findElement(By.id("comments"));
        comments.clear();
        comments.sendKeys("This is a multiline\ncomment");
        
        Thread.sleep(2000);
        driver.quit();
    }
}
```

---

## Dropdown Handling

### 1. Select by Visible Text

#### Syntax
```java
import org.openqa.selenium.support.ui.Select;

Select dropdown = new Select(dropdownElement);
dropdown.selectByVisibleText("Option Text");
```

#### Code Example
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class DropdownTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com/form");
        
        // Find dropdown
        WebElement countryDropdown = driver.findElement(By.id("country"));
        
        // Create Select object
        Select select = new Select(countryDropdown);
        
        // Select by visible text
        select.selectByVisibleText("United States");
        
        Thread.sleep(2000);
        driver.quit();
    }
}
```

---

### 2. Select by Value

#### Syntax
```java
Select dropdown = new Select(dropdownElement);
dropdown.selectByValue("optionValue");
```

#### Code Example
```java
// Select by value attribute
WebElement dropdown = driver.findElement(By.id("dropdown"));
Select select = new Select(dropdown);
select.selectByValue("usa");
```

---

### 3. Select by Index

#### Syntax
```java
Select dropdown = new Select(dropdownElement);
dropdown.selectByIndex(2);
```

#### Code Example
```java
// Select by index (0-based)
Select select = new Select(dropdown);
select.selectByIndex(1); // Selects second option
```

---

### 4. Get All Options

#### Code Example
```java
import java.util.List;

WebElement dropdown = driver.findElement(By.id("dropdown"));
Select select = new Select(dropdown);

// Get all options
List<WebElement> options = select.getOptions();

System.out.println("Total options: " + options.size());

for (WebElement option : options) {
    System.out.println(option.getText());
}
```

---

### 5. Get Selected Option

#### Code Example
```java
Select select = new Select(dropdown);

// Get currently selected option
WebElement selectedOption = select.getFirstSelectedOption();
System.out.println("Selected: " + selectedOption.getText());

// Get all selected options (for multi-select)
List<WebElement> selectedOptions = select.getAllSelectedOptions();
for (WebElement option : selectedOptions) {
    System.out.println(option.getText());
}
```

---

### 6. Multi-Select Dropdown

#### Code Example
```java
WebElement multiDropdown = driver.findElement(By.id("multiSelect"));
Select select = new Select(multiDropdown);

// Select multiple options
select.selectByVisibleText("Option 1");
select.selectByVisibleText("Option 2");
select.selectByVisibleText("Option 3");

// Deselect an option
select.deselectByVisibleText("Option 2");

// Check if multi-select
if (select.isMultiple()) {
    System.out.println("This is a multi-select dropdown");
}

// Deselect all
select.deselectAll();
```

---

## Checkbox Handling

### 1. Select Single Checkbox

#### Code Example
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class CheckboxTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com/form");
        
        // Find checkbox
        WebElement checkbox = driver.findElement(By.id("agreeCheckbox"));
        
        // Check if not already selected
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        
        // Verify it's selected
        System.out.println("Checked: " + checkbox.isSelected());
        
        // Uncheck
        checkbox.click();
        
        Thread.sleep(2000);
        driver.quit();
    }
}
```

---

### 2. Select Multiple Checkboxes

#### Code Example
```java
import java.util.List;

// Find all checkboxes
List<WebElement> checkboxes = driver.findElements(By.name("interests"));

// Check all checkboxes
for (WebElement checkbox : checkboxes) {
    if (!checkbox.isSelected()) {
        checkbox.click();
    }
}

// Check specific checkbox by value
for (WebElement checkbox : checkboxes) {
    String value = checkbox.getAttribute("value");
    if (value.equals("java")) {
        checkbox.click();
        break;
    }
}
```

---

### 3. Select Checkbox by Label

#### Code Example
```java
// Find label text and select corresponding checkbox
String labelText = "I agree to terms";
WebElement label = driver.findElement(By.xpath("//label[contains(text(), '" + labelText + "')]"));

// Find checkbox associated with this label
String checkboxId = label.getAttribute("for");
WebElement checkbox = driver.findElement(By.id(checkboxId));
checkbox.click();
```

---

## Radio Button Handling

### 1. Select Single Radio Button

#### Code Example
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class RadioButtonTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com/form");
        
        // Find radio button
        WebElement maleRadio = driver.findElement(By.id("male"));
        
        // Select radio button
        if (!maleRadio.isSelected()) {
            maleRadio.click();
        }
        
        // Verify selection
        System.out.println("Selected: " + maleRadio.isSelected());
        
        Thread.sleep(2000);
        driver.quit();
    }
}
```

---

### 2. Select Radio Button by Value

#### Code Example
```java
import java.util.List;

// Find all radio buttons with same name
List<WebElement> radioButtons = driver.findElements(By.name("gender"));

// Select by value
for (WebElement radio : radioButtons) {
    String value = radio.getAttribute("value");
    if (value.equals("female")) {
        radio.click();
        break;
    }
}
```

---

## Complete Form Handling Example

```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class CompleteFormTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com/registration");
        
        // Fill text inputs
        WebElement firstName = driver.findElement(By.id("firstName"));
        firstName.sendKeys("John");
        
        WebElement lastName = driver.findElement(By.id("lastName"));
        lastName.sendKeys("Doe");
        
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("john.doe@example.com");
        
        // Select from dropdown
        WebElement countryDropdown = driver.findElement(By.id("country"));
        Select countrySelect = new Select(countryDropdown);
        countrySelect.selectByVisibleText("United States");
        
        // Select radio button
        List<WebElement> genderRadios = driver.findElements(By.name("gender"));
        for (WebElement radio : genderRadios) {
            if (radio.getAttribute("value").equals("male")) {
                radio.click();
                break;
            }
        }
        
        // Select checkboxes
        List<WebElement> interestCheckboxes = 
            driver.findElements(By.name("interests"));
        for (WebElement checkbox : interestCheckboxes) {
            String value = checkbox.getAttribute("value");
            if (value.equals("java") || value.equals("selenium")) {
                if (!checkbox.isSelected()) {
                    checkbox.click();
                }
            }
        }
        
        // Select multi-select dropdown
        WebElement skillsDropdown = driver.findElement(By.id("skills"));
        Select skillsSelect = new Select(skillsDropdown);
        skillsSelect.selectByVisibleText("Java");
        skillsSelect.selectByVisibleText("Selenium");
        
        // Fill textarea
        WebElement bio = driver.findElement(By.id("bio"));
        bio.sendKeys("I am a QA Automation Engineer");
        
        // Submit form
        WebElement submitBtn = driver.findElement(By.id("submitBtn"));
        submitBtn.click();
        
        Thread.sleep(3000);
        driver.quit();
    }
}
```

---

## Form Validation Example

```java
public class FormValidationTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com/form");
        
        // Check if form field is enabled
        WebElement emailField = driver.findElement(By.id("email"));
        if (emailField.isEnabled()) {
            emailField.sendKeys("test@example.com");
        }
        
        // Check if field is required
        String required = emailField.getAttribute("required");
        if (required != null) {
            System.out.println("Email field is required");
        }
        
        // Get placeholder
        String placeholder = emailField.getAttribute("placeholder");
        System.out.println("Placeholder: " + placeholder);
        
        // Get default value
        String value = emailField.getAttribute("value");
        System.out.println("Default value: " + value);
        
        // Check dropdown if has multiple option
        WebElement dropdown = driver.findElement(By.id("options"));
        Select select = new Select(dropdown);
        System.out.println("Is multiple: " + select.isMultiple());
        
        driver.quit();
    }
}
```

---

## Key Points to Remember

✓ Clear field before entering text  
✓ Always check if checkbox/radio is selected before clicking  
✓ Use Select class for dropdown operations  
✓ Validate form fields before submission  
✓ Handle form errors appropriately  
✓ Use proper waits for form elements  

## Common Issues

❌ NoSuchElementException - Wrong locator  
❌ ElementNotInteractableException - Field disabled  
❌ StaleElementException - Element refreshed  

✓ Use proper waits  
✓ Verify element state before interaction  
✓ Use accurate locators