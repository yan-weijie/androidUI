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
import com.allinmd.page.AuthPageElements;
import com.allinmd.page.IndexPageElements;
import com.allinmd.page.ListPageElements;
import com.allinmd.page.LoginPageElements;
import com.allinmd.page.PerCenterPageElements;
import com.allinmd.util.Utils;
import com.runtime.listener.Assertion;

/**
 * 未登录文库终端页权限判断
 * @author yan
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class NotLoginDocAccessCase {
	private static AndroidDriver<AndroidElement> driver;
	private static final Logger log = Logger.getLogger(NotLoginDocAccessCase.class);
	
	@BeforeClass
	public void setUp() {
		log.info("-------------------------  case13-未登录-文库终端页权限判断  -------------------------");
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
		log.info("-------------------------  已完成: case13-未登录-文库终端页权限判断  -------------------------\n\n\n" );
	}
	
	@Test (priority = 1)
	@Parameters()
	public void skipAllinLogin() {
		LoginPageElements.skipLogin();
	}
	
	/**
	 * 验证未登文库终端页权限
	 */
	@Test (priority = 2,dependsOnMethods = "skipAllinLogin")
	public void notLoginDocAccess() {
		IndexPageElements.gotoDocList();
		ListPageElements.gotoTabListFinalPage("文库", By.id(ListPageElements.doc_list_resource_title), By.id(ListPageElements.doc_list_title));
		
		if(LoginPageElements.allmdBtnIsExist()) {
			log.info("未登录文库终端页权限验证：true");
		}else {
			Assertion.assertEquals(false, true, "未登录文库终端页权限验证:false");
		}
	}
}
