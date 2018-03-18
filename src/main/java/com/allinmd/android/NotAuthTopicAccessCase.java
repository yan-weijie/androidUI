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
import com.allinmd.page.ListPageElements;
import com.allinmd.page.LoginPageElements;
import com.allinmd.page.PerCenterPageElements;
import com.allinmd.util.Utils;
import com.runtime.listener.Assertion;

/**
 * 未认证用户话题终端页权限判断，验证认证后返回来源页。
 * 依赖RegPhoneCase
 * @author yan
 *
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class NotAuthTopicAccessCase {
	private static AndroidDriver<AndroidElement> driver;
	private static final Logger log = Logger.getLogger(NotAuthTopicAccessCase.class);
	
	@BeforeClass
	public void setUp() {
		log.info("-------------------------  case06-未认证-话题终端页权限判断  -------------------------");
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
		log.info("-------------------------  已完成: case06-未认证-话题终端页权限判断  -------------------------\n\n\n" );
	}
	
	@Test (priority = 1)
	@Parameters()
	public void allinLogin() {
		//RegEmailCase.username"yzypg7099@163.com"
		LoginPageElements.allinLogin(Dom4jXml.getUserValue("notAuthUser"), Dom4jXml.getUserValue("globalPassword"));
	}
	/**
	 * 验证话题终端页医师认证权限判断
	 */
	@Test (priority = 2,dependsOnMethods = "allinLogin")
	public void notAuthTopicAccess() {
		IndexPageElements.gotoTopicList();
		ListPageElements.gotoTabListFinalPage("话题", By.id(ListPageElements.topic_list_one_p_title), By.id(ListPageElements.topic_list_no_p_title));
		
		if(AuthPageElements.skipAuthIsExist()) {
			log.info("未认证话题终端页权限：true");
			//取消认证方便退出登录
			AuthPageElements.skipDoctorCer();
		}else {
			Assertion.assertEquals(false, true, "未认证话题终端页权限:false");
		}
	}
	
	/**
	 * 登出
	 */
	@Test (priority = 3,dependsOnMethods = "notAuthTopicAccess",alwaysRun = true)
	public void logout() {		
		PerCenterPageElements.logOut();
	}
}
