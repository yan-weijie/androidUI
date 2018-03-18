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
import com.allinmd.page.IndexPageElements;
import com.allinmd.page.ListPageElements;
import com.allinmd.page.LoginPageElements;
import com.allinmd.page.PerCenterPageElements;
import com.allinmd.page.TerminalPageElements;
import com.allinmd.util.Utils;

/**
 * 话题-收藏、取消收藏、个人中心—收藏的内容验证收藏的话题
 * @author sun
 *
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class CollectCheckTopicCase {
	private static AndroidDriver<AndroidElement> driver;
	private static final Logger LOG = Logger.getLogger(CollectCheckTopicCase.class);
	private static String topicName;
	
	@BeforeClass
	public void setUp() {
		LOG.info("-------------------------  case28-话题-收藏/取消收藏/个人中心查看收藏  -------------------------");
		driver = AndroidServer.androidDriverRun();
    	new LoginPageElements(driver);
    	new IndexPageElements(driver);
    	new ListPageElements(driver);
    	new PerCenterPageElements(driver);
    	new TerminalPageElements(driver);
	}
	
	@AfterClass  
	public void tearDown() {	
		driver.quit();
		Utils.sleep(2);
		Utils.setInputMethod();
		LOG.info("-------------------------  已完成: case28-话题-收藏/取消收藏/个人中心查看收藏  -------------------------\n\n\n" );
	} 
	
	@Test (priority = 1)
	public void allinLogin() {
		LoginPageElements.allinLogin(Dom4jXml.getUserValue("loginPhoneUser"), Dom4jXml.getUserValue("globalPassword"));
	}
	
	/**
	 * 进入终端页
	 * 终端页收藏
	 */
	@Test (priority = 2,dependsOnMethods = "allinLogin")
	public void collectTopic() {
		LOG.info("进入终端页");
		IndexPageElements.gotoTopicList();
		ListPageElements.gotoTabListFinalPage("话题", By.id(ListPageElements.topic_list_one_p_title), By.id(ListPageElements.topic_list_no_p_title));
		
		LOG.info("进行终端页收藏操作");
		TerminalPageElements.colection();
		
		WebElement case_name = driver.findElement(By.id(TerminalPageElements.topic_name));
		topicName = case_name.getText();
		LOG.info("收藏的话题名: " + topicName);

		WebElement terminal_back = driver.findElement(By.id(TerminalPageElements.back_btn));		
		terminal_back.click();
	
	}
	
	/**
	 * 个人中心—收藏的内容验证收藏的话题
	 */
	@Test (priority = 3,dependsOnMethods = "collectTopic")
	public void checkCollectTopic() {
		LOG.info("个人中心，验证收藏的话题");
		IndexPageElements.gotoPer();
		
		PerCenterPageElements.checkCollection("收藏", topicName, "话题");
		
	}
	
	/**
	 * 取消收藏
	 */
	@Test (priority = 4,dependsOnMethods = "checkCollectTopic")
	public void cancelCollectTopic() {
		LOG.info("进行取消收藏操作");
		PerCenterPageElements.deleteColection(topicName);
		Utils.sleep(1);	
	}
	
	/**
	 * 登出
	 */
	@Test (priority = 5,dependsOnMethods = "cancelCollectTopic",alwaysRun = true)
	public void logout() {	
		WebElement back_per = driver.findElement(By.id(TerminalPageElements.back_btn));
		back_per.click();
		Utils.sleep(1);
		PerCenterPageElements.logOut();
	}
}
