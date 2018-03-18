package com.allinmd.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.allinmd.driver.AndroidServer;
import com.allinmd.file.Dom4jXml;
import com.allinmd.page.LoginPageElements;
import com.allinmd.page.PerCenterPageElements;
import com.allinmd.util.Utils;
import com.runtime.listener.Assertion;

/**
 * 邮箱登录-退出登录
 * @author sun
 *
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class LoginEmailCase {
	private static AndroidDriver<AndroidElement> driver;
	private static final Logger LOG = Logger.getLogger(LoginEmailCase.class);
	
	@BeforeClass
	public void setUp() {
		LOG.info("-------------------------  case09-邮箱登录-退出登录  -------------------------");
		driver = AndroidServer.androidDriverRun();
    	new LoginPageElements(driver);
    	new PerCenterPageElements(driver);
	}
	
	@AfterClass  
	public void tearDown() {	
		driver.quit();
		Utils.sleep(2);
		Utils.setInputMethod();
		LOG.info("-------------------------  已完成: case09-邮箱登录-退出登录  -------------------------\n\n\n" );
	} 
	
	/**
	 * 验证"唯医会员登录"按钮是否存在以确定页面正常跳转
	 */
	@Test (priority = 1)
	public void assertRegButton() {
		
		if (LoginPageElements.allmdBtnIsExist() == true) {
			LOG.info("\"登录\"按钮存在，页面正确");
		}
		else {
			LOG.error("\"登录\"按钮未找到，页面不符或元素改变，请检查！");
			Assertion.assertEquals(false, true, "\"登录\"按钮未找到，页面不符或元素改变，请检查！");
		}
		
    }
	
	/**
	 * 邮箱登录唯医
	 */
	@Test (priority = 2,dependsOnMethods = "assertRegButton")
	public void loginEmail() {
		LOG.info("邮箱账户登录唯医");
		LoginPageElements.allinLogin(Dom4jXml.getUserValue("loginEmailUser"), Dom4jXml.getUserValue("globalPassword"));
	}
	
	@Test (priority = 3,dependsOnMethods = "loginEmail",alwaysRun = true)
	public void logoutEmail() {
		PerCenterPageElements.logOut();
	}
}
