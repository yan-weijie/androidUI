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
import com.allinmd.page.AuthPageElements;
import com.allinmd.page.IndexPageElements;
import com.allinmd.page.RegPageElements;
import com.allinmd.page.PerCenterPageElements;
import com.allinmd.util.ConnDataBase;
import com.allinmd.util.Utils;
import com.runtime.listener.Assertion;

/**
 * 邮箱注册-跳过认证
 * @author sun
 *
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class RegEmailCase {
	private static AndroidDriver<AndroidElement> driver;
	public static String username;
	private CleanData clearData;
	private static final Logger LOG = Logger.getLogger(RegEmailCase.class);
	
	@BeforeClass
	public void setUp() {
		LOG.info("-------------------------  case03-邮箱注册-跳过认证  -------------------------");
		username = Dom4jXml.getUserValue("regEmail");
		ConnDataBase connSql = new ConnDataBase();
		connSql.setUpWrite();

		LOG.info("删除" + username + "在数据库中的原有数据");
		clearData = new CleanData();
		clearData.invalidUser(username);
		driver = AndroidServer.androidDriverRun();
    	new RegPageElements(driver);
    	new IndexPageElements(driver);
    	new PerCenterPageElements(driver);
    	new AuthPageElements(driver);
	}
	
	@AfterClass  
	public void tearDown() {	
		driver.quit();
		Utils.sleep(2);
		Utils.setInputMethod();
		LOG.info("-------------------------  已完成: case03-邮箱注册-跳过认证  -------------------------\n\n\n" );
	} 
	
	/**
	 * 验证"立即注册"按钮是否存在以确定页面正常跳转
	 */
	@Test (priority = 1)
	public void assertRegButton() {
		if(RegPageElements.regBtnIsExist() == true){
			LOG.info("\"注册\"存在,页面正确");
		}
		else{
			LOG.error("\"注册\"按钮未找到，页面不符或元素改变，请检查！");
			Assertion.assertEquals(false, true, "\"注册\"按钮未找到，页面不符或元素改变，请检查！");
		}
    }
	
	/**
	 * 注册邮箱账号
	 */
	@Test (priority = 2 , dependsOnMethods = "assertRegButton")
	public void regEmail() {
//		username = RandomStr.randomEmail();

		LOG.info("注册邮箱：" + username);
		
		LOG.info("开始注册邮箱用户");
		RegPageElements.regEmailFlow(username, Dom4jXml.getUserValue("globalPassword"));
		
		AuthPageElements.skipDoctorCer();
		
	}
	
	/**
	 * 登出
	 */
	@Test (priority = 3, dependsOnMethods = "regEmail",alwaysRun = true)
	public void logout() {
		PerCenterPageElements.logOut();
	}
	
}
