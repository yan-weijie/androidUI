package com.allinmd.page;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;

import com.allinmd.util.Utils;
import com.runtime.listener.Assertion;

@Listeners({com.runtime.listener.AssertionListener.class})
public class PublishTopicElements {
	public static Logger log = Logger.getLogger(PublishTopicElements.class);
	
	// 发布话题按钮
	public static String pub_topic_btn = "com.allin.social:id/iv_pop_publish_conference";

	// 话题标题
	public static String topic_title_input = "com.allin.social:id/ed_topic_title";
	
	// 正文
	public static String topic_talk_input = "com.allin.social:id/ed_topic_text";
	
	// 添加影像按钮
	public static String add_pic_btn = "com.allin.social:id/ll_social_photo"; 
											 
	// 发布按钮
	public static String pub_topic = "com.allin.social:id/tv_publish";
	
	// 终端页话题标题
	public static String terminal_topic_title = "com.allin.social:id/tv_title";	
	
	private static AndroidDriver<AndroidElement> driver;
    
    public PublishTopicElements(AndroidDriver<AndroidElement> driver){  
    	PublishTopicElements.driver = driver; 
   
    }
    
    /**
     * 发布话题，并根据终端页信息验证是否发布成功。
     * @param topic_title
     * @param topic_talk
     * @return
     */
	public static void pubTopic(String topic_title, String topic_talk) {	
		if(Utils.isElementExist(driver, By.id(IndexPageElements.INDEX_PUBLISH_BTN))) {
    		driver.findElement(By.id(IndexPageElements.INDEX_PUBLISH_BTN)).click();	
    	}else {
    		Assertion.assertEquals(true, false, "未找到资源\"发布\"按钮页面元素不符，请检查");
    	}
		log.info("开始编辑发布话题！" + topic_title);
		WebElement ePubTopicBtn = driver.findElement(By.id(pub_topic_btn));
		ePubTopicBtn.click();
		Utils.sleep(1);
		
		// 标题
		WebElement eTopicTtile = driver.findElement(By.id(topic_title_input));
		eTopicTtile.clear();
		eTopicTtile.sendKeys(topic_title);
		Utils.sleep(1);
		
		// 正文
		WebElement eTopicTalk = driver.findElement(By.id(topic_talk_input));
		eTopicTalk.clear();
		eTopicTalk.sendKeys(topic_talk);
		Utils.sleep(1);
	
		// 发布
		WebElement ePubCase = driver.findElement(By.id(pub_topic));
		ePubCase.click();
		Utils.sleep(8);
		
		/*
		boolean flag = Utils.swipe(driver, By.id(pub_topic), 3);
		Utils.sleep(2);
		boolean ePubCaseAttribute;		
		if (flag) {
			ePubCaseAttribute = Boolean.parseBoolean(driver.findElement(By.id(pub_topic)).getAttribute("clickable"));			
			if (ePubCaseAttribute) {
				WebElement ePubCase = driver.findElement(By.id(pub_topic));
				ePubCase.click();
				Utils.sleep(3);
				TerminalPageElements.verMessage();
			} else {
				
				Assertion.assertEquals(ePubCaseAttribute, true, "未触发发布按钮，是置灰状态!");
			}
		} else {
			Assertion.assertEquals(flag, true, "发布按钮未找到，请检查后再试！");
		}*/
		
		boolean findTopicTitle = Utils.waitElement(driver, By.id(terminal_topic_title), 10);
		if (findTopicTitle) {
			String terminalTopicTitle = driver.findElement(By.id(terminal_topic_title)).getText();
			if(terminalTopicTitle.substring(0, 6).equals(topic_title.substring(0, 6))) {
				log.info("话题发布成功");
			}else {
				log.error("发布的话题 " + topic_title + " 与终端页标题 " + terminalTopicTitle + " 不一致，请检查！");
			}
		
		} else {
			Assertion.assertEquals(true, false, "发布话题后未能跳转到话题终端页，请检查话题是否发布成功！");
		}
		
	}
	
}
