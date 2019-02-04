import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.w3c.dom.css.CSSValue;

import java.io.File;
import java.util.Iterator;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GlobbingRegisterPage {
    WebDriver driver;
    Actions builder;
    Helper helper;
    String fbUrl = "https://www.facebook.com/login.php";
    public static final String path = System.getProperty("user.dir") + File.separator + "requirements" + File.separator + "webdrivers" + File.separator;



    @BeforeMethod
    protected void befor() {
        System.setProperty("webdriver.chrome.driver",path+"chromedriver-v2.37-win32.exe");
        driver = new ChromeDriver();
        builder = new Actions(driver);
        helper= new Helper();
        driver.manage().window().maximize();
    }

    @Test
    protected void hoverGrancvelButton() throws InterruptedException {
        driver.get("https://globbing.com/arm/register");
        builder.moveToElement(driver.findElement(By.xpath("//button[@class='register--submit btn is--primary ']"))).build().perform();
        Thread.sleep(1000);
        assertEquals("rgba(31, 139, 106, 1)", driver.findElement(By.xpath("//button[@class='register--submit btn is--primary ']")).getCssValue("background-color"));
    }

    @Test
    protected void clickGrancvelButton() {
        driver.get("https://globbing.com/arm/register");
        driver.findElement(By.xpath("//button[@class='register--submit btn is--primary ']")).click();
        assertEquals("rgba(251, 192, 192, 1)", driver.findElement(By.id("register_personal_email")).getCssValue("background-color"));
        assertEquals("rgba(251, 192, 192, 1)", driver.findElement(By.id("register_personal_password")).getCssValue("background-color"));
        assertEquals("rgba(251, 192, 192, 1)", driver.findElement(By.id("register_personal_passwordConfirmation")).getCssValue("background-color"));
        assertEquals("rgba(251, 192, 192, 1)", driver.findElement(By.id("register_billing_phone")).getCssValue("background-color"));
    }

    @Test
    protected void checkBorderColorWhenClickInEmailInputFeald() {
        driver.get("https://globbing.com/arm/register");
        driver.findElement(By.id("register_personal_email")).click();
        assertEquals("rgb(24, 25, 26)", driver.findElement(By.id("register_personal_email")).getCssValue("border-color"));
    }

    @Test
    protected void typingCursor() {
        driver.get("https://globbing.com/arm/register");
        driver.findElement(By.id("register_personal_email")).click();
        assertEquals("text", driver.findElement(By.id("register_personal_email")).getCssValue("cursor"));

    }

    @Test
    protected void inputFealdEmpty() {
        driver.get("https://globbing.com/arm/register");
        builder.moveToElement(driver.findElement(By.id("register_personal_email"))).build().perform();
        driver.findElement(By.id("register_personal_email")).sendKeys("Armen@gmail.com");
        driver.navigate().refresh();
        assertEquals("https://globbing.com/arm/register", driver.getCurrentUrl());
        assertEquals("", driver.findElement(By.id("register_personal_email")).getText());

    }

    @Test
    protected void invalidBug() throws InterruptedException {
        driver.get("https://globbing.com/arm/register");
        driver.findElement(By.id("register_personal_email")).sendKeys("#@%^%#$@#$@#.com");
        driver.findElement(By.xpath("//button[@class='register--submit btn is--primary ']")).click();
        Thread.sleep(2000);
        assertEquals("Խնդրում ենք մուտքագրեք ճիշտ էլ-փոստ", driver.findElement(By.linkText("Խնդրում ենք մուտքագրեք ճիշտ էլ-փոստ")).getText());

    }

    @Test
    protected void validInputField() {
        driver.get("https://globbing.com/arm/register");
        driver.findElement(By.id("register_personal_email")).sendKeys("ArmenAntonyan@gmail.com");
        driver.findElement(By.xpath("//button[@class='register--submit btn is--primary ']")).click();
        String actualColor = driver.findElement(By.id("register_personal_email")).getCssValue("background-color");
        assertEquals("rgba(236, 248, 244, 1)", actualColor);
    }
    @Test
    protected void limiation() throws InterruptedException {
        driver.get("https://globbing.com/arm/register");
        driver.findElement(By.id("register_personal_password")).sendKeys("a1a2a3a");
        driver.findElement(By.id("register_personal_passwordConfirmation")).sendKeys("a1a2a3a");
        driver.findElement(By.xpath("//button[@class='register--submit btn is--primary ']")).click();
        String sxal = driver.findElement(By.xpath("//*[@id='f_registerFormValidation']/div[1]/div/div[2]/div[2]/div")).getText();
        assertEquals("Ձեր գաղտնաբառը պետք է պարունակի առնվազն 8 նիշ", sxal);

    }

    @Test
    protected void limiationBug() throws InterruptedException {
        driver.get("https://globbing.com/arm/register");
        driver.findElement(By.id("register_personal_password")).sendKeys("a1a2a3a");
        driver.findElement(By.xpath("//button[@class='register--submit btn is--primary ']")).click();
        String sxal = driver.findElement(By.xpath("//*[@id='f_registerFormValidation']/div[1]/div/div[2]/div[2]/div")).getText();
        assertEquals("Ձեր գաղտնաբառը պետք է պարունակի առնվազն 8 նիշ", sxal);
    }

    @Test
    protected void checkbox() throws InterruptedException {

        driver.get("https://globbing.com/arm/register");
        javaScript(0,400);
        driver.findElement(By.xpath("//button[@class='register--submit btn is--primary ']")).click();
        helper.waitForElementPresent(driver,"//*[@id='f_registerFormValidation']/div[1]/div/div[2]/div[5]/label/span[1]",5000);
        driver.findElement(By.xpath("//*[@id='f_registerFormValidation']/div[1]/div/div[2]/div[5]/label/span[1]")).click();
        helper.waitForElementPresent(driver,"//span[@class=\"accept-reg-icon\"]",5000);
        assertEquals("accept-reg-icon", driver.findElement(By.xpath("//*[@id='f_registerFormValidation']/div[1]/div/div[2]/div[5]/label/span[1]")).getAttribute("class"));
    }

    @Test
    protected void registerWithFB() throws InterruptedException {
        driver.get("https://globbing.com/arm/register");
        driver.findElement(By.xpath("//*[@id='f_registerFormValidation']/div[5]/ul/li/a")).click();
        helper.waitForUrlPresent(driver,"https://www.facebook.com/login.php",5000);
        assertTrue(switchToWindow().contains(fbUrl));
    }
    @Test
    protected void calculator() throws InterruptedException {
        driver.get("https://globbing.com/arm/register");
        helper.waitForElementPresent(driver,"//a[@class='shipping-calculator-icon']",5000);
        driver.findElement(By.xpath("//a[@class='shipping-calculator-icon']")).click();
       helper.waitForElementPresent(driver,"//*[@id='f_shippingCalcWindow']/div[1]",5000);
        assertTrue (driver.findElement(By.xpath("//*[@id='f_shippingCalcWindow']/div[1]")).isDisplayed());
    }
    @Test
    protected void grancvacEmail() throws InterruptedException
    {
        driver.get("https://globbing.com/arm/register");
        driver.findElement(By.id("register_personal_email")).sendKeys("Menchmenchyan@gmail.com");
        driver.findElement(By.xpath("//button[@class='register--submit btn is--primary ']")).click();
        assertEquals("Այս Էլ-փոստը արդեն գրանցված է",driver.findElement(By.xpath("//*[@id='f_registerFormValidation']/div[1]/div/div[1]/div")).getText());

    }
    @Test
    protected void geraffeIcon() throws InterruptedException {
        driver.get("https://globbing.com/arm/register");
        helper.waitForElementPresent(driver,"//*[@id='PureChatWidget']/div[3]/div[1]",5000);

        assertTrue(driver.findElement(By.xpath("//*[@id='PureChatWidget']/div[3]/div[1]")).isDisplayed());
    }
    @Test
    protected void stres() throws InterruptedException {
        driver.get("https://globbing.com/arm/register");
        sexmel00Angam();
        assertEquals("https://globbing.com/arm/register",driver.getCurrentUrl());
    }



    @Test
    protected void invalidBug2() throws InterruptedException {
        driver.get("https://globbing.com/arm/register");
        driver.findElement(By.id("register_personal_email")).sendKeys("ԱրմենԱնտոնյան@gmail.com");
        helper.captureScreen(driver);
        driver.findElement(By.xpath("//button[@class='register--submit btn is--primary ']")).click();
        helper.captureScreen(driver);
        assertEquals("Խնդրում ենք մուտքագրեք լատինատառ",driver.findElement(By.xpath("//*[@id='f_registerFormValidation']/div[1]/div/div[1]/div")).getText());

    }

    @AfterMethod
    protected void after() {
        driver.quit();
    }

    protected String switchToWindow() {
        Set<String> set = driver.getWindowHandles();
        Iterator<String> it = set.iterator();
        String parentWindowId = it.next();
        String childWindowId = it.next();
        driver.switchTo().window(childWindowId);
        return driver.getCurrentUrl();
    }

    protected void sexmel00Angam() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            driver.navigate().refresh();

        }

    }
    protected void javaScript(int x,int y)
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy("+x+","+y+")");
    }
}