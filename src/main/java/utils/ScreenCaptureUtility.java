package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenCaptureUtility {
    public boolean areImagesEqual(String baseline, String screenshot) {
        BufferedImage imageBaseLine = null;
        BufferedImage imageScreenShot = null;

        try {
            imageBaseLine = ImageIO.read(new File(System.getProperty("user.dir") + "/src/images/baseline/" + baseline + ".png"));
            imageScreenShot = ImageIO.read(new File(System.getProperty("user.dir") + "/src/images/screenshots/" + screenshot + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ImageDiff diff = new ImageDiffer().makeDiff(imageBaseLine, imageScreenShot);
        boolean isDiff = diff.hasDiff();
        if (isDiff) {
            BufferedImage diffImage = diff.getMarkedImage();
            try {
                ImageIO.write(diffImage, "png", new File(System.getProperty("user.dir") + "/src/images/diffimages/" + baseline + ".png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return !isDiff;
    }

    public void takePageScreenshot(WebDriver driver, String name) {
        Screenshot screenshot = new AShot().takeScreenshot(driver);
        BufferedImage image = screenshot.getImage();

        File imageFile = new File(System.getProperty("user.dir") + "/src/images/screenshots/" + name + ".png");
        try {
            ImageIO.write(image, "png", imageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void takePageScreenshotImproved(WebDriver driver, String name) {
        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver);
        BufferedImage image = screenshot.getImage();

        File imageFile = new File(System.getProperty("user.dir") + "/src/images/screenshots/" + name + ".png");
        try {
            ImageIO.write(image, "png", imageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void takeElementScreenshot(WebDriver driver, String name, WebElement element) {
        Screenshot screenshot = new AShot().coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(driver, element);
        BufferedImage image = screenshot.getImage();

        File imageFile = new File(System.getProperty("user.dir") + "/src/images/screenshots/" + name + ".png");
        try {
            ImageIO.write(image, "png", imageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
