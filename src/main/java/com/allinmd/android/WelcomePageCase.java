package com.allinmd.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.server.SystemClock;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.allinmd.driver.AndroidServer;
import com.allinmd.page.IndexPageElements;
import com.allinmd.page.LoginPageElements;
import com.allinmd.page.PerCenterPageElements;
import com.allinmd.util.Utils;
import com.runtime.listener.Assertion;

/**
 * 欢迎页面 ， 进入主页并完成初始引导
 * @author yan
 *
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class WelcomePageCase {
	
	private AndroidDriver<AndroidElement> driver;
	private static final Logger log = Logger.getLogger(WelcomePageCase.class);
	private String name = "WelcomePageCase";
	@BeforeClass
	public void setup() {
		log.info("-------------------------  欢迎页面  -------------------------");
		Utils.clearAppData();
		driver = AndroidServer.androidDriverRun();
		new LoginPageElements(driver);
		new IndexPageElements(driver);
		new PerCenterPageElements(driver);
	}
	
	@AfterClass  
	public void tearDown() {
		driver.quit();
		Utils.sleep(2);
		Utils.setInputMethod();
		log.info("-------------------------  已完成: 欢迎页面  -------------------------\n\n\n" );
	} 
	
	/**
	 * 首次安装进入app体验一下
	 */
	@Test (priority = 1)
	public void welcomeExperienceIt() {
		LoginPageElements.assertSetupGui();
		//体验一下
		driver.findElement(By.id(LoginPageElements.EXPER_BTN)).click();
		driver.findElement(By.id(LoginPageElements.REMIND_KNOW)).click();
		if(Utils.waitElement(driver, By.id(IndexPageElements.MARKABLE_TAB_LIST), 10)) {
			log.info("\"体验一下\"成功进入首页!");
		}else{
			Assertion.assertEquals(true, false, "\"体验一下\"后未能成功进入首页or页面加载失败请检查！");
		}
	}

	@Test (priority = 2)
	public void completeGuide() {
		log.info("首次进入首页完成引导：");
		for (int i=0; i<3; i++) {
			driver.tap(1, 900, 100, 200);
			Utils.sleep(2);
		}
		IndexPageElements.gotoPer();
	}
	
}
