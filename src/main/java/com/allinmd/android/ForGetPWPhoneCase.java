package com.allinmd.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.allinmd.driver.AndroidServer;
import com.allinmd.page.ForgetPWPageElements;
import com.allinmd.page.IndexPageElements;
import com.allinmd.page.LoginPageElements;
import com.allinmd.page.PerCenterPageElements;
import com.allinmd.util.RandomStr;
import com.allinmd.util.Utils;

/**
 * 手机找回密码，并验证密码是否修改
 * @author sun
 *
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class 		ForGetPWPhoneCase {
	private static AndroidDriver<AndroidElement> driver;
	private static final Logger LOG = Logger.getLogger(ForGetPWPhoneCase.class);
	private static String username;
	private static String password;
	
	@BeforeClass
	public void setUp() {
		LOG.info("-------------------------  case23-手机-忘记密码  -------------------------");
		driver = AndroidServer.androidDriverRun();
		new ForgetPWPageElements(driver);
		new LoginPageElements(driver);
		new PerCenterPageElements(driver);
		new IndexPageElements(driver);
	}
	
	@AfterClass  
	public void tearDown() {	
		driver.quit();
		Utils.sleep(2);
		Utils.setInputMethod();
		LOG.info("-------------------------  已完成: case23-手机-忘记密码  -------------------------\n\n\n" );
	} 
	
	/**
	 * 手机找回密码
	 */
	@Test (priority = 1)
	public void phoneFindPW() {
		int ranNum = RandomStr.randomNum(2, 9);
		username = "1270001000" + ranNum;
		password = Integer.toString(RandomStr.randomNum(100000, 999999));
		LOG.info("手机忘记密码，用户名：" + username + " 密码：" + password);
		ForgetPWPageElements.forgetPassword(username, password);
	}
	
	/**
	 * 设置密码后，登录验证
	 */
	@Test (priority = 2,dependsOnMethods = "phoneFindPW")
	public void assertPW() {
		PerCenterPageElements.logOut();
		LOG.info("修改密码后登录验证");
		LoginPageElements.allinLogin(username, password);
	}
	
	/**
	 * 登出
	 */
	@Test (priority = 3,dependsOnMethods = "assertPW",alwaysRun = true)
	public void logout() {		
		PerCenterPageElements.logOut();
	}
}
