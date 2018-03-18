package com.allinmd.page;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;

import com.allinmd.util.Utils;
import com.runtime.listener.Assertion;

@Listeners({com.runtime.listener.AssertionListener.class})
public class PublishCaseElements {
	private static final Logger log = Logger.getLogger(PublishCaseElements.class);
	
	// 发布病例按钮
	public static final String pub_case_btn = "com.allin.social:id/iv_pop_publish_case";
	
	// 病例标题
	public static final String case_title_input = "com.allin.social:id/et_cases_title";
	
	// 选择性别：女
	public static final String select_women = "com.allin.social:id/rb_select_women";
	
	// 选择性别：男
	public static final String select_men = "com.allin.social:id/rb_select_men";
		
	// 病人年龄
	public static final String age_input = "com.allin.social:id/et_patient_age";
	
	// 年龄选择保存按钮
	public static final String age_save_btn= "com.allin.social:id/tv_save";
	
	// 输入主诉
	public static final String main_suit_input = "com.allin.social:id/et_patient_main_suit";
	
	// 输入讨论
	public static final String case_talk_input = "com.allin.social:id/et_discussion";
	
	// 影像资料
	public static final String video_info_btn = "com.allin.social:id/tv_videoinfo";
	
	// 添加影像list
	public static final String add_photo_list = "com.allin.social:id/ll_photos_camera";
	
	// 取消图片按钮
	public static final String cancel_photo_btn = "com.allin.social:id/iv_show_photo_cancel";
	
	// 添加图片描述list
	public static final String add_photo_describe = "com.allin.social:id/et_photo_describe";
	
	// 发布按钮
	public static final String pub_btn = "com.allin.social:id/tv_publish";
	
	// 取消发布按钮
	public static final String cancel_pub_btn = "com.allin.social:id/tv_cancel";
	
	// 存为草稿
	public static final String save_draft = "com.allin.social:id/btn_draft_save";
	
	/**
	 */
	// 评论内容输入框
	public static final String comment_input = "com.allin.social:id/et_publish_input";
	
	// 提醒@
	public static final String at_btn = "com.allin.social:id/tv_remind";
	
	// @提醒匹配出用户列表
	public static final String at_person_name_list = "com.allin.social:id/tv_person_name";

	
	private static AndroidDriver<AndroidElement> driver;
    
    public PublishCaseElements(AndroidDriver<AndroidElement> driver){  
    	PublishCaseElements.driver = driver; 
    	new AuthPageElements(driver);
    }
	
    /**
     * 填写发布病例的各字段数据
     * @param case_title
     * @param age
     * @param main_suit
     * @param case_talk
     */
	public static void pubCase(String case_title, String age, String main_suit, String case_talk) {
		if(!Utils.isElementExist(driver, By.id(case_title_input))) {
			if(Utils.isElementExist(driver, By.id(IndexPageElements.INDEX_PUBLISH_BTN))) {
				driver.findElement(By.id(IndexPageElements.INDEX_PUBLISH_BTN)).click();
			}else {
				Assertion.assertEquals(true, false, "未找到资源\"发布\"按钮页面元素不符，请检查");
			}

			Utils.waitElement(driver, By.id(pub_case_btn), 10);
			log.info("创建病例!");
			driver.findElement(By.id(pub_case_btn)).click();
		}
		//点击空白处关闭引导提示
		for(int i=0; i<=3; i++) {
			Utils.sleep(1);
			driver.tap(1, 800, 150, 200);	
		}
		
		driver.findElement(By.id(case_title_input)).sendKeys(case_title);
		
		driver.findElement(By.id(select_women)).click();
		
		driver.findElement(By.id(age_input)).click();
		driver.findElement(By.id(age_save_btn)).click();
		
		driver.findElement(By.id(main_suit_input)).sendKeys(main_suit);
		
		driver.findElement(By.id(case_talk_input)).sendKeys(case_talk);
		//开始添加图像
		driver.findElement(By.id(video_info_btn)).click();
		caseUploadPhoto("术前", 0, 0);
		caseUploadPhoto("术后", 1, 4);
	}
	
	/**
	 * 病例上传图片
	 * @param describe 照片描述
	 * @param index 照片描述输入框size()
	 * @param photoNumber temp相册选择指定的照片
	 */
	public static void caseUploadPhoto(String describe, int index, int photoNumber) {
		log.info("开始上传\"" + describe + "\" 照片！");
		List<AndroidElement> add_photo = driver.findElements(By.id(add_photo_list));
		add_photo.get(0).click();
		AuthPageElements.SAUPPhoto(photoNumber);
		Utils.waitElement(driver, By.id(cancel_photo_btn), 10);
		if(Boolean.parseBoolean(driver.findElement(By.id(cancel_photo_btn)).getAttribute("clickable"))){
			List<AndroidElement> add_describe = driver.findElements(By.id(add_photo_describe));
			add_describe.get(index).click();
			add_describe.get(index).clear();
			add_describe.get(index).sendKeys(describe);
		}else{
			Assertion.assertEquals(true, false,describe + "图片上传失败请检查！");
		}
	}
	
	/**
	 * 发布病例。
	 */
	public static String submitCase() {
		String case_name = "";
		boolean ePubCaseAttribute;		
		if(Utils.isElementExist(driver, By.id(pub_btn))) {
			ePubCaseAttribute = Boolean.parseBoolean(driver.findElement(By.id(pub_btn)).getAttribute("clickable"));			
			if(ePubCaseAttribute) {
				WebElement ePubCase = driver.findElement(By.id(pub_btn));
				ePubCase.click();
				Utils.sleep(3);

				driver.tap(1, 1050, 1450, 300);

				int i = 0;
				boolean flag = false;
				while(flag == false && i < 3) {
					Dimension windowSize = driver.manage().window().getSize();
					driver.swipe(windowSize.getWidth() / 2, windowSize.getHeight() / 2 - 500, windowSize.getWidth() / 2,
							windowSize.getHeight() / 2 + 500, 0);
					flag = Utils.isElementExist(driver, By.id(TerminalPageElements.case_name));
				}

				if(flag) {
					case_name = driver.findElement(By.id(TerminalPageElements.case_name)).getText();
				}else {
					Assertion.assertEquals(false, true, "病例发布后未能跳转到终端页or未找到病例终端页Title，请检查！");
				}
			}else {
				Assertion.assertEquals(ePubCaseAttribute, true, "未触发发布按钮，是置灰状态!");
			}
		}else {
			Assertion.assertEquals(false, true, "发布按钮未找到，请检查后再试！");
		}
		return case_name;
	}
	
	/**
	 * 编辑病例
	 */
	public static void editCase() {
		WebElement eEditBtn = driver.findElement(By.id(TerminalPageElements.edit_btn));
		eEditBtn.click();
		Utils.sleep(1);
		
		driver.findElement(By.id(select_men)).click();
		Utils.sleep(1);
		submitCase();
		String case_sex = driver.findElement(By.id(TerminalPageElements.case_sex)).getText();
		if(case_sex.equals("男")) {
			log.info("病例编辑成功！");
		}else {
			log.error("编辑后病例终端页患者性别信未改变，请检查是否修改成功！");
		}
	}
	
	/**
	 * 取消编辑保存草稿
	 */
	public static void saveDraft() {
		driver.findElement(By.id(cancel_pub_btn)).click();
		Utils.sleep(1);
		driver.findElement(By.id(save_draft)).click();
		Utils.sleep(2);
		if(!Utils.isElementExist(driver, By.id(IndexPageElements.INDEX_MY_BTN))) {
			Assertion.assertEquals(true, false, "点击保存至草稿后跳转首页失败，请检查!");
		}
	}
	
	/**
	 * 编辑评论
	 */
	public static void editReply(String replyContent) {
		log.info("开始编辑发布评论！");
		if(Utils.waitElement(driver, By.id(TerminalPageElements.pub_comment_btn), 5)) {
			driver.findElement(By.id(TerminalPageElements.pub_comment_btn)).click();
			Utils.sleep(1.5);
			driver.findElement(By.id(comment_input)).sendKeys(replyContent);
//			driver.findElement(By.id(at_btn)).click();
//			driver.findElement(By.id(comment_input)).sendKeys("王");
			
			if(Utils.waitElement(driver, By.id(at_person_name_list), 5)) {
				List<AndroidElement> at_person_list = driver.findElements(By.id(at_person_name_list));
				at_person_list.get(0).click();
				
				boolean ePubAttribute;		
				if(Utils.isElementExist(driver, By.id(pub_btn))) {
					ePubAttribute = Boolean.parseBoolean(driver.findElement(By.id(pub_btn)).getAttribute("clickable"));			
					if(ePubAttribute) {
						WebElement ePub = driver.findElement(By.id(pub_btn));
						ePub.click();
						
					}else {
						Assertion.assertEquals(ePubAttribute, true, "未触发发布按钮，是置灰状态!");
					}
				}else {
					Assertion.assertEquals(false, true, "发布按钮未找到，请检查后再试！");
				}
			}else {
				Assertion.assertEquals(true, false, "未能找到@后匹配出的用户列表！");
			}
			
		}else {
			Assertion.assertEquals(false, true, "未找到病例终端页\"加入讨论\"按钮,请检查！");
		}
		driver.findElement(By.id(TerminalPageElements.reply_btn)).click();
		if(Utils.waitElement(driver, By.id(TerminalPageElements.comment_content_list),10)) {				
			List<AndroidElement> terminal_reply_list = driver.findElements(By.id(TerminalPageElements.comment_content_list));
			String comment_content = terminal_reply_list.get(0).getText();
			if(replyContent.substring(0, 8).equals(comment_content.substring(0, 8))) {
				log.info("回复成功！");
			}else {
				Assertion.assertEquals(true, false, "资源第一条评论内容：\""+comment_content+"\" 与发布的评论：\""+replyContent+"\" 不一致，请检查！");
			}			
		}else {
			Assertion.assertEquals(false, true, "评论发布后未能跳转到终端页，请检查评论发布是否失败！");
		}
				
	}
	
	
}
