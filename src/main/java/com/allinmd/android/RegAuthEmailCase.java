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
import com.allinmd.httpclient.CleanData;
import com.allinmd.page.LoginPageElements;
import com.allinmd.page.AuthPageElements;
import com.allinmd.page.IndexPageElements;
import com.allinmd.page.RegPageElements;
import com.allinmd.page.PerCenterPageElements;
import com.allinmd.util.ConnDataBase;
import com.allinmd.util.Utils;

/**
 * 邮箱注册并认证
 * @author sun
 *
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class RegAuthEmailCase {
	private static AndroidDriver<AndroidElement> driver;
	public static String username;
	private CleanData clearData;
	private static final Logger LOG = Logger.getLogger(RegAuthEmailCase.class);
	
	@BeforeClass
	public void setUp() {
		LOG.info("-------------------------  case01-邮箱注册-医师认证  -------------------------");
		username = Dom4jXml.getUserValue("regEmail");
		LOG.info("注册邮箱：" + username);
		ConnDataBase connSql = new ConnDataBase();
		connSql.setUpWrite();

		LOG.info("删除 " + username + " 在数据库中的原有数据");
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
		Utils.sleep(2);
		Utils.setInputMethod();
		LOG.info("-------------------------  已完成: case01-邮箱注册-医师认证  -------------------------\n\n\n" );
	} 
	
	/**
	 *  验证"立即注册"按钮是否存在以确定页面正常跳转
	 */
	@Test (priority = 1)
	public void assertRegButton() {
		LoginPageElements.assertSetupGui();
		
    }
	
	/**
	 * 注册邮箱账号
	 */
	@Test (priority = 2,dependsOnMethods = "assertRegButton")
	public void regEmail() {
//		username = RandomStr.randomEmail();

		LOG.info("开始注册邮箱用户: " + username);
		RegPageElements.regEmailFlow(username, Dom4jXml.getUserValue("globalPassword"));
	}
		
	/**
	 * 认证医师
	 */
	@Test (priority = 3 , dependsOnMethods = "regEmail")
	public void regAuthEmail() {
		LOG.info("认证页面，医师基本信息填写");
		AuthPageElements.doctorCer();
//		if (IndexPageElements.assertGoToIndex() == true) {
//			LOG.info("认证医师成功，进入首页");
//		}
//		else {
//			LOG.error("未成功进入首页，认证阶段有误");
//			Assertion.assertEquals(false, true,"未正常回到主页");
//		}

	}

	/**
	 * 登出
	 */
	@Test (priority = 4 , dependsOnMethods = "regEmail",alwaysRun = true)
	public void logout() {
		PerCenterPageElements.logOut();
	}
}
