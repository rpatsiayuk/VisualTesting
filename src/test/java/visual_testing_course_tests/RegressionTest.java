package visual_testing_course_tests;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ScreenCaptureUtility;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import static utils.WebDriverManager.getDriver;

public class RegressionTest extends BaseTest {


    @DataProvider(name = "urls")
    public static Object[][] urls() {
        return new Object[][]{
                {"https://catalog.onliner.by/", "homePage"}
        };
    }

    @Test(dataProvider = "urls")
    public void testRegression(String url, String title, Method method, ITestContext context) {
        context.getCurrentXmlTest().addParameter("image", title);
        extentTest = extentReports.startTest(method.getName() + "||" + url);
        getDriver().get(url);
        screenCaptureUtility.takePageScreenshot(getDriver(), title);
        Assert.assertTrue(screenCaptureUtility.areImagesEqual(title, title));
    }
}
