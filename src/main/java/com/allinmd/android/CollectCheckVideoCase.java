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
import com.runtime.listener.Assertion;

/**
 * 视频-收藏、取消收藏、个人中心—收藏的内容验证收藏的视频
 * @author sun
 *
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class CollectCheckVideoCase {
	private static AndroidDriver<AndroidElement> driver;
	private static final Logger LOG = Logger.getLogger(CollectCheckVideoCase.class);
	private static String videoName;
	
	@BeforeClass
	public void setUp() {
		LOG.info("-------------------------  case29-视频-收藏/取消收藏/个人中心查看收藏  -------------------------");
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
		LOG.info("-------------------------  已完成: case29-视频-收藏/取消收藏/个人中心查看收藏  -------------------------\n\n\n" );
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
	public void collectVideo() {

		LOG.info("进入视频终端页");
		IndexPageElements.gotoVideoList();
		ListPageElements.gotoTabListFinalPage("视频", By.id(ListPageElements.VIDEO_LIST_RESOURCE_TITLE), By.id(ListPageElements.VIDEO_LIST_RESOURCE_TITLE));
	
		Utils.sleep(1);
		LOG.info("进行终端页收藏操作");
		TerminalPageElements.videoColection();
		
		WebElement case_name = driver.findElement(By.id(TerminalPageElements.video_name));
		videoName = case_name.getText();
		LOG.info("收藏的视频名: " + videoName);

		WebElement terminal_back = driver.findElement(By.id(TerminalPageElements.video_back));		
		terminal_back.click();
		Utils.sleep(1);
	}
	
	/**
	 * 个人中心—收藏的内容验证收藏的视频
	 */
	@Test (priority = 3,dependsOnMethods = "collectVideo")
	public void checkCollectVideo() {
		LOG.info("个人中心，验证收藏的视频");
		IndexPageElements.gotoPer();
		
		PerCenterPageElements.checkCollection("收藏", videoName, "视频");
		
	}
	
	/**
	 * 取消收藏
	 */
	@Test (priority = 4,dependsOnMethods = "checkCollectVideo")
	public void cancelCollectVideo() {
		LOG.info("进行取消收藏操作");
		PerCenterPageElements.deleteColection(videoName);
		Utils.sleep(1);	
	}
	
	/**
	 * 登出
	 */
	@Test (priority = 5,dependsOnMethods = "cancelCollectVideo",alwaysRun = true)
	public void logout() {
		WebElement back_per = driver.findElement(By.id(TerminalPageElements.back_btn));
		back_per.click();
		Utils.sleep(1);
		PerCenterPageElements.logOut();
	}
}
