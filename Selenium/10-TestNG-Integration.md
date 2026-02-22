# 10. TestNG Integration

## Overview
TestNG is a testing framework that simplifies test organization, execution, and reporting.

## Installation and Setup

### 1. Add TestNG Dependency (pom.xml)
```xml
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.8.1</version>
    <scope>test</scope>
</dependency>
```

---

## Basic TestNG Annotations

### 1. @Test Annotation

```java
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BasicTestNGTest {
    
    @Test
    public void testOne() {
        System.out.println("Test One executed");
    }
    
    @Test
    public void testTwo() {
        System.out.println("Test Two executed");
    }
}
```

---

### 2. @BeforeTest and @AfterTest

```java
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BeforeAfterTest {
    private WebDriver driver;
    
    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        System.out.println("Browser launched");
    }
    
    @Test
    public void testOne() {
        System.out.println("Test One");
    }
    
    @Test
    public void testTwo() {
        System.out.println("Test Two");
    }
    
    @AfterTest
    public void tearDown() {
        driver.quit();
        System.out.println("Browser closed");
    }
}
```

---

### 3. @BeforeClass and @AfterClass

```java
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class ClassLevelAnnotationsTest {
    
    @BeforeClass
    public void setUpClass() {
        System.out.println("Before Class - Runs once before all tests in class");
    }
    
    @Test
    public void testOne() {
        System.out.println("Test One");
    }
    
    @Test
    public void testTwo() {
        System.out.println("Test Two");
    }
    
    @AfterClass
    public void tearDownClass() {
        System.out.println("After Class - Runs once after all tests in class");
    }
}
```

---

### 4. @BeforeMethod and @AfterMethod

```java
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class MethodLevelAnnotationsTest {
    
    @BeforeMethod
    public void setUp() {
        System.out.println("Before Method - Runs before each test method");
    }
    
    @Test
    public void testOne() {
        System.out.println("Test One");
    }
    
    @Test
    public void testTwo() {
        System.out.println("Test Two");
    }
    
    @AfterMethod
    public void tearDown() {
        System.out.println("After Method - Runs after each test method");
    }
}
```

---

### 5. @BeforeSuite and @AfterSuite

```java
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class SuiteAnnotationsTest {
    
    @BeforeSuite
    public void setUpSuite() {
        System.out.println("Before Suite - Runs once before all tests in suite");
        // Initialize reports, databases, etc.
    }
    
    @AfterSuite
    public void tearDownSuite() {
        System.out.println("After Suite - Runs once after all tests in suite");
        // Close reports, databases, etc.
    }
}
```

---

## TestNG Assertions

### Common Assertions

```java
import org.testng.Assert;
import org.testng.annotations.Test;

public class AssertionTest {
    
    @Test
    public void testAssertions() {
        // assertEquals
        Assert.assertEquals("Hello", "Hello");
        
        // assertNotEquals
        Assert.assertNotEquals("Hello", "World");
        
        // assertTrue
        Assert.assertTrue(true);
        Assert.assertTrue(5 > 3);
        
        // assertFalse
        Assert.assertFalse(false);
        Assert.assertFalse(5 < 3);
        
        // assertNull
        Assert.assertNull(null);
        
        // assertNotNull
        Assert.assertNotNull("Not Null");
        
        // fail
        Assert.fail("Test failed with message");
    }
}
```

---

## TestNG with Selenium

### Complete Example

```java
import org.](#)
