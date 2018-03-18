package com.allinmd.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.allinmd.driver.AndroidServer;
import com.allinmd.file.Dom4jXml;
import com.allinmd.page.AuthPageElements;
import com.allinmd.page.IndexPageElements;
import com.allinmd.page.LoginPageElements;
import com.allinmd.page.ListPageElements;
import com.allinmd.page.PerCenterPageElements;
import com.allinmd.util.Utils;
import com.runtime.listener.Assertion;

/**
 * 未认证用户病例终端页权限判断，验证认证后返回来源页。
 * 依赖RegEmailCase
 * @author sun
 *
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class NotAuthCaseAccessCase {
	private static AndroidDriver<AndroidElement> driver;
	private static final Logger log = Logger.getLogger(NotAuthCaseAccessCase.class);
	
	@BeforeClass
	public void setUp() {
		log.info("-------------------------  case05-未认证-病例终端页权限判断  -------------------------");
		driver = AndroidServer.androidDriverRun();
    	new LoginPageElements(driver);
    	new IndexPageElements(driver);
    	new ListPageElements(driver);
    	new AuthPageElements(driver);
    	new PerCenterPageElements(driver);
	}
	
	@AfterClass  
	public void tearDown() {	
		driver.quit();
		Utils.sleep(2);
		Utils.setInputMethod();
		log.info("-------------------------  已完成: case05-未认证-病例终端页权限判断  -------------------------\n\n\n" );
	} 
	
	@Test (priority = 1)
	@Parameters()
	public void allinLogin() {
		//RegEmailCase.username"yzypg7099@163.com"
		LoginPageElements.allinLogin(Dom4jXml.getUserValue("regPhone"), Dom4jXml.getUserValue("globalPassword"));
	}
	/**
	 * 验证病例终端页医师认证权限判断
	 */
	@Test (priority = 2,dependsOnMethods = "allinLogin")
	public void notAuthCaseAccess() {
		IndexPageElements.gotoCaseList();
		ListPageElements.gotoTabListFinalPage("病例", By.id(ListPageElements.case_list_two_title), By.id(ListPageElements.CASE_LIST_RESOURCE_TITLE));
		if(AuthPageElements.skipAuthIsExist()) {
			log.info("未认证病例终端页权限：true");
		}else {
			Assertion.assertEquals(false, true, "未认证病例终端页权限:false");
		}
	}
	
	/**
	 * 权限判断后认证成功是否返回来源页
	 */
	@Test (priority = 3,dependsOnMethods = "notAuthCaseAccess")
	public void authAllmd() {
		log.info("医师认证");
		AuthPageElements.noAuthDoctorCer();
//		AuthPageElements.selectMajor();
		log.info("判断后认证成功是否返回来源页");
		if(Utils.isElementExist(driver, By.xpath(IndexPageElements.INDEX_TAB_CASE))){
			log.info("已成功返回来源页");
		}
		else{
			log.error("未认证  or 认证后未返回列表！");
			Assertion.assertEquals(false, true, "未认证  or 认证后未返回列表！");
		}

	}
	
	/**
	 * 登出
	 */
	@Test (priority = 4,dependsOnMethods = "authAllmd",alwaysRun = true)
	public void logout() {		
		PerCenterPageElements.logOut();
	}
}
