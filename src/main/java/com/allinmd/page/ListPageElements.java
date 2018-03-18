package com.allinmd.page;


import java.util.List;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.allinmd.util.Utils;

public class ListPageElements {
	
	// 病例列表无图片资源Title
	public static final String CASE_LIST_RESOURCE_TITLE = "com.allin.social:id/tv_case_no_p_v_title";
	
	// 病例列表有图片资源title
	public static final String case_list_two_title = "com.allin.social:id/tv_case_two_p_or_p_v_title";
	 
	// 视频列表资源Title
	public static final String VIDEO_LIST_RESOURCE_TITLE = "com.allin.social:id/tv_video_small_title";
	
	// 文库列表资源title
	public static final String doc_list_resource_title = "com.allin.social:id/tv_library_no_cover_no_pdf_title";

	// 文库列表有图资源title
	public static final String doc_list_title = "com.allin.social:id/tv_library_has_cover_nopdf_title";
	
	// 话题列表有图资源title
	public static final String topic_list_one_p_title = "com.allin.social:id/tv_topic_one_p_v_title";
	
	// 话题列表无图资源title		com.allin.social:id/tv_topic_no_p_v_title
	public static final String topic_list_no_p_title = "com.allin.social:id/tv_topic_no_p_v_title";
	
	// 资源终端页关注提醒提示！
	public static final String ect = ".//FrameLayout[2]/FrameLayout/RelativeLayout/ImageView";
	
	
	
	private static AndroidDriver<AndroidElement> driver;
	
	private static final Logger log = Logger.getLogger(ListPageElements.class);
	
	public ListPageElements(AndroidDriver<AndroidElement> driver) {
		ListPageElements.driver = driver;

	}
	
	/**
	 * 进入tab列表第一资源终端页
	 * @param tabName tab列表名称
	 * @param by1 列表资源Title
	 * @param by2 列表多图片资源Title
	 */
	public static String gotoTabListFinalPage(String tabName, By by1, By by2) {
		String case_title = "";
		if(Utils.isElementExist(driver, by1)) {
			List<AndroidElement> case_list_resource_title = driver.findElements(by1);
			case_title = case_list_resource_title.get(0).getText();
			log.info("进入"  +tabName + "\"" + case_title + "\"终端页！");
			case_list_resource_title.get(0).click();
			Utils.sleep(3);
			driver.tap(1, 1050, 1450, 300);
		}else {
			List<AndroidElement> case_list_resource_title = driver.findElements(by2);
			case_title = case_list_resource_title.get(0).getText();
			log.info("进入"  +tabName + "\"" + case_title + "\"终端页！");
			case_list_resource_title.get(0).click();
			Utils.sleep(3);
			driver.tap(1, 1050, 1450, 300);
		}
		return case_title;	
	}
	
}
