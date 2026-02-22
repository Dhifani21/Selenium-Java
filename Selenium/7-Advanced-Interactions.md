# 7. Advanced Interactions

## Overview
Advanced interactions include mouse movements, keyboard actions, drag-and-drop, and other complex operations using the Actions class.

## Actions Class

### 1. Mouse Hover

#### What is it?
Moves mouse over an element without clicking.

#### Syntax
```java
import org.openqa.selenium.interactions.Actions;

Actions actions = new Actions(driver);
actions.moveToElement(element).perform();
```

#### Code Example
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class MouseHoverTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com");
        
        // Find element
        WebElement menu = driver.findElement(By.id("mainMenu"));
        
        // Create Actions object
        Actions actions = new Actions(driver);
        
        // Hover over element
        actions.moveToElement(menu).perform();
        
        Thread.sleep(2000);
        
        // Now submenu should be visible
        WebElement submenu = driver.findElement(By.id("submenu"));
        System.out.println("Submenu visible: " + submenu.isDisplayed());
        
        driver.quit();
    }
}
```

---

### 2. Double Click

#### What is it?
Performs double-click on an element.

#### Syntax
```java
Actions actions = new Actions(driver);
actions.doubleClick(element).perform();
```

#### Code Example
```java
WebElement element = driver.findElement(By.id("doubleClickElement"));

Actions actions = new Actions(driver);
actions.doubleClick(element).perform();

// Verify action (check if text changed or element highlighted)
System.out.println("Double click performed");
```

---

### 3. Right Click (Context Menu)

#### What is it?
Performs right-click to open context menu.

#### Syntax
```java
Actions actions = new Actions(driver);
actions.contextClick(element).perform();
```

#### Code Example
```java
WebElement rightClickElement = driver.findElement(By.id("contextElement"));

Actions actions = new Actions(driver);
actions.contextClick(rightClickElement).perform();

Thread.sleep(1000);

// Click option from context menu
WebElement contextOption = driver.findElement(By.id("contextOption"));
contextOption.click();
```

---

## Keyboard Actions

### 1. Send Keys

#### What is it?
Simulates keyboard actions like Enter, Tab, Escape, etc.

#### Syntax
```java
actions.sendKeys(Keys.ENTER).perform();
actions.sendKeys(Keys.TAB).perform();
actions.sendKeys(Keys.ESCAPE).perform();
```

#### Code Example
```java
import org.openqa.selenium.Keys;

// Press Enter key
Actions actions = new Actions(driver);
actions.sendKeys(Keys.ENTER).perform();

// Press Tab key
actions.sendKeys(Keys.TAB).perform();

// Press Escape key
actions.sendKeys(Keys.ESCAPE).perform();

// Press Ctrl+A (Select All)
actions.sendKeys(Keys.CONTROL + "a").perform();

// Press Ctrl+C (Copy)
actions.sendKeys(Keys.CONTROL + "c").perform();

// Press Ctrl+V (Paste)
actions.sendKeys(Keys.CONTROL + "v").perform();
```

---

### 2. Keyboard Modifier Keys

#### Code Example
```java
import org.openqa.selenium.Keys;

WebElement element = driver.findElement(By.id("element"));
Actions actions = new Actions(driver);

// Hold Shift and click
actions.keyDown(Keys.SHIFT)
       .click(element)
       .keyUp(Keys.SHIFT)
       .perform();

// Hold Control and click
actions.keyDown(Keys.CONTROL)
       .click(element)
       .keyUp(Keys.CONTROL)
       .perform();

// Hold Alt and press Down arrow
actions.keyDown(Keys.ALT)
       .sendKeys(Keys.DOWN)
       .keyUp(Keys.ALT)
       .perform();
```

---

## Drag and Drop

### 1. Drag Element to Another Element

#### Syntax
```java
WebElement source = driver.findElement(By.id("source"));
WebElement target = driver.findElement(By.id("target"));

Actions actions = new Actions(driver);
actions.dragAndDrop(source, target).perform();
```

#### Code Example
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class DragDropTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com/dragdrop");
        
        // Find source and target elements
        WebElement source = driver.findElement(By.id("draggable"));
        WebElement target = driver.findElement(By.id("droppable"));
        
        // Perform drag and drop
        Actions actions = new Actions(driver);
        actions.dragAndDrop(source, target).perform();
        
        Thread.sleep(2000);
        
        // Verify drop was successful
        System.out.println("Drop area text: " + target.getText());
        
        driver.quit();
    }
}
```

---

### 2. Drag by Offset

#### Syntax
```java
WebElement element = driver.findElement(By.id("element"));
Actions actions = new Actions(driver);
actions.dragAndDropBy(element, 50, 50).perform();
```

#### Code Example
```java
// Drag element 100 pixels right and 50 pixels down
WebElement element = driver.findElement(By.id("draggable"));

Actions actions = new Actions(driver);
actions.dragAndDropBy(element, 100, 50).perform();
```

---

## Advanced Action Chains

### 1. Multiple Actions in Chain

#### Code Example
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class ActionChainTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com");
        
        WebElement element1 = driver.findElement(By.id("element1"));
        WebElement element2 = driver.findElement(By.id("element2"));
        WebElement element3 = driver.findElement(By.id("element3"));
        
        Actions actions = new Actions(driver);
        
        // Chain multiple actions
        actions.moveToElement(element1)
               .click()
               .sendKeys("Text input")
               .sendKeys(Keys.TAB)
               .click(element2)
               .doubleClick(element3)
               .perform();
        
        Thread.sleep(2000);
        driver.quit();
    }
}
```

---

### 2. Click and Hold then Release

#### Code Example
```java
WebElement element = driver.findElement(By.id("element"));

Actions actions = new Actions(driver);

// Click and hold
actions.clickAndHold(element).perform();
Thread.sleep(2000);

// Release
actions.release().perform();
```

---

### 3. Move to Element and Click

#### Code Example
```java
WebElement element = driver.findElement(By.id("element"));

Actions actions = new Actions(driver);

// Move to element and click
actions.moveToElement(element)
       .click()
       .perform();
```

---

## Slider Interaction

### Code Example
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class SliderTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com/slider");
        
        // Find slider element
        WebElement slider = driver.findElement(By.id("slider"));
        
        Actions actions = new Actions(driver);
        
        // Drag slider to the right (increase value)
        actions.dragAndDropBy(slider, 50, 0).perform();
        
        Thread.sleep(1000);
        
        // Drag slider to the left (decrease value)
        actions.dragAndDropBy(slider, -20, 0).perform();
        
        Thread.sleep(2000);
        driver.quit();
    }
}
```

---

## Advanced Interaction Example

```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import java.util.List;

public class AdvancedActionsTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.com");
        
        Actions actions = new Actions(driver);
        
        // 1. Hover over menu
        WebElement menu = driver.findElement(By.id("menu"));
        actions.moveToElement(menu).perform();
        
        Thread.sleep(1000);
        
        // 2. Click on submenu item
        WebElement submenuItem = driver.findElement(By.id("submenuItem"));
        actions.click(submenuItem).perform();
        
        // 3. Fill search box with special keys
        WebElement searchBox = driver.findElement(By.id("search"));
        actions.click(searchBox)
               .sendKeys("Selenium")
               .sendKeys(Keys.ENTER)
               .perform();
        
        Thread.sleep(2000);
        
        // 4. Drag and drop element
        WebElement source = driver.findElement(By.id("draggable"));
        WebElement target = driver.findElement(By.id("droppable"));
        actions.dragAndDrop(source, target).perform();
        
        Thread.sleep(1000);
        
        // 5. Right click and select option
        WebElement element = driver.findElement(By.id("element"));
        actions.contextClick(element).perform();
        
        Thread.sleep(500);
        
        WebElement contextOption = driver.findElement(By.id("copyOption"));
        actions.click(contextOption).perform();
        
        Thread.sleep(2000);
        driver.quit();
    }
}
```

---

## Keyboard Combinations

```java
// Ctrl+A - Select All
actions.sendKeys(Keys.CONTROL + "a").perform();

// Ctrl+C - Copy
actions.sendKeys(Keys.CONTROL + "c").perform();

// Ctrl+V - Paste
actions.sendKeys(Keys.CONTROL + "v").perform();

// Ctrl+X - Cut
actions.sendKeys(Keys.CONTROL + "x").perform();

// Ctrl+Z - Undo
actions.sendKeys(Keys.CONTROL + "z").perform();

// Shift+Tab - Reverse Tab
actions.sendKeys(Keys.SHIFT + Keys.TAB).perform();
```

---

## Key Points to Remember

✓ Always call `.perform()` to execute actions  
✓ Chain multiple actions for efficiency  
✓ Use proper waits between fast operations  
✓ Verify element state before interactions  
✓ Handle exceptions properly  

## Common Issues

❌ ElementNotInteractableException - Element not visible  
❌ NoSuchElementException - Wrong locator  
❌ MoveTargetOutOfBoundsException - Element outside viewport  

✓ Scroll to element before interaction  
✓ Use proper waits  
✓ Verify element location