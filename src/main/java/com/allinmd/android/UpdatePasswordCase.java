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
import com.allinmd.page.LoginPageElements;
import com.allinmd.page.PerCenterPageElements;
import com.allinmd.util.Utils;

/**
 * 修改密码并验证是否修改
 * @author sun
 *
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class UpdatePasswordCase {
	private static AndroidDriver<AndroidElement> driver;
	private static final Logger LOG = Logger.getLogger(UpdatePasswordCase.class);
	private String username;
	private String new_password;
	private String old_password;

	@BeforeClass
	public void setUp() {
		LOG.info("-------------------------  case21-唯医登录-修改密码  -------------------------");
		driver = AndroidServer.androidDriverRun();
    	new PerCenterPageElements(driver);
		new LoginPageElements(driver);
		new IndexPageElements(driver);
	}
	
	@AfterClass  
	public void tearDown() {	
		driver.quit();
		Utils.sleep(2);
		Utils.setInputMethod();
		LOG.info("-------------------------  已完成: case21-唯医登录-修改密码  -------------------------\n\n\n" );
	} 
	
	/**
	 * 修改密码
	 * @param username
	 * @param password
	 * @param newPassword
	 */
	@Test (priority = 1)
	public void updatePassword() {
		this.username = Dom4jXml.getUserValue("resetPassUser");
		this.old_password = Dom4jXml.getUserValue("globalPassword");
		this.new_password = Dom4jXml.getUserValue("updatePass");
//		LoginPageElements.allinLogin(username, password);
		LOG.info("唯医用户[" + username + "]登录");
    	driver.findElement(By.id(LoginPageElements.LOGIN_ALLINMD_USER)).sendKeys(username);
    	Utils.sleep(1);
    	
    	driver.findElement(By.id(LoginPageElements.LOGIN_ALLINMD_BTN)).click();
    	
    	WebElement pw = driver.findElement(By.id(LoginPageElements.LOGIN_ALLINMD_PW));
    	pw.click();
    	pw.sendKeys(old_password);
    	Utils.sleep(1);
    	
    	
    	driver.findElement(By.id(LoginPageElements.LOGIN_ALLINMD_INTO)).click();
    	By by = By.id(LoginPageElements.ERROR_MESSAGE);
    	if (Utils.isElementExist(driver, by)  && Utils.getText(driver, by).equals("密码不正确")) {
//    		pw.clear();
        	driver.findElement(By.id(LoginPageElements.LOGIN_ALLINMD_PW)).sendKeys(new_password);
        	driver.findElement(By.id(LoginPageElements.LOGIN_ALLINMD_BTN)).click();
        	
        	//IndexPageElements.knowMessage();
        	LOG.info("进入个人主页");
        	IndexPageElements.gotoPer();
    		LOG.info("修改密码");
    		PerCenterPageElements.upPassword(new_password, old_password);
  
    		PerCenterPageElements.logOut();
    		
    		LoginPageElements.allinLogin(username, old_password);
    	} 
    	LOG.info("进入个人主页");
    	IndexPageElements.gotoPer();
		
		LOG.info("修改密码");
		PerCenterPageElements.upPassword(old_password, new_password);
	}
	
	/**
	 * 登录以验证新密码是否生效，最后还原密码
	 * @param username
	 * @param password
	 * @param newPassword
	 */
	@Test (priority = 2,dependsOnMethods = "updatePassword")
	public void assertPassword() {
		PerCenterPageElements.logOut();
		LOG.info("用新密码登录");
		LoginPageElements.allinLogin(username, new_password);
		LOG.info("进入个人主页");
		IndexPageElements.gotoPer();
		LOG.info("修改回原密码");
		PerCenterPageElements.upPassword(new_password, old_password);
	}
	
	/**
	 * 登出
	 */
	@Test (priority = 3,dependsOnMethods = "assertPassword",alwaysRun = true)
	public void logout() {
		PerCenterPageElements.logOut();
	}
}
