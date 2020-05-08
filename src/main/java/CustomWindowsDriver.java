import io.appium.java_client.TouchAction;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.touch.offset.PointOption;
import io.appium.java_client.windows.WindowsDriver;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.util.Assert;

import java.net.URL;

public class CustomWindowsDriver extends WindowsDriver {

    public LogEvent logEvent = null;

    public interface LogEvent{
        void beforeFind(String id,String describe);
        void afterFind(String id,String describe,boolean isFound);
        void afterFind(String id,String describe,boolean isFound,WebElement element);
        void operationBefore(String id,String describe);
        void operationAfter(String id,String describe);
    }

    public CustomWindowsDriver(HttpCommandExecutor executor, Capabilities capabilities) {
        super(executor, capabilities);
    }

    public CustomWindowsDriver(URL remoteAddress, Capabilities desiredCapabilities) {
        super(remoteAddress, desiredCapabilities);
    }

    public CustomWindowsDriver(URL remoteAddress, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(remoteAddress, httpClientFactory, desiredCapabilities);
    }

    public CustomWindowsDriver(AppiumDriverLocalService service, Capabilities desiredCapabilities) {
        super(service, desiredCapabilities);
    }

    public CustomWindowsDriver(AppiumDriverLocalService service, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(service, httpClientFactory, desiredCapabilities);
    }

    public CustomWindowsDriver(AppiumServiceBuilder builder, Capabilities desiredCapabilities) {
        super(builder, desiredCapabilities);
    }

    public CustomWindowsDriver(AppiumServiceBuilder builder, HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(builder, httpClientFactory, desiredCapabilities);
    }

    public CustomWindowsDriver(HttpClient.Factory httpClientFactory, Capabilities desiredCapabilities) {
        super(httpClientFactory, desiredCapabilities);
    }

    public CustomWindowsDriver(Capabilities desiredCapabilities) {
        super(desiredCapabilities);
    }

    public void setLogListener(LogEvent logListener){
        this.logEvent = logListener;
    }

    //显性等待以id方式查找element
    public void displayWaitById( int timeOutInSeconds, String id, String describe,boolean isAssert) {
        WebDriverWait wait = new WebDriverWait(this, timeOutInSeconds);
        WebElement element = null;
        try {
            logEvent.beforeFind(id,describe);
            element = wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
        } catch (Exception e) {
//            e.printStackTrace();
        }
        if(null != element){
            logEvent.afterFind(id,describe,true);
            logEvent.operationBefore(id,describe);
            element.click();
            logEvent.operationAfter(id,describe);
        }else {
            logEvent.afterFind(id,describe,false);
        }
        if(isAssert){
            Assert.notNull(element,"describe : "+describe+",id : "+id+",notFound");
        }
    }

    //显性等待以id方式处理alert
    public void displayWaitAlertById( int timeOutInSeconds, String describe,boolean isAssert) {
        WebDriverWait wait = new WebDriverWait(this, timeOutInSeconds);
        WebElement element = null;
        try {
            logEvent.beforeFind("com.android.packageinstaller:id/permission_message",describe);
            element = wait.until(ExpectedConditions.elementToBeClickable(By.id("com.android.packageinstaller:id/permission_message")));
        } catch (Exception e) {
//            e.printStackTrace();
        }
        if(null != element){
            logEvent.afterFind("com.android.packageinstaller:id/permission_message",describe,true,element);
            logEvent.operationBefore("com.android.packageinstaller:id/permission_message",describe);
            this.switchTo().alert().accept();
            logEvent.operationAfter("com.android.packageinstaller:id/permission_message",describe);
        }else {
            logEvent.afterFind("com.android.packageinstaller:id/permission_message",describe,false);
        }
        if(isAssert){
            Assert.notNull(element,"permission,notFound, Expectation description"+describe);
        }
    }

    //显性等待以AccessibilityId方式查找element
    public void displayWaitByAccessibilityId(int timeOutInSeconds, String id, String describe,boolean isAssert ) {
        final String  acId = id;
        WebDriverWait wait = new WebDriverWait(this, timeOutInSeconds);
        WebElement element = null;
        try {
            logEvent.beforeFind(id,describe);
            element = wait.until(new ExpectedCondition<WebElement>() {
                @NullableDecl
                public WebElement apply(@NullableDecl WebDriver webDriver) {
                    return ((WindowsDriver)webDriver).findElementByAccessibilityId(acId);
                }
            });
        } catch (Exception e) {
//            e.printStackTrace();
        }
        if (null != element) {
            logEvent.afterFind(id,describe,true);
            logEvent.operationBefore(id,describe);
            element.click();
            logEvent.operationAfter(id,describe);
        } else {
            logEvent.afterFind(id,describe,false);
        }
        if(isAssert){
            Assert.notNull(element,"describe : "+describe+",id : "+id+",notFound");
        }
    }

    public void displayWaitSendKeyById(int timeOutInSeconds, String id, String describe,String content,boolean isAssert){
        WebDriverWait wait = new WebDriverWait(this, timeOutInSeconds);
        WebElement element = null;
        try {
            logEvent.beforeFind(id,describe);
            element = wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
        } catch (Exception e) {
//            e.printStackTrace();
        }
        if (null != element) {
            logEvent.afterFind(id,describe,true);
            logEvent.operationBefore(id,describe);
            element.sendKeys(content);
            logEvent.operationAfter(id,describe);
        } else {
            logEvent.afterFind(id,describe,false);
        }
        if(isAssert){
            Assert.notNull(element,"describe : "+describe+",id : "+id+",notFound");
        }
    }

    public void displayWaitOnClickByXpath(int timeOutInSeconds, String xpath, String describe, boolean isAssert){
        final String  path = xpath;
        WebDriverWait wait = new WebDriverWait(this, timeOutInSeconds);
        WebElement element = null;
        try {
            logEvent.beforeFind(xpath,describe);
            element = wait.until(new ExpectedCondition<WebElement>() {
                @NullableDecl
                public WebElement apply(@NullableDecl WebDriver webDriver) {
                    return ((WindowsDriver) webDriver).findElementByXPath(path);
                }
            });
        } catch (Exception e) {
//            e.printStackTrace();
        }
        if (null != element) {
            logEvent.afterFind(xpath,describe,true);
            logEvent.operationBefore(xpath,describe);
            element.click();
            logEvent.operationAfter(xpath,describe);
        } else {
            logEvent.afterFind(xpath,describe,false);
        }
        if(isAssert){
            Assert.notNull(element,"describe : "+describe+",xpath : "+xpath+",notFound");
        }
    }


    public void displayWaitSwipeByXpath(int timeOutInSeconds, String xpath, String describe, boolean isAssert){
        final String  path = xpath;
        long timeout = timeOutInSeconds;
        WebDriverWait wait = new WebDriverWait(this, 1);
        WebElement element = null;
        while (timeout > 0) {
            try {
                logEvent.beforeFind(xpath, describe);
                element = wait.until(new ExpectedCondition<WebElement>() {
                    @NullableDecl
                    public WebElement apply(@NullableDecl WebDriver webDriver) {
                        return ((WindowsDriver) webDriver).findElementByXPath(path);
                    }
                });
            } catch (Exception e) {
//            e.printStackTrace();
            }
            timeout -= 1;
            if(null != element){
                break;
            }else{
                slideUp();
            }
        }
        if (null != element) {
            logEvent.afterFind(xpath,describe,true);
            logEvent.operationBefore(xpath,describe);
            element.click();
            logEvent.operationAfter(xpath,describe);
        } else {
            logEvent.afterFind(xpath,describe,false);
        }
        if(isAssert){
            Assert.notNull(element,"describe : "+describe+",xpath : "+xpath+",notFound");
        }
    }

    /**
     *向上滑动屏幕
     */
    public  void slideUp() {
        try{
            int width = this.manage().window().getSize().width;
            int height = this.manage().window().getSize().height;
            new TouchAction(this).press(PointOption.point(width / 2, height * 3 / 4)).
                    moveTo(PointOption.point(width / 2, height / 10)).release().perform();
        }catch (Exception e){

        }
    }

    /**
      *向下滑动屏幕
      */
    public void slideDown() {
        try{
            int width = this.manage().window().getSize().width;
            int height = this.manage().window().getSize().height;
            new TouchAction(this).press(PointOption.point(width / 2, height / 10))
                    .moveTo(PointOption.point(width / 2, height * 3 / 4)).release().perform();
        }catch (Exception e){

        }
    }

}
