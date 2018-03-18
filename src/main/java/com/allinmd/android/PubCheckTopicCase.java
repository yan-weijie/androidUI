package com.allinmd.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.allinmd.driver.AndroidServer;
import com.allinmd.file.Dom4jXml;
import com.allinmd.httpclient.CleanData;
import com.allinmd.page.IndexPageElements;
import com.allinmd.page.LoginPageElements;
import com.allinmd.page.PerCenterPageElements;
import com.allinmd.page.PublishTopicElements;
import com.allinmd.util.ConnDataBase;
import com.allinmd.util.Utils;

/**
 * 话题-发布\查看
 * @author sun
 *
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class PubCheckTopicCase {
	private static AndroidDriver<AndroidElement> driver;
	private static final Logger LOG = Logger.getLogger(PubCheckTopicCase.class);
	private ConnDataBase connSql = new ConnDataBase();
	private String topic_title;
	private String topic_talk;
	private String username;
	private  String password;
	
	@BeforeClass
	public void setUp() {
		LOG.info("-------------------------  case20-话题-发布/个人中心查看发布  -------------------------");
		driver = AndroidServer.androidDriverRun();
		new LoginPageElements(driver);
		new PublishTopicElements(driver);
		new IndexPageElements(driver);
		new PerCenterPageElements(driver);
		topic_title = Dom4jXml.getPublishValue("topicTitle");
		topic_talk = Dom4jXml.getPublishValue("topicTalk");
		username = Dom4jXml.getUserValue("loginPhoneUser");
		password = Dom4jXml.getUserValue("globalPassword");
	}
	
	@AfterClass  
	public void tearDown() {	
		driver.quit();
		Utils.sleep(2);
		Utils.setInputMethod();
		connSql.tearDown();
		LOG.info("-------------------------  已完成: case20-话题-发布/个人中心查看发布  -------------------------\n\n\n" );
	} 
	
	@Test (priority = 1)
	public void allinLogin() {
		LoginPageElements.allinLogin(username, password);
	}
	
	/**
	 * 发布话题
	 */
	@Test (priority = 2, dependsOnMethods = "allinLogin")
	public void pubTopic() {
		
		PublishTopicElements.pubTopic(topic_title, topic_talk);
		
		WebElement back = driver.findElement(By.id("com.allin.social:id/iv_back"));
		back.click();
	}
	
	/**
	 * 到个人中心——发布的话题查看刚发布的话题
	 * @param topic_title
	 */
	@Test (priority = 3, dependsOnMethods = "pubTopic")
	public void checkTopic() {
		IndexPageElements.gotoPer();
		boolean flag = PerCenterPageElements.checkPublish(topic_title);
		
		if(flag) {
			WebElement back = driver.findElement(By.id("com.allin.social:id/iv_back"));
			back.click();
		}else {
			LOG.info("在个人中心验证发布的话题:false");
		}	
	}
	
	/**
	 * 登出
	 */
	@Test (priority = 5,dependsOnMethods = "checkTopic",alwaysRun = true)
	public void logout() {			
		PerCenterPageElements.logOut();
	}
	
	/**
	 * 无效话题
	 */
	@Test (priority = 6,dependsOnMethods = "logout",alwaysRun = true)
	public void invalidTopic() {	
		CleanData cleanData = new CleanData();
		cleanData.invalidTopic(username);
	}

}
