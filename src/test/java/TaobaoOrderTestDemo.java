import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

//本用例使用第三方模拟器测试，仿真机。真机与原生模拟器的系统弹窗不同
public class TaobaoOrderTestDemo {

    //因版本签署问题，需要提前手动安装好apk，脚本或adb安装程序内部会做限制，导致重要功能无法使用，如登录。
    //如需测试完整功能需debug包，并且apk包含机器签名
    private static Logger log = LoggerFactory.getLogger(TaobaoStartUpTestDemo.class);
    private static CustomWindowsDriver taobaoSession = null;

    @BeforeClass
    public void initSimulator()throws MalformedURLException {
        //com.taobao.taobao/com.taobao.tao.welcome.Welcome
        log.debug("setup simulator");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appPackage", "com.taobao.taobao");
        capabilities.setCapability("appActivity", "com.taobao.tao.welcome.Welcome");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"127.0.0.1:62001");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,"5.1.1");
        capabilities.setCapability(MobileCapabilityType.NO_RESET,"True");
        taobaoSession = new CustomWindowsDriver(new URL("http://127.0.0.1:4723/wd/hub/"), capabilities);
        //添加操作回调监听
//        taobaoSession = EventFiringWebDriverFactory.getEventFiringWebDriver(taobaoSession,new ElementListener());
//        taobaoSession.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
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

    @Test(priority = 1)
    public void startApp(){
        //协议弹窗
//        taobaoSession.displayWaitById(20,"com.taobao.taobao:id/provision_positive_button","provision dialog",false);
    }

    @Test(priority = 2)
    public void swipAddItem(){

        //选择第一键商品
        taobaoSession.displayWaitSwipeByXpath(30,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.support.v7.widget.RecyclerView/android.widget.LinearLayout/android.support.v4.view.ViewPager/android.support.v7.widget.RecyclerView/android.widget.FrameLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout","第二件商品",true);
        taobaoSession.displayWaitOnClickByXpath(30,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.RelativeLayout[2]/android.widget.TextView","加入购物车",true);
        taobaoSession.displayWaitOnClickByXpath(10,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.LinearLayout","确定",false);
        taobaoSession.displayWaitById(10,"com.taobao.taobao:id/btn_skucard_closecard","物品选择失败，关闭窗口",false);
        taobaoSession.displayWaitByAccessibilityId(10,"返回","返回主页",true);

        //选择第二键商品
        taobaoSession.displayWaitSwipeByXpath(30,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.support.v7.widget.RecyclerView/android.widget.LinearLayout/android.support.v4.view.ViewPager/android.support.v7.widget.RecyclerView/android.widget.FrameLayout[4]/android.widget.FrameLayout/android.widget.LinearLayout","第三件商品",true);
        taobaoSession.displayWaitOnClickByXpath(30,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.RelativeLayout[2]/android.widget.TextView","加入购物车",true);
        taobaoSession.displayWaitOnClickByXpath(10,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.LinearLayout","确定",false);
        taobaoSession.displayWaitById(10,"com.taobao.taobao:id/btn_skucard_closecard","物品选择失败，关闭窗口",false);

        taobaoSession.displayWaitByAccessibilityId(10,"返回","返回主页",true);
        //android.widget.RelativeLayout[@content-desc="玉米热狗肠32g*15支"]/android.widget.RelativeLayout/android.widget.TextView
        //android.widget.RelativeLayout[@content-desc="200mL"]/android.widget.RelativeLayout/android.widget.TextView
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test(priority = 3)
    public void Shopping(){
        //选择购物车
        taobaoSession.displayWaitOnClickByXpath(30,"//android.widget.FrameLayout[@content-desc=\"购物车\"]/android.widget.ImageView","选择规格",false);
        taobaoSession.displayWaitOnClickByXpath(10,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.support.v7.widget.RecyclerView/android.widget.FrameLayout[5]/android.widget.LinearLayout/android.widget.CheckBox","选择购物车中商品",false);
        taobaoSession.displayWaitOnClickByXpath(10,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.support.v7.widget.RecyclerView/android.widget.FrameLayout[8]/android.widget.LinearLayout/android.widget.CheckBox","选择购物车中商品",false);
        taobaoSession.displayWaitOnClickByXpath(10,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout[2]/android.support.v7.widget.RecyclerView/android.widget.FrameLayout[11]/android.widget.LinearLayout/android.widget.CheckBox","选择购物车中商品",false);
        taobaoSession.slideUp();
        taobaoSession.slideUp();
        taobaoSession.slideUp();
        taobaoSession.slideUp();
        taobaoSession.displayWaitById(10,"com.taobao.taobao:id/button_cart_charge","结算按钮",false);
        taobaoSession.displayWaitOnClickByXpath(10,"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.ImageView","提交订单",true);
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
