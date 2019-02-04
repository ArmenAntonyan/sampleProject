import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class Helper {
//    String windowUrl;
//
    public String captureScreen(WebDriver driver) {
        String path;
        try {
            WebDriver augmentedDriver = new Augmenter().augment(driver);
            File source = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
            path = "./target/screenshots/" + source.getName();
            FileUtils.copyFile(source, new File(path));
        }
        catch(IOException e) {
            path = "Failed to capture screenshot: " + e.getMessage();
        }
        return path;
    }

    public void wait(int miliSecond) throws InterruptedException {
        Thread.sleep(miliSecond);
    }

    public void waitForElementPresent(WebDriver driver,String xpath, int maxTime) throws InterruptedException {
        try{
            int t=0;
            while (!driver.findElement(By.xpath(xpath)).isDisplayed() && t<=maxTime){
                t+=200;
                Thread.sleep(t);
            }
        }
        catch (Exception e)
        {
            Thread.sleep(maxTime);
        }

    }
    public void waitForUrlPresent(WebDriver driver,String text, int maxTime) throws InterruptedException {
        try {
            int t = 0;
            while (!(text.equals(driver.getCurrentUrl())) && t <= maxTime) {
                t += 200;
                Thread.sleep(t);
            }
        } catch (Exception e) {
            System.out.println("Connection error/Cant locate an element");
        }
    }

    public void waitForPageToBeReady(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
    }
}