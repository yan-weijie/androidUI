package com.allinmd.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import java.util.List;

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
import com.allinmd.page.ListPageElements;
import com.allinmd.page.LoginPageElements;
import com.allinmd.page.PerCenterPageElements;
import com.allinmd.page.TerminalPageElements;
import com.allinmd.util.Utils;
import com.runtime.listener.Assertion;

/**
 * 评论-收藏、取消收藏、个人中心—收藏的内容验证收藏的评论
 * @author sun
 *
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class CollectCheckCommentCase {
	private static AndroidDriver<AndroidElement> driver;
	private static final Logger LOG = Logger.getLogger(CollectCheckCommentCase.class);
	private static String commentText;
	
	@BeforeClass
	public void setUp() {
		LOG.info("-------------------------  case29-评论-收藏/取消收藏/个人中心查看收藏  -------------------------");
		driver = AndroidServer.androidDriverRun();
    	new LoginPageElements(driver);
    	new IndexPageElements(driver);
    	new ListPageElements(driver);
    	new TerminalPageElements(driver);
    	new PerCenterPageElements(driver);
	}
	
	@AfterClass  
	public void tearDown() {
		driver.close();
	//	driver.quit();
		Utils.sleep(2);
		Utils.setInputMethod();
		LOG.info("-------------------------  已完成: case29-评论-收藏/取消收藏/个人中心查看收藏  -------------------------\n\n\n" );
	} 
	
	@Test (priority = 1)
	public void allinLogin() {
		LoginPageElements.allinLogin(Dom4jXml.getUserValue("loginPhoneUser"), Dom4jXml.getUserValue("globalPassword"));
	}
	
	/**
	 * 登出
	 */
	@Test (priority = 5,dependsOnMethods = "cancelCollectComment",alwaysRun = true)
	public void logout() {	
		PerCenterPageElements.logOut();
	}
	
	/**
	 * 取消收藏
	 */
	@Test (priority = 6,dependsOnMethods = "logout",alwaysRun = true)
	public void deleteCollection() {	
		CleanData cl = new CleanData();
		cl.deleteCollection(Dom4jXml.getUserValue("loginPhoneUser"));
	}

}
