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
 * 文章-收藏、取消收藏、个人中心—收藏的内容验证收藏的文章
 * @author ywj
 *
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class CollectCheckDocCase {
	private static AndroidDriver<AndroidElement> driver;
	private static final Logger LOG = Logger.getLogger(CollectCheckDocCase.class);
	private static String docName;
	
	@BeforeClass
	public void setUp() {
		LOG.info("-------------------------  case27-文章-收藏/取消收藏/个人中心查看收藏  -------------------------");
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
		LOG.info("-------------------------  已完成: case27-文章-收藏/取消收藏/个人中心查看收藏  -------------------------\n\n\n" );
	} 
	
	@Test (priority = 1)
	public void allinLogin() {
		LoginPageElements.allinLogin(Dom4jXml.getUserValue("loginPhoneUser"), Dom4jXml.getUserValue("globalPassword"));
	}
	
	/**
	 * 进入终端页
	 * 终端页收藏
	 */
	@Test (priority = 2, dependsOnMethods = "allinLogin")
	public void collectDoc() {
		LOG.info("进入终端页");
		IndexPageElements.gotoDocList();
		docName = ListPageElements.gotoTabListFinalPage("文库", By.id(ListPageElements.doc_list_resource_title), 
				By.id(ListPageElements.doc_list_title));
		
		LOG.info("进行终端页收藏操作");
		TerminalPageElements.colection();
		
		LOG.info("收藏的文章名: " + docName);

		driver.navigate().back();
	}
	
	/**
	 * 个人中心—收藏的内容验证收藏的话题
	 */
	@Test (priority = 3,dependsOnMethods = "collectDoc")
	public void checkCollectDoc() {
		LOG.info("个人中心，验证收藏的文章");
		IndexPageElements.gotoPer();
		
		PerCenterPageElements.checkCollection("收藏", docName, "文库");
		
	}
	
	/**
	 * 取消收藏
	 */
	@Test (priority = 4,dependsOnMethods = "checkCollectDoc")
	public void cancelCollectDoc() {
		LOG.info("进行取消收藏操作");
		PerCenterPageElements.deleteColection(docName);
		Utils.sleep(1);	
	}
	
	/**
	 * 登出
	 */
	@Test (priority = 5,dependsOnMethods = "cancelCollectDoc",alwaysRun = true)
	public void logout() {	
		WebElement back_per = driver.findElement(By.id(TerminalPageElements.back_btn));
		back_per.click();
		Utils.sleep(1);
		PerCenterPageElements.logOut();
	}
}
