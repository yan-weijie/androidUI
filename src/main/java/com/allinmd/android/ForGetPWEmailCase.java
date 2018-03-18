package com.allinmd.android;



import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.apache.log4j.Logger;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.allinmd.driver.AndroidServer;
import com.allinmd.file.Dom4jXml;
import com.allinmd.httpclient.GetResetPasswd;
import com.allinmd.page.ForgetPWPageElements;
import com.allinmd.page.PerCenterPageElements;
import com.allinmd.util.Utils;

/**
 * 邮箱找回密码，并验证密码是否修改
 * @author sun
 *
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class ForGetPWEmailCase {
	private static AndroidDriver<AndroidElement> driver;
	private static final Logger LOG = Logger.getLogger(ForGetPWEmailCase.class);
//	private static WebDriver browser;
	private String username;
	private String password;
	
	@BeforeClass
	public void setUp() {
		LOG.info("-------------------------  case22-邮箱-忘记密码  -------------------------");
		driver = AndroidServer.androidDriverRun();
		new ForgetPWPageElements(driver);
		new PerCenterPageElements(driver);
		new GetResetPasswd();
	}
	
	@AfterClass  
	public void tearDown() {	
		driver.quit();
		Utils.sleep(2);
		Utils.setInputMethod();
		Utils.sleep(2);
		LOG.info("-------------------------  已完成: case22-邮箱-忘记密码  -------------------------\n\n\n" );
	} 
	
	/**
	 * 邮箱找回密码
	 * @param username
	 */
	@Test (priority = 1)
	public void emailFindPW() {
		this.username = Dom4jXml.getUserValue("loginEmailUser");
		this.password = Dom4jXml.getUserValue("globalPassword");
		LOG.info("邮箱找回密码，用户名：" + username + " 密码：" + password);
		ForgetPWPageElements.forgetPassword(username, password);
	}
	
	/**
	 * 登出
	 */
	@Test (priority = 2,dependsOnMethods = "emailFindPW",alwaysRun = true)
	public void logout() {	
		PerCenterPageElements.logOut();
	}
	
}
