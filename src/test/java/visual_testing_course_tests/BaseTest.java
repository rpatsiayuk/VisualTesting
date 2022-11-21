package visual_testing_course_tests;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import utils.ScreenCaptureUtility;
import utils.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseTest {

    ScreenCaptureUtility screenCaptureUtility = new ScreenCaptureUtility();
    WebDriverManager webDriverManager = new WebDriverManager();

    ExtentReports extentReports;
    ExtentTest extentTest;

    @BeforeClass
    public void beforeClass() {
        extentReports = new ExtentReports(System.getProperty("user.dir") + "\\report\\Report.html");
        try {
            FileUtils.cleanDirectory(new File(System.getProperty("user.dir") + "/src/images/diffimages/"));
            FileUtils.cleanDirectory(new File(System.getProperty("user.dir") + "/src/images/screenshots/"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterClass
    protected void afterClass() {
        extentReports.endTest(extentTest);
        extentReports.flush();
    }

    @BeforeMethod
    protected void setUp(Method method) {
        webDriverManager.setupDriver("chrome");
        //extentTest = extentReports.startTest(method.getName());
    }

    @AfterMethod
    protected void tearDown(ITestResult result, ITestContext context) {
        WebDriverManager.closeDriver();
        String image = context.getCurrentXmlTest().getParameter("image");
        if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.log(LogStatus.PASS, "Test Passed");
        }
        if (result.getStatus() == ITestResult.FAILURE) {
            String diff = extentTest.addScreenCapture("../src/images/diffimages/" + image + ".png");
            extentTest.log(LogStatus.FAIL, "Test Failed", "screenshot: \n" + diff);
            extentTest.log(LogStatus.FAIL, result.getThrowable());
        }
        if (result.getStatus() == ITestResult.SKIP) {
            extentTest.log(LogStatus.SKIP, "Test Skipped");
        }
    }
}
