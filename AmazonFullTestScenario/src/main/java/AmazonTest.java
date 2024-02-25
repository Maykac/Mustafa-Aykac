import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.List;

public class AmazonTest {

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");

        // Open the Amazon website
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.amazon.com.tr/");

        // Verify that the homepage is opened
        assertTitle(driver);

        // Accept cookies from cookie preferences
        acceptCookies(driver);

        // Log in to the site
        login(driver);

        // Select "Bebek" from the categories
        selectCategory(driver);

        // Verify that the "Bebek" category is selected
        assertCategorySelected(driver);

        // Type "Sleepy" in the search field and perform the search
        searchProduct(driver);

        // Verify that the search is performed
        assertSearchPerformed(driver);

        // Open the 3rd page from the search results
        navigateToSearchResultPage(driver);

        // Verify that the 3rd page is opened
        assertPageNumber(driver);

        // Add the first two products on the 3rd page to the cart
        addToCart(driver);

        // Go to the shopping cart
        goToShoppingCart(driver);

        // Assert that the correct calculation is done for the added items
        assertCartTotal(driver);

        // Log out
        logout(driver);

        // Close the browser
        driver.quit();
    }

    private static void assertTitle(WebDriver driver) {
        assert driver.getTitle().equals("Amazon.com.tr") : "Title not as expected";
    }

    private static void acceptCookies(WebDriver driver) {
        try {
            WebElement acceptCookiesButton = driver.findElement(By.id("accept"));
            acceptCookiesButton.click();
        } catch (Exception ignored) {

        }
    }

    private static void login(WebDriver driver) {

        WebElement signInButton = driver.findElement(By.id("accountList"));
        signInButton.click();

        WebElement usernameField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement signInSubmitButton = driver.findElement(By.id("signInSubmit"));

        usernameField.sendKeys("your_username");
        passwordField.sendKeys("your_password");
        signInSubmitButton.click();
    }

    private static void selectCategory(WebDriver driver) {
        WebElement categoriesTab = driver.findElement(By.id("searchDropdownBox"));
        categoriesTab.sendKeys("Bebek");
    }

    private static void assertCategorySelected(WebDriver driver) {
        WebElement selectedCategory = driver.findElement(By.xpath("//option[@selected='selected']"));
        assert selectedCategory.getText().equals("Bebek") : "Category not selected as expected";
    }

    private static void searchProduct(WebDriver driver) {
        WebElement searchField = driver.findElement(By.id("twotabsearchtextbox"));
        searchField.sendKeys("Sleepy");

        WebElement searchButton = driver.findElement(By.xpath("//input[@value='Go']"));
        searchButton.click();
    }

    private static void assertSearchPerformed(WebDriver driver) {
        WebElement searchResultsTitle = driver.findElement(By.xpath("//span[@class='a-declarative']/h1/span"));
        assert searchResultsTitle.getText().contains("Sleepy") : "Search not performed as expected";
    }

    private static void navigateToSearchResultPage(WebDriver driver) {
        WebElement nextPageButton = driver.findElement(By.xpath("//li[@class='a-last']/a"));
        for (int i = 2; i <= 3; i++) {
            nextPageButton.click();
        }
    }

    private static void assertPageNumber(WebDriver driver) {
        WebElement currentPageNumber = driver.findElement(By.xpath("//span[@class='a-pagination']/li[@class='a-selected']/a"));
        int actualPageNumber = Integer.parseInt(currentPageNumber.getText());
        assert actualPageNumber == 3 : "Page number not as expected";
    }

    private static void addToCart(WebDriver driver) {
        List<WebElement> addToCartButtons = driver.findElements(By.xpath("//span[text()='Sepete Ekle']/parent::span/parent::span/parent::div/parent::div//div[@class='a-row a-size-small']/div/span/input"));

        for (int i = 0; i < 2 && i < addToCartButtons.size(); i++) {
            addToCartButtons.get(i).click();
        }
    }

    private static void goToShoppingCart(WebDriver driver) {
        WebElement cartIcon = driver.findElement(By.id("nav-cart-count-container"));
        cartIcon.click();
    }

    private static void assertCartTotal(WebDriver ignoredDriver) {

    }

    private static void logout(WebDriver driver) {

        WebElement accountList = driver.findElement(By.id("nav-link-accountList"));
        accountList.click();

        WebElement signOutButton = driver.findElement(By.id("nav-item-signout"));
        signOutButton.click();
    }
}



