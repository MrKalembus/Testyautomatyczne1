import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class tests {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    public void driverSetup() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        //implicity wait - niejawne
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver = new ChromeDriver();
        // explicity wait - czekanie jawne
        wait = new WebDriverWait(driver,10);
    }

    @AfterEach
    public void driverClose() {
        driver.quit();
    }

    @Test
    public void firstTest() {
        driver.get("https://sdacademy.pl/");
        String tytul = driver.getTitle();
        String url = driver.getCurrentUrl();
        System.out.println(tytul);
        System.out.println(url);
    }

    @Test
    public void secondTest() {
        driver.navigate().to("https://sdacademy.pl/");
        driver.navigate().to("https://wykop.pl/");
        driver.navigate().back();
        driver.navigate().forward();
        driver.navigate().refresh();
    }

    @Test
    public void selectorTest() {
        driver.get("https://skleptest.pl/");
        WebElement searchInput = driver.findElement(By.id("search-field-top-bar"));
        WebElement searchButton = driver.findElement(By.className("search-top-bar-submit"));
        WebElement newsletterNameInput = driver.findElement(By.name("es_txt_name"));
        List<WebElement> listOfElements = driver.findElements(By.tagName("h5"));
        System.out.println(listOfElements.size());
        WebElement shopButton = driver.findElement(By.linkText("SHOP"));
        WebElement aboutUsButton = driver.findElement(By.partialLinkText("US"));
    }

    @Test
    public void advSelectorTest() {
        driver.get("https://skleptest.pl/");
        WebElement searchInput = driver.findElement(By.xpath(("//input[@type=\"search\"]")));
        WebElement searchInputByCss = driver.findElement(By.cssSelector("input[type=search]"));
        WebElement accountButton = driver.findElement(By.cssSelector(".top-account"));
        WebElement searchClass = driver.findElement(By.xpath("//li[@class=\"top-account\"]"));
    }

    @Test
    public void simpleActions() {
        driver.get("https://skleptest.pl/");
        WebElement searchInput = driver.findElement(By.className("search-field-top-bar"));
        WebElement searchButton = driver.findElement(By.id("search-top-bar-submit"));
        searchInput.clear();
        searchInput.sendKeys("Dress");
        searchButton.click();
    }

    @Test
    public void simpleActions2() {
        driver.get("https://skleptest.pl/");
        WebElement newsletterNameInput = driver.findElement(By.cssSelector(".es_textbox_class"));
        WebElement newsletterEmailInput = driver.findElement(By.cssSelector("#es_txt_email"));
        WebElement newsletterSubscribeButton = driver.findElement(By.name("es_txt_button"));

        newsletterNameInput.clear();
        newsletterNameInput.sendKeys("TEST");
        newsletterEmailInput.clear();
        newsletterEmailInput.sendKeys("TEST@test.test");
        newsletterSubscribeButton.click();
    }
    @Test
    public void assertionTest(){
        driver.get("https://skleptest.pl/my-account/");
        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement userLogin = driver.findElement(By.name("login"));


        usernameInput.clear();
        usernameInput.sendKeys("user");
        userLogin.click();

        WebElement loginInfo = driver.findElement(By.className("woocommerce-error"));
        Assertions.assertEquals("Error: The password field is empty.",loginInfo.getText());
    }

    @Test
    public void assertionTest2(){
        driver.get("https://skleptest.pl/my-account/");
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement userLogin = driver.findElement(By.name("login"));


        passwordInput.clear();
        passwordInput.sendKeys("password");
        userLogin.click();

        WebElement loginInfo = driver.findElement(By.className("woocommerce-error"));
        Assertions.assertEquals("Error: Username is required.",loginInfo.getText());
    }
    @Test
    public void waitTest(){
        driver.get("https://skleptest.pl/product/little-black-top/");
        WebElement quantity = driver.findElement(By.name("quantity"));
        quantity.clear();
        quantity.sendKeys("1");
        WebElement addToCart = driver.findElement(By.name("add-to-cart"));
        addToCart.click();
        WebElement myCartButton = driver.findElement(By.cssSelector(".top-cart"));
        myCartButton.click();

        WebElement updateCartButton = driver.findElement(By.name("update_cart"));
        WebElement checkoutButton = driver.findElement(By.cssSelector(".checkout-button"));
        Assertions.assertFalse(updateCartButton.isEnabled());

        WebElement increaseButton = driver.findElement(By.cssSelector(".arrow-up"));
        increaseButton.click();
        updateCartButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
        Assertions.assertTrue(checkoutButton.isEnabled());
    }
}