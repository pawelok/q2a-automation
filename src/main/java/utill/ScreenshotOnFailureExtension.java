package utill;

import com.google.common.io.ByteSource;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.LifecycleMethodExecutionExceptionHandler;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScreenshotOnFailureExtension implements TestExecutionExceptionHandler, LifecycleMethodExecutionExceptionHandler {

    private static WebDriver driver;

    public static void init(WebDriver driver) {
        ScreenshotOnFailureExtension.driver = driver;
    }

    @Override
    public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {

        saveScreenshot(extensionContext);
        throw throwable;
    }

    @Override
    public void handleBeforeAllMethodExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {
        saveScreenshot(extensionContext);
        throw throwable;
    }

    public final void saveScreenshot(ExtensionContext extensionContext) throws IOException {
        String baseFileName = extensionContext.getTestMethod().get().getName();

        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        try {
            String path = "target/screenshots";
            new File(path).mkdirs();
            try (FileOutputStream out = new FileOutputStream(path + File.separator + baseFileName + ".png")) {
                out.write(screenshot);
                System.out.println("[[ATTACHMENT|" + path + File.separator + baseFileName + ".png]]");
            }
        } catch (Exception e) {
            System.out.println("screenshot failed:" + e.getMessage());
        }
        Allure.addAttachment("Screenshot on Failure", "image/png", ByteSource.wrap(screenshot).openStream(), ".png");
    }
}
