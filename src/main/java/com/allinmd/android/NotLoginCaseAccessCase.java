package com.allinmd.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.allinmd.file.Dom4jXml;
import com.allinmd.driver.AndroidServer;
import com.allinmd.page.AuthPageElements;
import com.allinmd.page.IndexPageElements;
import com.allinmd.page.ListPageElements;
import com.allinmd.page.LoginPageElements;
import com.allinmd.page.PerCenterPageElements;
import com.allinmd.util.Utils;
import com.runtime.listener.Assertion;

/**
 * 未登录病例终端页权限判断
 * @author yan
 *
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class NotLoginCaseAccessCase {
	private static AndroidDriver<AndroidElement> driver;
	private static final Logger log = Logger.getLogger(NotLoginCaseAccessCase.class);
	
	@BeforeClass
	public void setUp() {
		log.info("-------------------------  case11-未登录-病例终端页权限判断  -------------------------");
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
		log.info("-------------------------  已完成: case11-未登录-病例终端页权限判断  -------------------------\n\n\n" );
	}
	
	@Test (priority = 1)
	@Parameters()
	public void skipAllinLogin() {
		LoginPageElements.skipLogin();
	}
	
	/**
	 * 验证未登录病例终端页权限
	 */
	@Test (priority = 2,dependsOnMethods = "skipAllinLogin")
	public void notLoginCaseAccess() {
		IndexPageElements.gotoCaseList();
		ListPageElements.gotoTabListFinalPage("病例", By.id(ListPageElements.case_list_two_title), By.id(ListPageElements.CASE_LIST_RESOURCE_TITLE));
		
		if(LoginPageElements.allmdBtnIsExist()) {
			log.info("未登录病例终端页权限验证：true");
		}else {
			Assertion.assertEquals(false, true, "未登录病例终端页权限验证:false");
		}
	}
	
	/**
	 * 手机登录唯医
	 */
	@Test (priority = 3,dependsOnMethods = "notLoginCaseAccess")
	public void loginPhone() {
		
		log.info("手机登录唯医");	
    	Utils.waitElement(driver, By.id(LoginPageElements.LOGIN_ALLINMD_USER), 15);
		// 登录账号
		WebElement login_name_text = driver.findElement(By.id(LoginPageElements.LOGIN_ALLINMD_USER));
		login_name_text.sendKeys(Dom4jXml.getUserValue("loginPhoneUser"));
		Utils.sleep(1);
		
		driver.findElement(By.id(LoginPageElements.LOGIN_ALLINMD_BTN)).click();
		Utils.sleep(1);	
		
		// 登录密码
		WebElement login_pwd_text = driver.findElement(By.id(LoginPageElements.LOGIN_ALLINMD_PW));
		login_pwd_text.click();
		login_pwd_text.sendKeys(Dom4jXml.getUserValue("globalPassword"));
		Utils.sleep(1);
		// 立即登录按钮
		driver.findElement(By.id(LoginPageElements.LOGIN_ALLINMD_INTO)).click();
		Utils.sleep(1);
		log.info("判断登录成功是否返回来源页");
		if(Utils.isElementExist(driver, By.xpath(IndexPageElements.INDEX_TAB_CASE))){
			log.info("已成功返回来源页");
		}
		else{
			log.error("未登录  or 登录后未返回列表！");
			Assertion.assertEquals(false, true, "未登录  or 登录后未返回列表！");
		}
	}
	
	/**
	 * 登出
	 */
	@Test (priority = 3,dependsOnMethods = "loginPhone",alwaysRun = true)
	public void logout() {		
		PerCenterPageElements.logOut();
	}
}
