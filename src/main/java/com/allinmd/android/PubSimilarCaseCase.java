package com.allinmd.android;

import com.allinmd.driver.AndroidServer;
import com.allinmd.file.Dom4jXml;
import com.allinmd.httpclient.CleanData;
import com.allinmd.httpclient.CreateData;
import com.allinmd.page.*;
import com.allinmd.util.ConnDataBase;
import com.allinmd.util.Utils;
import com.runtime.listener.Assertion;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

/**
 * 引用病例评论，病例发布
 */
public class PubSimilarCaseCase {
    private static AndroidDriver<AndroidElement> driver;
    private static final Logger log = Logger.getLogger(PubEditCaseCase.class);
    private ConnDataBase connSql = new ConnDataBase();
    private String case_title;
    private String age;
    private String main_suit;
    private String case_talk;
    private String username;
    private String password;

    @BeforeClass
    public void setUp() {
        log.info("-------------------------  case17-病例-发布/编辑  -------------------------");
        driver = AndroidServer.androidDriverRun();
        new LoginPageElements(driver);
        new PublishCaseElements(driver);
        new PerCenterPageElements(driver);
        new IndexPageElements(driver);
        username = Dom4jXml.getUserValue("loginPhoneUser");
        password = Dom4jXml.getUserValue("globalPassword");
        case_title = Dom4jXml.getPublishValue("caseTitle");
        case_talk = Dom4jXml.getPublishValue("caseTalk");
        age = Dom4jXml.getPublishValue("age");
        main_suit = Dom4jXml.getPublishValue("mainSuit");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        Utils.sleep(2);
        Utils.setInputMethod();
        connSql.tearDown();
        log.info("-------------------------  已完成: case17-病例-发布/编辑  -------------------------\n\n\n" );
    }

    @Test(priority = 1)
    public void allinLogin() {
        LoginPageElements.allinLogin(username, password);
    }

    /**
     * 从个人中心进入终端页
     */
    @Test (priority = 2,dependsOnMethods = "allinLogin")
    public void goTerminalPage() {
        //因为依赖发布过病例，调用接口发布一个病例
        CreateData create = new CreateData();
        create.createCase(username, password);

        IndexPageElements.gotoPer();

        if(PerCenterPageElements.checkPublish(case_title)) {
            driver.findElement(By.id(TerminalPageElements.similar_case)).click();

            PublishCaseElements.pubCase(case_title, age, main_suit, case_talk);
            // 发布病例
            String tiaozhuan_title = PublishCaseElements.submitCase();
            Utils.sleep(3);
            driver.findElement(By.id(TerminalPageElements.reply_btn)).click();
            if(Utils.waitElement(driver, By.id(TerminalPageElements.comment_content_list),10)) {
                List<AndroidElement> terminal_reply_list = driver.findElements(By.id(TerminalPageElements.comment_content_list));
                String comment_content = terminal_reply_list.get(0).getText();
                if(comment_content.substring(6, 12).equals(case_title.substring(0, 6))) {
                    log.info("引用病例回复成功！");
                }else {
                    Assertion.assertEquals(true, false, "资源第一条评论内容：\""+comment_content+"\" 与发布的评论：\""+case_title+"\" 不一致，请检查！");
                }
            }else {
                Assertion.assertEquals(false, true, "引用病例评论发布后未能跳转到终端页，请检查评论发布是否失败！");
            }

        }else {
            Assertion.assertEquals(true, false, "个人中心未找到发布的病例！请检查！");
        }
    }

    /**
     * 登出
     */
    @Test (priority = 3,dependsOnMethods = "goTerminalPage",alwaysRun = true)
    public void logout() {
        PerCenterPageElements.logOut();
    }

    /**
     * 无效病例
     */
    @Test (priority = 5,dependsOnMethods = "logout",alwaysRun = true)
    public void invalidCase() {
        CleanData cleanData = new CleanData();
        cleanData.invalidCase(username);
    }
}
