# UI Test Framework with Design Patterns

This project demonstrates a Selenium-based UI testing framework implementing multiple design patterns.

## Design Patterns Implemented

### 1. **Singleton Pattern**
- `DriverManager` - Manages WebDriver instance lifecycle
- `PropertiesReader` - Reads configuration from properties file

### 2. **DTO (Data Transfer Object) Pattern**
- `LoginCredentials` - Encapsulates login data (username and password)
- `LoginStatus` - Enum for login status (SUCCESS, FAILED)

### 3. **Builder Pattern**
- `LoginCredentialsBuilder` - Fluent API for creating LoginCredentials objects
  - `withValidCredentials()` - Loads valid credentials from properties
  - `withInvalidCredentials()` - Loads invalid credentials from properties
  - `build()` - Creates immutable LoginCredentials object

### 4. **Page Object Pattern with Page Factory**
- `LoginPage` - Page Object for login page with @FindBy annotations
- `SecureAreaPage` - Page Object for secure area page
- Uses Selenium PageFactory for element initialization

## Project Structure

```
mentoring/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/mentoring/
│   │   │       ├── builder/
│   │   │       │   └── LoginCredentialsBuilder.java
│   │   │       ├── driver/
│   │   │       │   └── DriverManager.java
│   │   │       ├── dto/
│   │   │       │   ├── LoginCredentials.java
│   │   │       │   └── LoginStatus.java
│   │   │       ├── pages/
│   │   │       │   ├── LoginPage.java
│   │   │       │   └── SecureAreaPage.java
│   │   │       └── utils/
│   │   │           └── PropertiesReader.java
│   │   └── resources/
│   │       └── config.properties
│   └── test/
│       └── java/
│           └── com/mentoring/tests/
│               └── LoginTest.java
├── pom.xml
└── testng.xml
```

## Key Features

### Singleton Pattern
- **Thread-safe implementation** with double-checked locking
- `DriverManager` ensures only one WebDriver instance per test
- `PropertiesReader` loads configuration once and reuses it

### Builder Pattern
- Fluent API for creating test data
- Predefined methods for valid/invalid credentials
- Validation in `build()` method ensures data integrity

### Page Object Pattern
- Encapsulates page structure and behavior
- Uses `@FindBy` annotations with PageFactory
- Fluent API for chaining page actions
- Returns appropriate page objects after navigation

### Configuration Management
All test configuration externalized in `config.properties`:
- URLs (base URL, login URL)
- Test credentials (valid/invalid)
- Browser settings
- Timeouts
- Expected messages for assertions

## Usage Example

```java
@Test
public void testSuccessfulLogin() {
    LoginCredentials credentials = new LoginCredentialsBuilder()
            .withValidCredentials()
            .build();
    
    SecureAreaPage secureAreaPage = loginPage.login(
            credentials.username(), 
            credentials.password()
    );
    
    Assert.assertTrue(secureAreaPage.isSecureAreaDisplayed());
}
```

## Configuration

All test configuration is stored in `config.properties`:
- URLs
- Valid/Invalid credentials
- Browser settings
- Timeouts
- Expected messages

## Running Tests

### Using Maven:
```bash
mvn clean test
```

### Using TestNG XML:
```bash
mvn test -DsuiteXmlFile=testng.xml
```

## Test Scenarios

1. **testSuccessfulLogin** - Tests login with valid credentials using Builder pattern
   - Verifies secure area is displayed
   - Validates success message

2. **testFailedLogin** - Tests login with invalid credentials
   - Verifies error message is displayed
   - Validates error message content

3. **testLoginWithEmptyCredentials** - Tests login with empty username and password
   - Verifies error message is displayed

4. **testLoginWithValidUsernameInvalidPassword** - Tests mixed valid/invalid credentials
   - Uses valid username with invalid password
   - Verifies appropriate error message

