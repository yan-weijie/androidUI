package com.allinmd.page;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class EditCommentElements {

	// 评论内容输入框
	public static final String comment_input = "com.allin.social:id/et_publish_input";
	
	// 提醒@
	public static final String at_btn = "com.allin.social:id/tv_remind";
	
	public static AndroidDriver<AndroidElement> driver;
	
	public EditCommentElements(AndroidDriver<AndroidElement> driver) {
		EditCommentElements.driver = driver;
	}
	
	/**
	 * 
	 */
}
