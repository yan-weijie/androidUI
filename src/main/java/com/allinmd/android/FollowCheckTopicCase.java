package com.allinmd.android;

import java.util.List;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.allinmd.driver.AndroidServer;
import com.allinmd.file.Dom4jXml;
import com.allinmd.page.IndexPageElements;
import com.allinmd.page.ListPageElements;
import com.allinmd.page.LoginPageElements;
import com.allinmd.page.PerCenterPageElements;
import com.allinmd.page.TerminalPageElements;
import com.allinmd.util.Utils;

/**
 * 病例-关注、个人中心-关注的内容查看关注、取消关注
 * @author ywj
 *
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class FollowCheckTopicCase {
	private static AndroidDriver<AndroidElement> driver;
	private static final Logger LOG = Logger.getLogger(CollectCheckCaseCase.class);
	private static String topicName;
	
	@BeforeClass
	public void setUp() {
		LOG.info("-------------------------  case26-病例-关注/个人中心查看关注/取消关注 -------------------------");
		driver = AndroidServer.androidDriverRun();
    	new LoginPageElements(driver);
    	new IndexPageElements(driver);
    	new ListPageElements(driver);
    	new PerCenterPageElements(driver);
    	new TerminalPageElements(driver);
	}
	
	@AfterClass  
	public void tearDown() {	
		driver.quit();
		Utils.sleep(2);
		Utils.setInputMethod();
		LOG.info("-------------------------  已完成: case26-病例-关注/个人中心查看关注/取消关注 -------------------------\n\n\n" );
	}
	
	@Test (priority = 1)
	public void allinLogin() {
		LoginPageElements.allinLogin(Dom4jXml.getUserValue("loginPhoneUser"), Dom4jXml.getUserValue("globalPassword"));
	}
	
	/**
	 * 进入终端页
	 * 终端页关注
	 */
	@Test (priority = 2, dependsOnMethods= "allinLogin")
	public void followCase() {	
//		ConnDataBase connSql = new ConnDataBase();
//		connSql.setUpWrite();
//		CleanData cl = new CleanData();
//		cl.deleteCollection(Dom4jXml.getUserValue("loginPhoneUser"));
		IndexPageElements.gotoTopicList();
		ListPageElements.gotoTabListFinalPage("话题", By.id(ListPageElements.topic_list_one_p_title), By.id(ListPageElements.topic_list_no_p_title));
		
		LOG.info("进行终端页关注操作");
		TerminalPageElements.follow();
		
		WebElement case_name = driver.findElement(By.id(TerminalPageElements.case_name));
		topicName = case_name.getText();
		LOG.info("收藏的病例名: " + topicName);
		
		WebElement back_per = driver.findElement(By.id(TerminalPageElements.back_btn));
		back_per.click();
		Utils.sleep(1);
	
	}
	
	/**
	 * 个人中心—关注的内容验证关注的话题
	 */
	@Test (priority = 3, dependsOnMethods= "followCase")
	public void checkfollowCase() {
		LOG.info("个人中心，验证关注的话题");
		IndexPageElements.gotoPer();
		
		PerCenterPageElements.checkCollection("关注", topicName,"话题");
	}
	
	/**
	 * 取消关注
	 */
	@Test (priority = 4, dependsOnMethods= "checkfollowCase")
	public void deletefollowCase() {
		LOG.info("进行取消关注操作");
		TerminalPageElements.deleteFollow();
		Utils.sleep(1);
		driver.findElement(By.id(TerminalPageElements.back_btn)).click();
		Utils.sleep(1);
		//下拉屏幕刷新
		Dimension size = driver.manage().window().getSize();
		driver.swipe(size.getWidth() / 2, size.getHeight() / 3*1, size.getWidth() / 2, size.getHeight() / 3*2, 0);
		Utils.sleep(2);
		if(Utils.isElementExist(driver, By.id(PerCenterPageElements.FTV_TITLE_LIST))) {
			List<AndroidElement> name_list = driver.findElements(By.id(PerCenterPageElements.FTV_TITLE_LIST));
			String name = name_list.get(0).getText();
			if(topicName.substring(0, 3).equals(name.substring(0, 3))) {
				LOG.error("找到关注的资源：\""+topicName+"\"，取消关注失败！");
				
					name_list.get(0).click();
					Utils.sleep(2);
					
					LOG.info("再次进行取消关注操作");
					TerminalPageElements.deleteFollow();
					Utils.sleep(1);
					driver.findElement(By.id(TerminalPageElements.back_btn)).click();
					Utils.sleep(1);

			}
		}
	}
	
	/**
	 * 登出
	 */
	@Test (priority = 5, dependsOnMethods= "deletefollowCase", alwaysRun = true)
	public void logout() {	
		if(Utils.isElementExist(driver, By.id(TerminalPageElements.back_btn))) {
			driver.findElement(By.id(TerminalPageElements.back_btn)).click();
		}else {
			driver.navigate().back();
		}
		PerCenterPageElements.logOut();
	}
}
