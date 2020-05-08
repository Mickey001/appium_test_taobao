import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class taobao {

    private static WindowsDriver TaobaoSession = null;
    private static WebElement CalculatorResult = null;

    public void initSimulator(){
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.APP, "D:\\apk\\taobao.apk");
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"emulator-5554");
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,"Android");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,"9.0");
            TaobaoSession = new WindowsDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
            TaobaoSession.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            TaobaoSession.manage().timeouts().pageLoadTimeout(10,TimeUnit.SECONDS);
            TaobaoSession.manage().timeouts().setScriptTimeout(10,TimeUnit.SECONDS);
        }catch(Exception e){
            e.printStackTrace();
        } finally {
        }
    }


}
