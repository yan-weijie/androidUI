package com.allinmd.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.allinmd.driver.AndroidServer;
import com.allinmd.file.Dom4jXml;
import com.allinmd.page.IndexPageElements;
import com.allinmd.page.LoginPageElements;
import com.allinmd.page.PerCenterPageElements;
import com.allinmd.page.PublishCaseElements;
import com.allinmd.util.ConnDataBase;
import com.allinmd.util.Utils;
import com.allinmd.httpclient.CleanData;
import com.runtime.listener.Assertion;

/**
 * 病例-草稿\发布
 * @author yan
 *
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class PubDraftOfCaseCase {
	private static AndroidDriver<AndroidElement> driver;
	private static final Logger log = Logger.getLogger(PubDraftOfCaseCase.class);
	private ConnDataBase connSql = new ConnDataBase();
	private String case_title;
	private String age;
	private String main_suit;
	private String case_talk;
	private String username;
	private  String password;
	
	@BeforeClass
	public void setUp() {
		log.info("-------------------------  case18-病例-草稿/发布  -------------------------");
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
		log.info("-------------------------  已完成: case18-病例-草稿/发布  -------------------------\n\n\n" );
	} 
	
	@Test (priority = 1)
	public void allinLogin() {
		LoginPageElements.allinLogin(username, password);
	}
	
	/**
	 * 发布病例
	 */
	@Test (priority = 2,dependsOnMethods = "allinLogin")
	public void pubCase() {
		log.info("开始创建病例: " + case_title);
		// 创建病例
		PublishCaseElements.pubCase(case_title, age, main_suit, case_talk);
		//保存至草稿
		PublishCaseElements.saveDraft();
		
		log.info("个人中心检查草稿保存");
		String terminalCaseName = "";
		
		IndexPageElements.gotoPer();
		
		if(PerCenterPageElements.checkDraft(case_title)) {
			log.info("开始草稿发布病例");
			terminalCaseName = PublishCaseElements.submitCase();
			Utils.sleep(5);
		}else {
			Assertion.assertEquals(true, false, "未找到保存的草稿：\""+case_title+"\",请检查是否保存成功！");
		}
		
		if (terminalCaseName.equals(case_title) ) {
			log.info("草稿发布病例成功");
		}else {
			log.error("发布病例后跳转的终端页不符，请检查！");
			Assertion.assertEquals(terminalCaseName, case_title, "发布病例后跳转的终端页不符，请检查！");
		}
		
		driver.findElement(By.id("com.allin.social:id/iv_back")).click();
		Utils.sleep(1);
		if(Utils.isElementExist(driver, By.id("com.allin.social:id/iv_back"))) {
			driver.findElement(By.id("com.allin.social:id/iv_back")).click();
		}else {
			driver.navigate().back();
		}
	}
	
	/**
	 * 个人中心效验发布的病例
	 */
	@Test (priority = 3,dependsOnMethods = "pubCase")
	public void checkEditCase() {	
		boolean flag = PerCenterPageElements.checkPublish(case_title);
		if(flag) {		
			driver.findElement(By.id("com.allin.social:id/iv_back")).click();			
		}else {
			Assertion.assertEquals(true, false, "个人中心未找到发布的病例 "+case_title+" 请检查！");
		}	
	}
	
	/**
	 * 登出
	 */
	@Test (priority = 5,dependsOnMethods = "checkEditCase",alwaysRun = true)
	public void logout() {	
		PerCenterPageElements.logOut();
	}
	
	/**
	 * 无效病例
	 */
	@Test (priority = 6,dependsOnMethods = "logout",alwaysRun = true)
	public void invalidCase() {	
		CleanData cleanData = new CleanData();
		cleanData.invalidCase(username);
	}
	
}
