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
import com.allinmd.page.ListPageElements;
import com.allinmd.page.LoginPageElements;
import com.allinmd.page.PerCenterPageElements;
import com.allinmd.page.TerminalPageElements;
import com.allinmd.util.ConnDataBase;
import com.allinmd.util.Utils;

/**
 * 病例-收藏、个人中心-收藏的内容查看收藏、取消收藏
 * @author sun
 *
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class CollectCheckCaseCase {
	private static AndroidDriver<AndroidElement> driver;
	private static final Logger LOG = Logger.getLogger(CollectCheckCaseCase.class);
	private static String caseName;
	
	@BeforeClass
	public void setUp() {
		LOG.info("-------------------------  case26-病例-收藏/个人中心查看收藏/取消收藏  -------------------------");
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
		LOG.info("-------------------------  已完成: case26-病例-收藏/个人中心查看收藏/取消收藏 -------------------------\n\n\n" );
	} 
	
	@Test (priority = 1)
	public void allinLogin() {
		LoginPageElements.allinLogin(Dom4jXml.getUserValue("loginPhoneUser"), Dom4jXml.getUserValue("globalPassword"));
	}
	
	/**
	 * 进入终端页
	 * 终端页收藏
	 */
	@Test (priority = 2, dependsOnMethods= "allinLogin")
	public void collectCase() {
//		ConnDataBase connSql = new ConnDataBase();
//		connSql.setUpWrite();
//		CleanData cl = new CleanData();
//		cl.deleteCollection(Dom4jXml.getUserValue("loginPhoneUser"));
		LOG.info("进入终端页");
		IndexPageElements.gotoCaseList();
		caseName = ListPageElements.gotoTabListFinalPage("病例", By.id(ListPageElements.case_list_two_title), By.id(ListPageElements.CASE_LIST_RESOURCE_TITLE));
		
		LOG.info("进行终端页收藏操作");
		TerminalPageElements.colection();
		LOG.info("收藏的病例名: " + caseName);
		
		driver.navigate().back();
		Utils.sleep(1);
	
	}
	
	/**
	 * 个人中心—收藏的内容验证收藏的病例
	 */
	@Test (priority = 3, dependsOnMethods= "collectCase")
	public void checkCollectCase() {
		LOG.info("个人中心，验证收藏的病例");
		IndexPageElements.gotoPer();
		
		PerCenterPageElements.checkCollection("收藏", caseName,"病例");
		
		LOG.info("进行取消收藏操作");
		PerCenterPageElements.deleteColection(caseName);
		Utils.sleep(1);	
	}
	
	/**
	 * 登出
	 */
	@Test (priority = 4, dependsOnMethods= "checkCollectCase", alwaysRun = true)
	public void logout() {
		WebElement back_per = driver.findElement(By.id(TerminalPageElements.back_btn));
		back_per.click();
		Utils.sleep(1);
		PerCenterPageElements.logOut();
	}
}
