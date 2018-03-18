package com.allinmd.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.allinmd.util.Utils;
import com.runtime.listener.Assertion;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import java.util.List;

public class IndexPageElements {
	public static Logger log = Logger.getLogger(IndexPageElements.class);
	
	// 首页
	public static final String INDEX_BTN = "com.allin.social:id/it_tab_home";
	
	// 发现
	public static final String INDEX_FIND_BTN = "com.allin.social:id/it_tab_find";
	
	// 发布
	public static final String INDEX_PUBLISH_BTN = "com.allin.social:id/it_tab_publish";
	
	// 消息
	public static final String INDEX_MESSAGE_BTN = "com.allin.social:id/it_tab_msg";
	
	// 我的
	public static final String INDEX_MY_BTN = "com.allin.social:id/it_tab_myself";
	
	// 热门 xpath
	public static final String INDEX_TAB_HOT = "//*[contains(@text, '热门')]";
	
	// 病例 xpath
	public static final String INDEX_TAB_CASE = "//*[contains(@text, '病例')]";
		
	// 视频 xpath
	public static final String INDEX_TAB_VIDEO = "//*[contains(@text, '视频')]";
		
	// 会议 xpath
	public static final String INDEX_TAB_MEETING = "//*[contains(@text, '会议')]";
	
	// 文库 xpath
	public static final String INDEX_TAB_DOC = "//*[contains(@text, '文库')]";
	
	// 话题 xpath
	public static final String INDEX_TAB_TOPIC= "//*[contains(@text, '话题')]";
	
	// 首页【朋友圈】【推荐医师】...list
	public static final String MARKABLE_TAB_LIST= "com.allin.social:id/tv_markable_tab_text";
	
	// 首页搜索按钮
	public static final String INDEX_SEARCH_BTN = "com.allin.social:id/container_action_bar_right_sub_2";
	
	// 首页下载按钮
	public static final String INDEX_CASE_BTN = "com.allin.social:id/tv_download";
	
	// 无数据网络不佳图片
	public static final String NO_RESULT_PNG = "com.allin.social:id/iv_search_no_result";

	// 无数据网络不佳刷新重试
	public static final String NO_RESULT_RETRY = "com.allin.social:id/iv_search_no_result";
	
	private static AndroidDriver<AndroidElement> driver;
	
    public IndexPageElements(AndroidDriver<AndroidElement> driver){
    	IndexPageElements.driver = driver;
    }
    
    /**
     * 判断我的按钮是否存在
     */
    public static boolean myBtnIsExist() {
    	By index_my_btn = By.id(INDEX_MY_BTN);
    	Utils.waitElement(driver, index_my_btn, 10);
    	return Utils.isElementExist(driver, index_my_btn);
    }
    
    /**
     * 进入我的（个人中心）
     */
    public static void gotoPer() {
    	if(myBtnIsExist()) {
    		driver.findElement(By.id(INDEX_MY_BTN)).click();   		
    		Utils.sleep(1);
			//我的贡献过多时会提示展示类型，这里点击全部关闭提示
			PerCenterPageElements.downMenuIsExist();
			Utils.sleep(1);
    		//账户首次进入个人中心，点击空白处完成引导
    		driver.tap(1, 800, 150, 200);
    		Utils.sleep(1);
    		driver.tap(1, 800, 150, 200);
			List<AndroidElement> per_tab_list = driver.findElements(By.id(PerCenterPageElements.PER_TAB_LIST));
			per_tab_list.get(0).click();
    	}else {
    		Assertion.assertEquals(true, false, "未找到我的按钮,页面未跳转成功请检查！");
    	}  	
    }
    
    /**
     * 进入病例列表页
     */
    public static void gotoCaseList() {
    	if(Utils.isElementExist(driver, By.xpath(INDEX_TAB_CASE))) {
    		log.info("进入病例列表页！");
    		driver.findElement( By.xpath(INDEX_TAB_CASE)).click();
    		if(Utils.waitElement(driver, By.id(ListPageElements.case_list_two_title), 10)) {
    			
    		}else if(Utils.isElementExist(driver, By.id(ListPageElements.CASE_LIST_RESOURCE_TITLE))) {
    			
    		}else {
    			Assertion.assertEquals(true, false, "病例列表页数据加载失败，请检查网络情况！");
    		}
    		
    	}else {
    		Assertion.assertEquals(true, false, "未找首页病例列表tab,页面不符请检查！");
    	}
    }
    
    /**
     * 进入视频列表页
     */
    public static void gotoVideoList() {
    	if(Utils.isElementExist(driver, By.xpath(INDEX_TAB_VIDEO))) {
    		log.info("进入视频列表页！");
    		driver.findElement( By.xpath(INDEX_TAB_VIDEO)).click();
    		
    		if(Utils.waitElement(driver, By.id(ListPageElements.VIDEO_LIST_RESOURCE_TITLE), 10)) {
    			
    		}else {
    			Assertion.assertEquals(true, false, "视频列表页数据加载失败，请检查网络情况！");
    		}
    	}else {
    		Assertion.assertEquals(true, false, "未找首页视频列表tab,页面不符请检查！");
    	}
    }
    
    /**
     * 进入文库列表页
     */
    public static void gotoDocList() {
    	if(Utils.isElementExist(driver, By.xpath(INDEX_TAB_DOC))) {
    		log.info("进入文库列表页！");
    		driver.findElement( By.xpath(INDEX_TAB_DOC)).click();

    		if(Utils.waitElement(driver, By.id(ListPageElements.doc_list_resource_title), 10)) {
    			
    		}else {
    			Assertion.assertEquals(true, false, "文库列表页数据加载失败，请检查网络情况！");
    		}
    	}else {
    		Assertion.assertEquals(true, false, "未找首页文库列表tab,页面不符请检查！");
    	}
    }
    
    /**
     * 进入话题列表页
     */
    public static void gotoTopicList() {
    	if(Utils.isElementExist(driver, By.xpath(INDEX_TAB_TOPIC))) {
    		log.info("进入话题列表页！");
    		driver.findElement( By.xpath(INDEX_TAB_TOPIC)).click();
    		
    		if(Utils.waitElement(driver, By.id(ListPageElements.topic_list_one_p_title), 10)) {
    			
    		}else if(Utils.isElementExist(driver, By.id(ListPageElements.topic_list_no_p_title))) {
    			
    		}else {
    			Assertion.assertEquals(true, false, "话题列表页数据加载失败，请检查网络情况！");
    		}
    	}else {
    		Assertion.assertEquals(true, false, "未找首页文库列表tab,页面不符请检查！");
    	}
    }
    
    /**
     * 进入发现列表页
     */
    public static void gotoFindPage() {
    	if(Utils.isElementExist(driver, By.id(INDEX_FIND_BTN))) {
    		log.info("进入发现页！");
    		driver.findElement(By.id(INDEX_FIND_BTN)).click();
    		Utils.sleep(2);
    	}else {
    		Assertion.assertEquals(true, false, "未找到发现按钮,页面不符请检查！");
    	}
    }
    
    
}
