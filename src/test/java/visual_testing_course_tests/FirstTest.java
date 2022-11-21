package visual_testing_course_tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ScreenCaptureUtility;

import static utils.WebDriverManager.getDriver;

public class FirstTest extends BaseTest {

    @Test
    public void screenshotHomePageTest() {
        getDriver().get("https://catalog.onliner.by/");
        new ScreenCaptureUtility().takePageScreenshot(getDriver(), "homePage");
    }

    @Test
    public void screenshotWebElementTest() {
        getDriver().get("https://catalog.onliner.by/");
        WebElement logo = getDriver().findElement(By.cssSelector("img.onliner_logo"));
        new ScreenCaptureUtility().takeElementScreenshot(getDriver(), "onliner_logo_element", logo);
    }

    @Test
    public void compareImages() {
        getDriver().get("https://catalog.onliner.by/");
        new ScreenCaptureUtility().takePageScreenshot(getDriver(), "screenHomePage");
        boolean result = new ScreenCaptureUtility().areImagesEqual("homePage", "screenHomePage");
        Assert.assertTrue(result);
    }

}
