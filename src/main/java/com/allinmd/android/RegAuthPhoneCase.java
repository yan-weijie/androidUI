package com.allinmd.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.allinmd.page.AuthPageElements;
import com.allinmd.page.IndexPageElements;
import com.allinmd.page.LoginPageElements;
import com.allinmd.page.RegPageElements;
import com.allinmd.page.PerCenterPageElements;
import com.allinmd.driver.AndroidServer;
import com.allinmd.file.Dom4jXml;
import com.allinmd.httpclient.CleanData;
import com.allinmd.util.ConnDataBase;
import com.allinmd.util.Utils;
import com.runtime.listener.Assertion;

/**
 * 手机号注册并认证
 * @author sun
 *
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class RegAuthPhoneCase {
	private static AndroidDriver<AndroidElement> driver;
	public static String username;
	private CleanData clearData;
	private static final Logger LOG = Logger.getLogger(RegAuthPhoneCase.class);
	
	@BeforeClass
	public void setUp() {
		LOG.info("-------------------------  case02-手机注册-医师认证  -------------------------");
		username = Dom4jXml.getUserValue("regPhone");
		ConnDataBase connSql = new ConnDataBase();
		connSql.setUpWrite();

		LOG.info("删除"+username+"在数据库中的原有数据");
		clearData = new CleanData();
		clearData.invalidUser(username);
		driver = AndroidServer.androidDriverRun();
		new RegPageElements(driver);
    	new IndexPageElements(driver);
    	new AuthPageElements(driver);
    	new LoginPageElements(driver);
    	new PerCenterPageElements(driver);

	}
	
	@AfterClass  
	public void tearDown() {	
		driver.quit();
		Utils.sleep(1);
		Utils.setInputMethod();
		LOG.info("-------------------------  已完成: case02-手机注册-医师认证  -------------------------\n\n\n" );
	} 
	
	/**
	 *  验证"立即注册"按钮是否存在以确定页面正常跳转
	 */
	@Test (priority = 1)
	public void assertRegButton() {
		if (RegPageElements.regBtnIsExist()) {
			LOG.info("注册按钮存在，页面正确");
		}
		else {
			LOG.error("\"注册\"按钮未找到，页面不符或元素改变，请检查！");
			Assertion.assertEquals(false, true, "\"立即注册\"按钮未找到，页面不符或元素改变，请检查！");
		}
		
    }
	
	/**
	 * 注册手机账号
	 */
	@Test (priority = 2 , dependsOnMethods = "assertRegButton")
	public void regPhone() {
//		username = RandomStr.randomPhone();
		LOG.info("注册手机号："+username);

		LOG.info("开始注册手机用户");
		RegPageElements.regPhoneFlow(username, Dom4jXml.getUserValue("globalPassword"));
	}
		
	/**
	 * 认证医师
	 */
	@Test (priority = 3 , dependsOnMethods = "regPhone")
//	@Test (groups = {"phone_reg_auth"})
	public void regAuthPhone() {
		LOG.info("认证页面，医师信息填写");
		AuthPageElements.doctorCer();
	}
	
	/**
	 * 登出
	 */
	@Test (priority = 4 ,dependsOnMethods = "regPhone",alwaysRun = true)
	public void logout() {		
		PerCenterPageElements.logOut();
	}
}
