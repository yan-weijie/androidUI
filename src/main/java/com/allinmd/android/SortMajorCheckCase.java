package com.allinmd.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.allinmd.page.AndroidServer;
import com.allinmd.file.Dom4jXml;
import com.allinmd.page.FindPageElements;
import com.allinmd.page.LoginPageElements;
import com.allinmd.page.PerCenterPageElements;
import com.allinmd.util.Utils;

/**
 * 按专业筛选列表排序检查
 * @author yan
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class SortMajorCheckCase {
	private static AndroidDriver<AndroidElement> driver;
	private static final Logger log = Logger.getLogger(SortMajorCheckCase.class);
	
	@BeforeClass
	public void setUp() {
		log.info("-------------------------  case32-按专业筛选-列表排序检查  -------------------------");
		driver = AndroidServer.androidDriverRun();
    	new LoginPageElements(driver);
    	new PerCenterPageElements(driver);
    	new FindPageElements(driver);
	}
	
	@AfterClass  
	public void tearDown() {	
		driver.quit();
		Utils.sleep(2);
		Utils.setInputMethod();
		log.info("-------------------------  已完成: case32-按专业筛选-列表排序检查  -------------------------\n\n\n" );
	}
	
	@Test (priority = 1)
	public void allinLogin() {
		LoginPageElements.allinLogin(Dom4jXml.getUserValue("loginPhoneUser"), Dom4jXml.getUserValue("globalPassword"));
	}
	
	/**
	 * 验证最多评论
	 */
	@Test (priority = 2, dependsOnMethods = "allinLogin")
	public void checka() {
		FindPageElements.sort(By.id(FindPageElements.major_sort));
		
		log.info("开始验证\"最多评论\"排序！");
		if(FindPageElements.checkOrder(1, FindPageElements.review_count)) {
			log.info("\"最多评论\"排序结果：true");
		}else{
			log.error("\"最多评论\"排序结果：false");
		}
		
		driver.findElement(By.id(FindPageElements.sort_back)).click();
	}
	

	/**
	 * 验证最多评论
	 */
	@Test (priority = 3, dependsOnMethods = "checka")
	public void checkb() {
		FindPageElements.sort(By.id(FindPageElements.major_sort));
		
		log.info("开始验证\"最多浏览\"排序！");
		if(FindPageElements.checkOrder(2, FindPageElements.look_count)) {
			log.info("\"最多浏览\"排序结果：true");
		}else{
			log.error("\"最多浏览\"排序结果：false");
		}
		
		driver.findElement(By.id(FindPageElements.sort_back)).click();
	}
	
	/**
	 * 登出
	 */
	@Test (priority = 4,dependsOnMethods = "checkb",alwaysRun = true)
	public void logout() {	
		PerCenterPageElements.logOut();
	}
}
