package com.allinmd.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.allinmd.page.IndexPageElements;
import com.allinmd.page.LoginPageElements;
import com.allinmd.driver.AndroidServer;
import com.allinmd.page.PerCenterPageElements;
import com.allinmd.page.PublishCaseElements;
import com.allinmd.file.Dom4jXml;
import com.allinmd.httpclient.CreateData;
import com.allinmd.httpclient.CleanData;
import com.allinmd.util.ConnDataBase;
import com.allinmd.util.Utils;
import com.runtime.listener.Assertion;

/**
 * 病例-评论\删除
 * @author sun
 *
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class PubCheckDeleteCommentCase {
	private static AndroidDriver<AndroidElement> driver;
	private ConnDataBase connSql = new ConnDataBase();
	private static final Logger LOG = Logger.getLogger(PubCheckDeleteCommentCase.class);
	
	private  String case_title;
	private  String comment;
	private  String username;
	private  String password;
	
	@BeforeClass
	public void setUp() {
		LOG.info("-------------------------  case19-评论-发布/个人中心查看发布/删除  -------------------------");
		driver = AndroidServer.androidDriverRun();
		new LoginPageElements(driver);
		new PerCenterPageElements(driver);
		new PublishCaseElements(driver);
		new IndexPageElements(driver);
		case_title = Dom4jXml.getPublishValue("caseTitle");
		username = Dom4jXml.getUserValue("loginPhoneUser");
		password = Dom4jXml.getUserValue("globalPassword");
		comment = Dom4jXml.getPublishValue("reviewText");
	}
	
	@AfterClass  
	public void tearDown() {	
		driver.quit();
		Utils.sleep(2);
		Utils.setInputMethod();
		connSql.tearDown();
		LOG.info("-------------------------  已完成: case19-评论-发布/个人中心查看发布/删除  -------------------------\n\n\n" );
	} 
	

	@Test (priority = 1)
	public void allinLogin() {
	    
		LoginPageElements.allinLogin(username, password);

	}
	
	/**
	 * 从个人中心进入终端页 
	 */
	@Test (priority = 2,dependsOnMethods = "allinLogin")
	public void goTerminalPage() {	
		//因为依赖发布过病例，调用接口发布一个病例
		CreateData create = new CreateData();
		create.createCase(username, password);
		
		IndexPageElements.gotoPer();
		
		if(PerCenterPageElements.checkPublish(case_title)) {
			PublishCaseElements.editReply(comment);
			
			WebElement back = driver.findElement(By.id("com.allin.social:id/iv_back"));
			back.click();
		}else {
			Assertion.assertEquals(true, false, "个人中心未找到发布的病例！请检查！");
		}
		
	}
		
	/**
	 * 到个人中心-我的评论检查
	 */
	@Test (priority = 4,dependsOnMethods = "goTerminalPage")
	public void checkComment() {
		if (PerCenterPageElements.checkComment(comment).substring(0, 8).equals(comment.substring(0, 8))) {
			WebElement back = driver.findElement(By.id("com.allin.social:id/iv_back"));
			back.click();
		}else {
			Assertion.assertEquals(true, false, "个人中心我的评论内容与发布评论不一致！");
		}
	}
	
	/**
	 * 登出
	 */
	@Test (priority = 5,dependsOnMethods= "checkComment",alwaysRun = true)
	public void logout() {
		PerCenterPageElements.logOut();	
	}
	
	/**
	 * 无效病例
	 */
	@Test (priority = 6,dependsOnMethods = "logout",alwaysRun = true)
	public void invalidCase() {	
		CleanData cleanData = new CleanData();
		//后台无效病例和评论	
		cleanData.invalidCase(username);
		cleanData.invalidReview(username);
	}
	
}
