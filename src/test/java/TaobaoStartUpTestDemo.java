import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.testng.annotations.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

//本用例使用Android 模拟器测试。真机与原生模拟器的系统弹窗不同
public class TaobaoStartUpTestDemo {

    private static Logger log = LoggerFactory.getLogger(TaobaoStartUpTestDemo.class);
    private static CustomWindowsDriver taobaoSession = null;

    @BeforeClass
    public void initSimulator()throws MalformedURLException {
        //com.taobao.taobao/com.taobao.tao.welcome.Welcome
        log.debug("setup simulator");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.APP,"D://apk//taobao.apk");
        capabilities.setCapability("appPackage", "com.taobao.taobao");
        capabilities.setCapability("appActivity", "com.taobao.tao.welcome.Welcome");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"emulator-5554");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,"9.0");
        capabilities.setCapability(MobileCapabilityType.NO_RESET,"True");
        taobaoSession = new CustomWindowsDriver(new URL("http://127.0.0.1:4723/wd/hub/"), capabilities);
        //添加操作回调监听
//        taobaoSession = EventFiringWebDriverFactory.getEventFiringWebDriver(taobaoSession,new ElementListener());
        taobaoSession.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//        taobaoSession.manage().timeouts().pageLoadTimeout(20,TimeUnit.SECONDS);
//        taobaoSession.manage().timeouts().setScriptTimeout(20,TimeUnit.SECONDS);
        //添加暂时的自定义日志回调
        taobaoSession.setLogListener(new CustomWindowsDriver.LogEvent() {
            public void beforeFind(String id, String describe) {
                log.debug("Before finding,id is{},describe is {}",id,describe);
            }

            public void afterFind(String id, String describe, boolean isFound) {
                log.debug("Before finding,id is{},describe is {},Node found or not : {}",id,describe,isFound);
            }

            public void afterFind(String id, String describe, boolean isFound, WebElement element) {
                log.debug("Before finding,id is{},describe is {},Node found or not : {}","element text is{}",id,describe,isFound,element.getText());
            }

            public void operationBefore(String id, String describe) {
                log.debug("operationBefore,id is{},describe is {}",id,describe);
            }

            public void operationAfter(String id, String describe) {
                log.debug("operationBefore,id is{},describe is {}",id,describe);
            }
        });
    }

//    卸载安装测试
    @Test(priority = 1)
    public void installTest(){
        if(taobaoSession.isAppInstalled("com.taobao.taobao")){
            log.debug("App already installed,Start uninstall");
            taobaoSession.removeApp("com.taobao.taobao");
            log.debug("Uninstall complete");
        }
        log.debug("Start app installation");
        taobaoSession.installApp("D://apk//taobao.apk");
        Assert.isTrue(taobaoSession.isAppInstalled("com.taobao.taobao"));
        log.debug("是否安装成功：{}", taobaoSession.isAppInstalled("com.taobao.taobao"));
    }

    //首次启动时，进入首页逻辑测试
    @Test(priority = 2)
    void startApp(){
        log.debug("start test...");
        taobaoSession.launchApp();

        //网络弹窗
        taobaoSession.displayWaitById(40,"com.taobao.taobao:id/uik_mdButtonDefaultPositive","Network dialog",false);

        //协议弹窗
        taobaoSession.displayWaitById(20,"com.taobao.taobao:id/provision_positive_button","provision dialog",false);

        //公告提示弹窗
        taobaoSession.displayWaitById(20,"com.taobao.taobao:id/txtConfirm","txt dialog",false);

        //定位通知弹窗
        taobaoSession.displayWaitAlertById(30,"Location services",false);

        //电话通讯弹窗
        taobaoSession.displayWaitAlertById(30,"make and manage phone calls",false);
    }

    @Test(priority = 3)
    public void login(){
        taobaoSession.displayWaitByAccessibilityId(20,"我的淘宝","进入login",true);

        //账号密码登录
        taobaoSession.displayWaitSendKeyById(20,"com.taobao.taobao:id/aliuser_login_mobile_et","Account input","13530150521",true);
        taobaoSession.displayWaitSendKeyById(20,"com.taobao.taobao:id/aliuser_register_sms_code_et","Account input","xxxxxxxxx",true);
        taobaoSession.displayWaitById(20,"com.taobao.taobao:id/aliuser_login_login_btn","login button",true);
        taobaoSession.displayWaitById(20,"com.taobao.taobao:id/aliuser_login_send_smscode_btn","send verification code",true);

        //暂停一段时间，便于查看最后的结果
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void end(){
        taobaoSession.closeApp();
        taobaoSession.quit();
    }
}
