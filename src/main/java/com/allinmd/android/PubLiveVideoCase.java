package com.allinmd.android;

import com.allinmd.driver.AndroidServer;
import com.allinmd.file.Dom4jXml;
import com.allinmd.page.*;
import com.allinmd.util.Utils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * Created by yanweijie on 2016/12/20.
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class PubLiveVideoCase {
    private static AndroidDriver<AndroidElement> driver;
    private static final Logger log = Logger.getLogger(PubEditCaseCase.class);

    @BeforeClass
    public void setUp() {
        log.info("-------------------------  case17-病例-发布/编辑  -------------------------");
        driver = AndroidServer.androidDriverRun();
        new LoginPageElements(driver);
        new PublishCaseElements(driver);
        new PerCenterPageElements(driver);
        new IndexPageElements(driver);
        new PublishLiveVideoElements(driver);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        Utils.sleep(2);
        Utils.setInputMethod();
        log.info("-------------------------  已完成: case17-病例-发布/编辑  -------------------------\n\n\n" );
    }

    @Test(priority = 1)
    public void allinLogin() {
        Utils.sleep(8);
        driver.findElement(By.id(IndexPageElements.INDEX_PUBLISH_BTN)).click();
        PublishLiveVideoElements.pubLiveVideo();
        Utils.sleep(60);
        PublishLiveVideoElements.openLiveVideo();

    }
}
