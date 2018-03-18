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
 * CAOS手机账号联合登录、登出
 * @author sun
 *
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class CAOSLoginPhoneCase {
	private static AndroidDriver<AndroidElement> driver;
	private static final Logger LOG = Logger.getLogger(CAOSLoginPhoneCase.class);
	
	@BeforeClass
	public void setUp() {
		LOG.info("-------------------------  case16-手机CAOS登录 -退出登录  -------------------------");
		driver = AndroidServer.androidDriverRun();
		new LoginPageElements(driver);
		new PerCenterPageElements(driver);
	}
	
	@AfterClass  
	public void tearDown() {	
		driver.quit();
		Utils.sleep(2);
		Utils.setInputMethod();
		LOG.info("-------------------------  已完成: case16-手机CAOS登录 -退出登录  -------------------------\n\n\n" );
	} 
	
	/**
	 * 验证"唯医会员登录"按钮是否存在以确定页面正常跳转
	 */
	@Test (priority = 1)
	public void assertRegButton() {
		Assertion.assertEquals(LoginPageElements.allmdBtnIsExist(), true, "\"唯医会员登录\"按钮未找到，页面不符或元素改变，请检查！");
    }
	
	/**
	 * caos手机账户登录唯医
	 */
	@Test (priority = 2)
	public void loginPhone() {
		LOG.info("CAOS会员手机号登录");
		LoginPageElements.caosLogin(Dom4jXml.getUserValue("caosPhoneUser"), Dom4jXml.getUserValue("globalPassword"));
	}
	
	/**
	 * 登出
	 */
	@Test (priority = 3,dependsOnMethods = "loginPhone",alwaysRun = true)
	public void logoutEmail() {
		PerCenterPageElements.logOut();
		Assertion.assertEquals(LoginPageElements.allmdBtnIsExist(), true);
	}
}
