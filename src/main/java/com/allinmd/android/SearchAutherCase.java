package com.allinmd.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import java.util.List;

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
 * 搜索医师关键字，并验证结果
 * @author sun
 *
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class SearchAutherCase {
	private static AndroidDriver<AndroidElement> driver;
	private String authName;
	private static final Logger LOG = Logger.getLogger(SearchAutherCase.class);
	
	@BeforeClass
	public void setUp() {
		LOG.info("-------------------------  case24-搜索-输入医师名-结果验证  -------------------------");
		driver = AndroidServer.androidDriverRun();
		new SearchPageElements(driver);
		new PerCenterPageElements(driver);
		new LoginPageElements(driver);
	}
	
	@AfterClass  
	public void tearDown() {	
		driver.quit();
		Utils.sleep(2);
		Utils.setInputMethod();
		LOG.info("-------------------------  已完成: case24-搜索-输入医师名-结果验证  -------------------------\n\n\n" );
	} 
	
	/**
	 * 手机登录唯医
	 */
	@Test (priority = 1)
	public void loginPhone() {
		LOG.info("手机登录唯医");
		LoginPageElements.allinLogin(Dom4jXml.getUserValue("loginPhoneUser"), Dom4jXml.getUserValue("globalPassword"));
	}
	
	/**
	 * 搜索关键词
	 * @param authName
	 */
	@Test (priority = 2,dependsOnMethods = "loginPhone")
	public void searchF() {	
		this.authName = Dom4jXml.getValue("searchAuth");
		LOG.info("搜医师名字： " + authName);
		SearchPageElements.searchMethod(authName);	
	}
	
	/**
	 * 验证搜索结果列表病例tag页
	 */
	@Test (priority = 3,dependsOnMethods = "searchF")
	public void assertCase() {			
		LOG.info("搜索结果：病例tag页验证搜索结果");
		SearchPageElements.getResourceName(1);

	}
	
	/**
	 * 验证搜索结果列表视频tag页
	 */
	@Test (priority = 4,dependsOnMethods = "assertCase")
	public void assertVideo() {	
		LOG.info("搜索结果：视频tag页验证搜索结果");	
		List<String> videoInfo = SearchPageElements.getResourceName(2);
		for (int i = 0; i < videoInfo.size() - 1; i ++) {
			LOG.info("视频搜索结果:"+videoInfo.get(i));
//			Assertion.assertEquals(videoInfo.get(i).contains(authName), true, "搜索结果显示错误，请检查！");
		}
	
	}
	
	/**
	 * 验证搜索结果列表医师tag页
	 */
	@Test (priority = 5,dependsOnMethods = "assertVideo")
	public void assertDoctor() {
		LOG.info("搜索结果：医师tag页验证搜索结果");

		List<String> doctorName = SearchPageElements.getDoctorName();
		for (int i = 0; i < doctorName.size() - 1; i ++) {		
			LOG.info("医师搜索结果:"+doctorName.get(i));
		//	Assertion.assertEquals(doctorName.get(i).contains(authName), true, "搜索结果显示错误，请检查！");
		}		
	}
	
	/**
	 * 验证搜索结果列表文库tag页
	 */
	@Test (priority = 6,dependsOnMethods = "assertDoctor")
	public void assertDoc() {	
		LOG.info("搜索结果：文库tag页验证搜索结果");
		SearchPageElements.getResourceName(5);

	}
	
	/**
	 * 验证搜索结果列表话题tag页
	 */
	@Test (priority = 7,dependsOnMethods = "assertDoc")
	public void assertTopic() {	
		LOG.info("搜索结果：话题tag页验证搜索结果");
		SearchPageElements.getResourceName(6);
	}

	/**
	 * 登出
	 */
	@Test (priority = 8,dependsOnMethods = "assertTopic",alwaysRun = true)
	public void logout() {	
		driver.findElement(By.id(SearchPageElements.cancle_btn)).click();
	
		PerCenterPageElements.logOut();
	}
}
