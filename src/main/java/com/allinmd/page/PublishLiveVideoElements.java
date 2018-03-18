package com.allinmd.page;

import com.allinmd.util.Utils;
import com.runtime.listener.Assertion;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.Listeners;

import java.util.List;

/**
 * 发布视频直播页面元素
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class PublishLiveVideoElements {
    private static final Logger log = Logger.getLogger(PublishLiveVideoElements.class);
    //发布直播按钮
    public static final String pub_live_video = "com.allin.social:id/iv_pop_publish_live";

    //竖屏直播
    public static final String screen_live = "com.allin.social:id/tv_live_begin_vertical_screen";

    //横屏直播
    public static final String landscape_live = "com.allin.social:id/tv_live_begin_vertical_landscape";

    //房间标题输入框
    public static final String live_title_input = "com.allin.social:id/et_live_begin_input_title";

    //直播分类
    public static final String live_type = "com.allin.social:id/tv_live_begin_type";

    //选择直播分类弹窗确定按钮
    public static final String type_save_btn = "com.allin.social:id/tv_save";

    //选择直播时间
    public static final String live_time = "com.allin.social:id/tv_live_begin_time";

    //房间公告输入框
    public static final String live_notice_input = "com.allin.social:id/et_live_begin_input_notice";

    //封面上传按钮
    public static final String up_cover_btn = "com.allin.social:id/ll_live_begin_input_cover";

    //邀大家来看朋友圈按钮
    public static final String share_pyq = "com.allin.social:id/iv_live_begin_share_pyq";

    //创建直播按钮
    public static final String create_live_btn = "com.allin.social:id/btn_live_begin_create";

    //我的直播页面直播时间list
    public static final String my_live_time = "com.allin.social:id/tv_my_live_time";

    //我的直播房间title list
    public static final String my_live_title = "com.allin.social:id/tv_live_describe";

    //立即直播
    public static final String begin_open_live = "com.allin.social:id/btn_live_detail_appointment";

    private static AndroidDriver<AndroidElement> driver;

    public PublishLiveVideoElements(AndroidDriver<AndroidElement> driver) {
        PublishLiveVideoElements.driver = driver;
    }

    /**
     * 创建直播
     */
    public static void pubLiveVideo() {
        Utils.waitElement(driver, By.id(pub_live_video), 5);
        driver.findElement(By.id(pub_live_video)).click();
        if(Utils.waitElement(driver, By.id(screen_live), 5)) {
            driver.findElement(By.id(screen_live)).click(); //竖屏直播
            //输入直播标题
            driver.findElement(By.id(live_title_input)).sendKeys("自动测试直播");
            //选择直播类型
            driver.findElement(By.id(live_type)).click();
            driver.findElement(By.id(type_save_btn)).click();
            //选择直播时间
            driver.findElement(By.id(live_time)).click();
            Utils.sleep(1.5);
            driver.swipe(800, 1615, 800, 1440, 500);
            driver.findElement(By.id(type_save_btn)).click();
            Utils.sleep(1.5);
            String createLiveTime = driver.findElement(By.id(live_time)).getText();
            //输入房间公告
            driver.findElement(By.id(live_notice_input)).sendKeys("这是房间的公告！");
            //上传封面
            //driver.findElement(By.id(up_cover_btn)).click();
            //取消默认邀大家来看朋友圈
            driver.findElement(By.id(share_pyq)).click();
            //创建
            Utils.swipe(driver, By.id(create_live_btn), 1);
            driver.findElement(By.id(create_live_btn)).click();

            boolean flag = false;
            if(Utils.waitElement(driver, By.id(my_live_title), 5)) {
                List<AndroidElement> myLiveTitle_list = driver.findElements(By.id(my_live_title));
                List<AndroidElement> myLiveTime_list = driver.findElements(By.id(my_live_time));
                for (int i=0; i<myLiveTitle_list.size(); i++) {
                    String myLiveTime = myLiveTime_list.get(i).getText();
                    boolean times = createLiveTime.substring(createLiveTime.length()-11).equals(myLiveTime);
                    if (myLiveTitle_list.get(i).getText().equals("自动测试直播") && times) {
                        log.info("我的直播列表找到发布的直播房间，创建成功！");
                        myLiveTitle_list.get(i).click();
                        flag = true;
                        break;
                    }
                }
            }else {
                log.error("创建直播后未能跳转至我的直播列表页请检查是否发布成功！");
            }
            Assertion.assertEquals(true, flag, "我的直播列表未找到刚发布的直播，请检查！");
        }
    }

    /**
     * 开启直播
     */
    public static void openLiveVideo() {
        if (Utils.waitElement(driver, By.id(begin_open_live), 5)) {
            driver.findElement(By.id(begin_open_live)).click();
        }else {

        }
    }

}
