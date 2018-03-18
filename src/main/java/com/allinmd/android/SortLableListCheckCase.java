package com.allinmd.android;

import java.util.List;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.allinmd.file.Dom4jXml;
import com.allinmd.page.AndroidServer;
import com.allinmd.page.FindPageElements;
import com.allinmd.page.IndexPageElements;
import com.allinmd.page.LoginPageElements;
import com.allinmd.page.PerCenterPageElements;
import com.allinmd.util.Utils;

/**
 * 按标签筛选列表排序检查
 * @author yan
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class SortLableListCheckCase {
	private static AndroidDriver<AndroidElement> driver;
	private static final Logger log = Logger.getLogger(SortMajorCheckCase.class);
	
	@BeforeClass
	public void setUp() {
		log.info("-------------------------  case31-按标签筛选-列表排序检查  -------------------------");
		driver = AndroidServer.androidDriverRun();
    	new LoginPageElements(driver);
    	new PerCenterPageElements(driver);
    	new FindPageElements(driver);
    	new IndexPageElements(driver);
	}
	
	@AfterClass  
	public void tearDown() {	
		driver.quit();
		Utils.sleep(2);
		Utils.setInputMethod();
		log.info("-------------------------  已完成: case31-按标签筛选-列表排序检查  -------------------------\n\n\n" );
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
		IndexPageElements.gotoFindPage();

		driver.findElement(By.id(FindPageElements.lable_sort)).click();
		//按标签第一个标签
		Utils.sleep(2);
		List<AndroidElement> lable_title = driver.findElements(By.id(FindPageElements.lable_title_list));
		lable_title.get(0).click();
		//选择便签数据展示类型（病例）
		Utils.sleep(2);
		Utils.waitElement(driver, By.id(FindPageElements.item_user_img), 5);
		driver.findElement(By.id(FindPageElements.data_sort)).click();
		Utils.sleep(1);
		driver.findElement(By.name(FindPageElements.data_sort_case)).click();
		
		log.info("开始验证\"最多评论\"排序！");
		if(FindPageElements.checkLableOrder(FindPageElements.lable_max_review, FindPageElements.lable_review_count)) {
			log.info("\"最多评论\"排序结果：true");
		}else{
			log.error("\"最多评论\"排序结果：false");
		}
		
	}
	
	/**
	 * 验证最多浏览
	 */
	@Test (priority = 3, dependsOnMethods = "checka")
	public void checkb() {
		log.info("开始验证\"最多浏览\"排序！");
		if(FindPageElements.checkLableOrder(FindPageElements.lable_max_look, FindPageElements.lable_look_count)) {
			log.info("\"最多浏览\"排序结果：true");
		}else{
			log.error("\"最多浏览\"排序结果：false");
		}
		driver.navigate().back();
		Utils.sleep(1);
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
