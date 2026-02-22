# 11. Cucumber BDD (Behavior Driven Development)

## Overview
Cucumber is a BDD framework that allows writing test scenarios in plain English using Gherkin language. It bridges the gap between non-technical stakeholders and technical teams.

## What is BDD?

BDD is a development approach that emphasizes collaboration between developers, QA engineers, and business stakeholders. Tests are written in a language that business people can understand.

### Why Cucumber?
- Write tests in plain English
- Improve communication between teams
- Easy to read and maintain
- Reusable step definitions
- Better documentation

---

## Installation and Setup

### 1. Add Cucumber Dependencies (pom.xml)

```xml
<!-- Cucumber Java -->
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-java</artifactId>
    <version>7.14.0</version>
    <scope>test</scope>
</dependency>

<!-- Cucumber TestNG -->
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-testng</artifactId>
    <version>7.14.0</version>
    <scope>test</scope>
</dependency>

<!-- Selenium WebDriver -->
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>4.15.0</version>
</dependency>
```

---

## Gherkin Language

### Basic Structure

Gherkin uses five key keywords:

#### 1. Feature
Describes what feature is being tested

```gherkin
Feature: User Login
  As a user
  I want to login to the application
  So that I can access my account
```

#### 2. Scenario
Describes a specific test case

```gherkin
Scenario: Successful login with valid credentials
  Given user is on login page
  When user enters valid username and password
  And user clicks login button
  Then user should be logged in successfully
```

#### 3. Given
Initial context/precondition

```gherkin
Given user is on login page
Given user has an existing account
```

#### 4. When
Action that triggers the scenario

```gherkin
When user enters "testuser" in username field
When user clicks login button
```

#### 5. Then
Expected outcome/verification

```gherkin
Then user should be logged in successfully
Then user should see dashboard page
```

#### 6. And/But
Additional conditions

```gherkin
Scenario: Successful login
  Given user is on login page
  When user enters valid credentials
  And user clicks login button
  Then user should see dashboard
  And user profile should be displayed
```

---

## Complete Feature File Example

Create file: `src/test/resources/features/login.feature`

```gherkin
Feature: Login Functionality
  As a user
  I want to login to the application
  So that I can access my account

  Background:
    Given user is on login page

  Scenario: Successful login with valid credentials
    When user enters "testuser" as username
    And user enters "password123" as password
    And user clicks login button
    Then user should be logged in successfully
    And user should see dashboard page

  Scenario: Login with invalid username
    When user enters "invaliduser" as username
    And user enters "password123" as password
    And user clicks login button
    Then error message should be displayed
    And error message should contain "Invalid username"

  Scenario: Login with empty credentials
    When user clicks login button without entering credentials
    Then validation error should be displayed
    And user should remain on login page

  Scenario Outline: Login with multiple credentials
    When user enters "<username>" as username
    And user enters "<password>" as password
    And user clicks login button
    Then login result should be "<result>"

    Examples:
      | username    | password      | result  |
      | testuser    | password123   | success |
      | user2       | mypassword    | success |
      | invaliduser | wrongpassword | failure |
      | emptyuser   |               | failure |
```

---

## Step Definitions

### Basic Step Definition Class

Create file: `src/test/java/stepdefinitions/LoginSteps.java`

```java
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class LoginSteps {
    private WebDriver driver;
    private String result;
    
    // Constructor
    public LoginSteps() {
        this.driver = DriverManager.getDriver();
    }
    
    // Given steps
    @Given("user is on login page")
    public void userIsOnLoginPage() {
        driver.get("https://example.com/login");
        System.out.println("User is on login page");
    }
    
    @Given("user has an existing account")
    public void userHasExistingAccount() {
        // Setup code for existing account
        System.out.println("User has an existing account");
    }
    
    // When steps
    @When("user enters {string} as username")
    public void userEntersUsername(String username) {
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.clear();
        usernameField.sendKeys(username);
        System.out.println("User entered username: " + username);
    }
    
    @When("user enters {string} as password")
    public void userEntersPassword(String password) {
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.clear();
        passwordField.sendKeys(password);
        System.out.println("User entered password");
    }
    
    @When("user clicks login button")
    public void userClicksLoginButton() {
        WebElement loginBtn = driver.findElement(By.id("loginBtn"));
        loginBtn.click();
        System.out.println("User clicked login button");
    }
    
    @When("user clicks login button without entering credentials")
    public void userClicksLoginButtonWithoutCredentials() {
        WebElement loginBtn = driver.findElement(By.id("loginBtn"));
        loginBtn.click();
        System.out.println("User clicked login button without entering credentials");
    }
    
    // Then steps
    @Then("user should be logged in successfully")
    public void userShouldBeLoggedInSuccessfully() throws InterruptedException {
        Thread.sleep(2000);
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("dashboard"), 
            "User is not logged in - URL: " + currentUrl);
        System.out.println("User is logged in successfully");
    }
    
    @Then("user should see dashboard page")
    public void userShouldSeeDashboardPage() {
        WebElement dashboard = driver.findElement(By.id("dashboard"));
        Assert.assertTrue(dashboard.isDisplayed(), 
            "Dashboard is not displayed");
        System.out.println("Dashboard page is displayed");
    }
    
    @Then("error message should be displayed")
    public void errorMessageShouldBeDisplayed() {
        try {
            WebElement errorMsg = driver.findElement(By.className("error"));
            Assert.assertTrue(errorMsg.isDisplayed(), 
                "Error message is not displayed");
            System.out.println("Error message is displayed");
        } catch (Exception e) {
            Assert.fail("Error message not found");
        }
    }
    
    @Then("error message should contain {string}")
    public void errorMessageShouldContain(String expectedMessage) {
        WebElement errorMsg = driver.findElement(By.className("error"));
        String actualMessage = errorMsg.getText();
        Assert.assertTrue(actualMessage.contains(expectedMessage), 
            "Error message does not contain: " + expectedMessage);
        System.out.println("Error message contains: " + expectedMessage);
    }
    
    @Then("user should remain on login page")
    public void userShouldRemainOnLoginPage() {
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("login"), 
            "User is not on login page");
        System.out.println("User remained on login page");
    }
    
    @Then("validation error should be displayed")
    public void validationErrorShouldBeDisplayed() {
        try {
            WebElement validationError = 
                driver.findElement(By.className("validation-error"));
            Assert.assertTrue(validationError.isDisplayed(), 
                "Validation error not displayed");
            System.out.println("Validation error is displayed");
        } catch (Exception e) {
            Assert.fail("Validation error not found");
        }
    }
    
    @Then("login result should be {string}")
    public void loginResultShouldBe(String expectedResult) throws InterruptedException {
        Thread.sleep(1000);
        
        if (expectedResult.equalsIgnoreCase("success")) {
            String url = driver.getCurrentUrl();
            Assert.assertTrue(url.contains("dashboard"), 
                "Login failed - expected success");
            System.out.println("Login was successful");
        } else if (expectedResult.equalsIgnoreCase("failure")) {
            try {
                WebElement errorMsg = 
                    driver.findElement(By.className("error"));
                Assert.assertTrue(errorMsg.isDisplayed(), 
                    "Expected error but login succeeded");
                System.out.println("Login failed as expected");
            } catch (Exception e) {
                Assert.fail("Expected login to fail but it succeeded");
            }
        }
    }
}
```

---

## Driver Management

### DriverManager Class

Create file: `src/test/java/utils/DriverManager.java`

```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverManager {
    private static WebDriver driver;
    
    public static WebDriver getDriver() {
        if (driver == null) {
            initializeDriver();
        }
        return driver;
    }
    
    public static void initializeDriver() {
        String browser = System.getProperty("browser", "chrome");
        
        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        }
        
        driver.manage().window().maximize();
    }
    
    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
```

---

## Hooks

### Before and After Hooks

Create file: `src/test/java/hooks/Hooks.java`

```java
import io.cucumber.java.Before;
import io.cucumber.java.After;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Hooks {
    private WebDriver driver;
    
    @Before
    public void setUp() {
        System.out.println("===== TEST STARTED =====");
        driver = DriverManager.getDriver();
    }
    
    @After
    public void tearDown() {
        System.out.println("===== TEST ENDED =====");
        DriverManager.closeDriver();
    }
    
    @After
    public void takeScreenshotOnFailure() {
        // This runs after each scenario
        try {
            SimpleDateFormat dateFormat = 
                new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String timestamp = dateFormat.format(new Date());
            
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            File destFile = new File("screenshots/screenshot_" + 
                timestamp + ".png");
            
            destFile.getParentFile().mkdirs();
            FileUtils.copyFile(srcFile, destFile);
            
            System.out.println("Screenshot saved: " + 
                destFile.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("Error taking screenshot: " + e.getMessage());
        }
    }
}
```

---

## Test Runner

### TestNG Runner

Create file: `src/test/java/runners/TestRunner.java`

```java
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"stepdefinitions", "hooks"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/report.html",
        "json:target/cucumber-reports/report.json",
        "junit:target/cucumber-reports/report.xml"
    },
    monochrome = true,
    tags = "@smoke"
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
```

---

## Feature File with Tags

### login.feature with Tags

```gherkin
Feature: User Authentication

  @smoke @critical
  Scenario: Successful login with valid credentials
    Given user is on login page
    When user enters "testuser" as username
    And user enters "password123" as password
    And user clicks login button
    Then user should be logged in successfully

  @regression
  Scenario: Login with invalid credentials
    Given user is on login page
    When user enters "invaliduser" as username
    And user enters "wrongpass" as password
    And user clicks login button
    Then error message should be displayed

  @smoke
  Scenario: Password reset functionality
    Given user is on login page
    When user clicks forgot password link
    Then password reset page should be displayed
```

### Run Specific Tags

```java
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"stepdefinitions", "hooks"},
    tags = "@smoke",  // Run only @smoke scenarios
    plugin = {"pretty", "html:target/reports/report.html"}
)
public class SmokeTestRunner extends AbstractTestNGCucumberTests {
}
```

---

## Page Object Model with Cucumber

### LoginPage Class

Create file: `src/test/java/pages/LoginPage.java`

```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

public class LoginPage {
    private WebDriver driver;
    
    // Locators
    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.id("loginBtn");
    private By errorMessage = By.className("error");
    private By forgotPasswordLink = By.id("forgotPassword");
    
    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    
    // Methods
    public void navigateTo() {
        driver.get("https://example.com/login");
    }
    
    public void enterUsername(String username) {
        WebElement usernameInput = driver.findElement(usernameField);
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }
    
    public void enterPassword(String password) {
        WebElement passwordInput = driver.findElement(passwordField);
        passwordInput.clear();
        passwordInput.sendKeys(password);
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
        } catch (Exception e) {
            return false;
        }
    }
    
    public void clickForgotPasswordLink() {
        driver.findElement(forgotPasswordLink).click();
    }
}
```

### Step Definitions Using POM

```java
import io.cucumber.java.en.*;
import pages.LoginPage;
import utils.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginStepsWithPOM {
    private WebDriver driver;
    private LoginPage loginPage;
    
    public LoginStepsWithPOM() {
        this.driver = DriverManager.getDriver();
        this.loginPage = new LoginPage(driver);
    }
    
    @Given("user is on login page")
    public void userIsOnLoginPage() {
        loginPage.navigateTo();
    }
    
    @When("user enters {string} as username")
    public void userEntersUsername(String username) {
        loginPage.enterUsername(username);
    }
    
    @When("user enters {string} as password")
    public void userEntersPassword(String password) {
        loginPage.enterPassword(password);
    }
    
    @When("user clicks login button")
    public void userClicksLoginButton() {
        loginPage.clickLoginButton();
    }
    
    @Then("user should be logged in successfully")
    public void userShouldBeLoggedIn() throws InterruptedException {
        Thread.sleep(2000);
        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.contains("dashboard"));
    }
    
    @Then("error message should be displayed")
    public void errorMessageDisplayed() {
        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
    }
}
```

---

## Data Tables in Cucumber

### Feature File with Data Table

```gherkin
Feature: User Management

  Scenario: Add multiple users
    Given admin is on user management page
    When admin adds the following users:
      | FirstName | LastName | Email              | Role     |
      | John      | Doe      | john@example.com   | Admin    |
      | Jane      | Smith    | jane@example.com   | User     |
      | Bob       | Johnson  | bob@example.com    | Manager  |
    Then all users should be added successfully
```

### Step Definition with DataTable

```java
import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import java.util.List;
import java.util.Map;

public class UserManagementSteps {
    
    @When("admin adds the following users:")
    public void adminAddsUsers(DataTable dataTable) {
        List<Map<String, String>> users = dataTable.asMaps();
        
        for (Map<String, String> user : users) {
            String firstName = user.get("FirstName");
            String lastName = user.get("LastName");
            String email = user.get("Email");
            String role = user.get("Role");
            
            System.out.println("Adding user: " + firstName + " " + 
                lastName + " (" + email + ") - " + role);
            
            // Add user logic here
        }
    }
}
```

---

## Scenario Outline

```gherkin
Feature: Shopping Cart

  Scenario Outline: Add items to cart with different quantities
    Given user is on product page
    When user adds "<quantity>" of "<product>" to cart
    Then cart should contain "<product>" with quantity "<quantity>"
    And total price should be "<total>"

    Examples:
      | product         | quantity | total  |
      | Laptop          | 1        | $999   |
      | Mouse           | 2        | $50    |
      | Keyboard        | 1        | $79    |
      | Monitor         | 3        | $1197  |
```

### Corresponding Step Definition

```java
@When("user adds {string} of {string} to cart")
public void userAddsItemToCart(String quantity, String product) {
    System.out.println("Adding " + quantity + " of " + product + " to cart");
    // Implementation
}

@Then("cart should contain {string} with quantity {string}")
public void cartShouldContain(String product, String quantity) {
    System.out.println("Verifying " + quantity + " of " + product + 
        " in cart");
    // Implementation
}

@And("total price should be {string}")
public void totalPriceShouldBe(String expectedTotal) {
    System.out.println("Verifying total price: " + expectedTotal);
    // Implementation
}
```

---

## Background in Cucumber

### Feature File with Background

```gherkin
Feature: Shopping Functionality

  Background:
    Given user is on home page
    And user is logged in as "customer"
    And user has an active shopping cart

  Scenario: Add item to cart
    When user searches for "laptop"
    And user adds laptop to cart
    Then cart should contain 1 item

  Scenario: Remove item from cart
    Given cart contains "keyboard" and "mouse"
    When user removes "keyboard" from cart
    Then cart should contain only "mouse"
```

---

## Best Practices

```gherkin
# ✓ GOOD - Clear and business-friendly
Feature: User Registration
  Scenario: User registers with valid email
    Given user is on registration page
    When user enters valid email
    And user enters strong password
    Then user should receive confirmation email

# ❌ AVOID - Technical language
Feature: UserRegistrationForm
  Scenario: AuthenticationWithValidCredentials
    Given element with id "registerForm" is displayed
    When user inputs "test@example.com" in field id "emailInput"
    Then page URL contains "/dashboard"
```

---

## Running Cucumber Tests

### Command Line
```bash
# Run all tests
mvn test

# Run specific feature file
mvn test -Dtest=LoginTestRunner

# Run specific tag
mvn test -Dcucumber.filter.tags="@smoke"

# Run tests in parallel
mvn test -DthreadCount=4
```

---

## Cucumber Reports

### Generate Reports

```xml
<!-- In pom.xml -->
<plugin>
    <groupId>net.masterthought</groupId>
    <artifactId>maven-cucumber-reporting</artifactId>
    <version>5.7.1</version>
    <executions>
        <execution>
            <id>execution</id>
            <phase>verify</phase>
            <goals>
                <goal>generate</goal>
            </goals>
            <configuration>
                <projectName>Cucumber Reports</projectName>
                <outputDirectory>target/cucumber-reports</outputDirectory>
                <cucumberOutput>target/cucumber.json</cucumberOutput>
                <checkBuildResult>true</checkBuildResult>
            </configuration>
        </execution>
    </executions>
</plugin>
```

---

## Key Points to Remember

✓ Write scenarios in plain English  
✓ Use Given-When-Then structure  
✓ Keep steps reusable and independent  
✓ Use Page Object Model with Cucumber  
✓ Implement proper hooks for setup/teardown  
✓ Use tags for test organization  
✓ Use Scenario Outline for data-driven tests  
✓ Generate reports for better visibility  

## Common Issues

❌ Vague step definitions  
❌ Hard-coded values in steps  
❌ Mixing test logic with steps  

✓ Write clear, business-friendly steps  
✓ Use parameters instead of hard-coded values  
✓ Keep step definitions focused and reusable