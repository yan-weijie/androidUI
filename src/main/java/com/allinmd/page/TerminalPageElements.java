package com.allinmd.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.allinmd.util.Utils;
import com.runtime.listener.Assertion;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class TerminalPageElements {
	
	// 后退
	public static final String back_btn = "com.allin.social:id/iv_back";
	
	// 病例终端页标题
	public static final String case_name = "com.allin.social:id/tv_case_title";
	
	// 视频终端页 视频title
	public static final String video_name = "com.allin.social:id/tv_video_name";
	
	// 话题终端页标题
	public static final String topic_name = "com.allin.social:id/tv_title";
	
	// 文库终端页标题
	public static final String doc_name = "com.allin.social:id/tv_pdf_name";
	
	// 病例终端页编辑按钮
	public static final String edit_btn = "com.allin.social:id/tv_attentionn_btn";
	
	// 病例终端页患者性别信息
	public static final String case_sex = "com.allin.social:id/tv_sex";
	
	// 加入讨论
	public static final String pub_comment_btn = "com.allin.social:id/rl_add_discuss";
	
	// 评论按钮
	public static final String reply_btn = "com.allin.social:id/iv_reply";
	
	// 评论数量
	public static final String discuss_num = "com.allin.social:id/tv_discuss_num";
	
	// 收藏按钮
	public static final String collection_btn = "com.allin.social:id/iv_collection";
	
	// 收藏数量
	public static final String colection_num = "com.allin.social:id/tv_colectionnum";
	
	// 点赞按钮
	public static final String praise_btn = "com.allin.social:id/iv_parise";
	
	// 赞数量
	public static final String praise_num = "com.allin.social:id/tv_praisenum";
	
	// 评论内容list
	public static final String comment_content_list = "com.allin.social:id/tv_title";
	
	// 视频终端页收藏按钮
	public static final String video_collection_btn = "com.allin.social:id/tv_collection";

	// 视频终端页视频title
	public static final String video_title = "com.allin.social:id/tv_video_name";
	
	// 病例终端页关注按钮
	public static final String follow_btn = "com.allin.social:id/tv_attentionn_btn";
	
	// 病例终端页关注数量
	public static final String follow_num = "com.allin.social:id/tv_attention_num";
	
	// 视频终端页后退按钮
	public static final String video_back = "com.allin.social:id/rl_back";

	// 我有相似病例
	public static final String similar_case = "com.allin.social:id/tv_publishcase";
	
	public static AndroidDriver<AndroidElement> driver;
	private static final Logger log = Logger.getLogger(TerminalPageElements.class);
	
	public TerminalPageElements(AndroidDriver<AndroidElement> driver) {
		TerminalPageElements.driver = driver;
	}
	
	/**
	 * 资源收藏
	 */
	public static void 	colection() {
		int j = 0;
		int f = 0;
		if(Utils.isElementExist(driver, By.id(colection_num))) {
			j = Integer.parseInt(driver.findElement(By.id(colection_num)).getText());
		}
		
		driver.findElement(By.id(collection_btn)).click();
		
		if(Utils.isElementExist(driver, By.id(colection_num))) {
			f = Integer.parseInt(driver.findElement(By.id(colection_num)).getText());	
		}
		
		if(j < f){
			log.info("点击收藏后收藏数量："+j+" 增加为："+f);
		}else if(j == f) {
			log.error("点击收藏后收藏数量未发生改变，请检查!");
		}else if(j > f) {
			log.info("点击收藏后收藏数量："+j+" 增加为："+f+"该资源已关注，取消收藏成功！");
			log.info("再次点击收藏，收藏该资源！");
			driver.findElement(By.id(collection_btn)).click();
		}
	}
	
	/**
	 * 资源关注
	 */
	public static void follow() {
		int j = 0;
		int f = 0;
		if(Utils.isElementExist(driver, By.id(follow_num))) {
			String sss = driver.findElement(By.id(follow_num)).getText();
			if(sss.equals("  关注")) {
				
			}else {
				j = Integer.parseInt(sss.replace(" 关注", ""));
			}
		}else{
			Assertion.assertEquals(true, false, "未找到资源关注数量元素，请检查页面是否正确！");
		}
		if(Utils.isElementExist(driver, By.id(follow_btn))) {
			String follow_text = driver.findElement(By.id(follow_btn)).getText();
			if(follow_text.equals("+ 关注")) {
				log.info("点击关注");
				driver.findElement(By.id(follow_btn)).click();
				
				if(Utils.isElementExist(driver, By.id(follow_num))) {
					String fff = driver.findElement(By.id(follow_num)).getText();
					if(fff.equals("  关注")) {
						
					}else {
						f = Integer.parseInt(fff.replace(" 关注", ""));
					}
				}
				
				if(j < f){
					log.info("点击关注后关注数量："+j+" 增加为："+f);
				}else if(j == f) {
					log.error("点击关注后关注数量未发生改变，请检查!");
				}else if(j > f) {
					log.error("点击关注后关注数量："+j+" 增加为："+f+"该资源关注变更数量有误请核查！");
				}
			}else {
				String follow_text_t = driver.findElement(By.id(follow_btn)).getText();
				log.warn("该资源：" + follow_text_t);
			}
		}else {
			log.error("未找到关注按钮，请检查页面是否正确！");
			Assertion.assertEquals(true, false, "未找到关注按钮，请检查页面是否正确！");
		}			
	}
	
	/**
	 * 取消资源关注
	 */
	public static void deleteFollow() {
		int j = 0;
		int f = 0;
		if(Utils.isElementExist(driver, By.id(follow_num))) {
			String sss = driver.findElement(By.id(follow_num)).getText();
			if(sss.equals("  关注")) {
				
			}else {
				j = Integer.parseInt(sss.replace(" 关注", ""));
			}
		}else{
			Assertion.assertEquals(true, false, "未找到资源关注数量元素，请检查页面是否正确！");
		}
		
		if(Utils.isElementExist(driver, By.id(follow_btn))) {
			String follow_text = driver.findElement(By.id(follow_btn)).getText();
			if(follow_text.equals("已关注")) {
				log.info("点击取消关注");
				driver.findElement(By.id(follow_btn)).click();
				Utils.sleep(1);
				//取消关注确认
				driver.findElement(By.name("取消关注")).click();
				Utils.sleep(1);
				if(Utils.isElementExist(driver, By.id(follow_num))) {
					String fff = driver.findElement(By.id(follow_num)).getText();
					if(fff.equals("  关注")) {
						
					}else {
						f = Integer.parseInt(fff.replace(" 关注", ""));
					}	
				}
				
				if(j > f){
					log.info("点击取消关注后关注数量："+j+" 递减为："+f+"取消关注成功！");
				}else if(j == f) {
					log.error("点击取消关注后关注数量未发生改变，请检查!");
				}else if(j < f) {
					log.error("点击取消关注后关注数量："+j+" 增加为："+f+"该资源关注变更数量有误请核查！");
				}
			}else {
				log.warn("该资源已是未关注状态");
			}
		}else {
			log.error("未找到关注按钮，请检查页面是否正确！");
			Assertion.assertEquals(true, false, "未找到关注按钮，请检查页面是否正确！");
		}	
	}
	
	/**
	 * 视频收藏
	 */
	public static void videoColection() {
		int j = 0;
		int f = 0;
		if(Utils.isElementExist(driver, By.id(video_collection_btn))) {
			String coll_btn_txt = driver.findElement(By.id(video_collection_btn)).getText();
			if(coll_btn_txt.equals("收藏")){

			}else if(coll_btn_txt.equals("已收藏")) {
				log.error("该资源已收藏！");
				driver.findElement(By.id(video_collection_btn)).click();	//点击取消收藏
			}else {
				j = Integer.parseInt(driver.findElement(By.id(video_collection_btn)).getText());
			}
		}else {
			log.error("视频终端页收藏按钮未找到or页面不符，请检查！");
		}
		log.info("点击收藏！");
		driver.findElement(By.id(video_collection_btn)).click();
		
		if(!driver.findElement(By.id(video_collection_btn)).getText().equals("收藏")){
			f = Integer.parseInt(driver.findElement(By.id(video_collection_btn)).getText());
		}
		
		if(j < f){
			log.info("点击收藏后收藏数量："+j+" 增加为："+f);
		}else if(j == f) {
			log.error("点击收藏后收藏数量未发生改变，请检查!");
		}else if(j > f) {
			log.info("点击收藏后收藏数量："+j+" 增加为："+f+"该资源已关注，取消收藏成功！");
			log.info("再次点击收藏，收藏该资源！");
			driver.findElement(By.id(video_collection_btn)).click();
		}
	}

}
