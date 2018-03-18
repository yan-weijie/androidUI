package com.allinmd.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.allinmd.driver.AndroidServer;
import com.allinmd.file.Dom4jXml;
import com.allinmd.page.LoginPageElements;
import com.allinmd.page.PerCenterPageElements;
import com.allinmd.page.SearchPageElements;
import com.allinmd.util.Utils;

/**
 * 搜索医学关键字，并验证结果
 * @author sun
 *
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class SearchKeywordCase {
	private static AndroidDriver<AndroidElement> driver;
	private static final Logger LOG = Logger.getLogger(SearchKeywordCase.class);
//	private String keyword;
	
	@BeforeClass
	public void setUp() {
		LOG.info("-------------------------  case25-搜索-输入医学关键词-结果验证  -------------------------");
		driver = AndroidServer.androidDriverRun();
		new SearchPageElements(driver);
		new LoginPageElements(driver);
		new PerCenterPageElements(driver);
		

	}
	
	@AfterClass  
	public void tearDown() {	
		driver.quit();
		Utils.sleep(2);
		Utils.setInputMethod();
		LOG.info("-------------------------  已完成: case25-搜索-输入医学关键词-结果验证  -------------------------\n\n\n" );
	} 
	
	@Test (priority = 1)
	public void allinLogin() {
		LoginPageElements.allinLogin(Dom4jXml.getUserValue("loginPhoneUser"), Dom4jXml.getUserValue("globalPassword"));
	}
	
	/**
	 * 搜索关键词
	 * @param authName
	 */
	@Test (priority = 2,dependsOnMethods = "allinLogin")
	public void searchF() {	
//		this.keyword = keyword;
		LOG.info("搜索关键词: " + Dom4jXml.getValue("searchKeyword"));
		SearchPageElements.searchMethod(Dom4jXml.getValue("searchKeyword"));	
	}
	
	/**
	 * 验证搜索结果列表病例tag页
	 */
	@Test (priority = 3,dependsOnMethods = "searchF")
	public void assertResult() {	
		LOG.info("遍历搜索结果");
		if(SearchPageElements.ergodicResult()) {
			LOG.info("结果:true");
		}
	}
	
	/**
	 * 登出
	 */
	@Test (priority = 4,dependsOnMethods = "assertResult",alwaysRun = true)
	public void logout() {	
		driver.findElement(By.id(SearchPageElements.cancle_btn)).click();
		
		PerCenterPageElements.logOut();
	}
}
