package com.allinmd.page;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;

import com.allinmd.util.Utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * 发现页面元素
 */
public class FindPageElements {
	
	// 后退
	public static final String sort_back = "com.allin.social:id/ll_back";
	
	// 按专业
	public static final String major_sort = "com.allin.social:id/tv_discovery_major";
	
	// 按疾病
	public static final String illness_sort = "com.allin.social:id/tv_discovery_illness";
		
	// 按术式
	public static final String operation_sort = "com.allin.social:id/tv_discovery_operation";
		
	// 按类型
	public static final String style_sort = "com.allin.social:id/tv_discovery_style";
	
	// 按专题
	public static final String special_sort = "com.allin.social:id/tv_discovery_special";
	
	// 按*分类or排序list
	public static final String sort_style_list = "com.allin.social:id/tv_item_filter";
		
	// 筛选
	public static final String screen = "//*[contains(@name, '筛选')]";
		
	// 全部类型
	public static final String all_type = "//*[contains(@text, '全部类型')]";
			
	// 智能排序
	public static final String sorting_rule = "//*[contains(@text, '智能排序')]";
		
	// 按*排序的title
	public static final String page_title = "com.allin.social:id/tv_doc_name";
		
	// 浏览数
	public static final String look_count = "com.allin.social:id/tv_item_comm_look_count";
		
	// 评论数 
	public static final String review_count = "com.allin.social:id/tv_item_comm_review_count";	
	/**
	 * 标签列表	
	 */
	// 按标签
	public static final String lable_sort = "com.allin.social:id/tv_discovery_lable";
	
	// 标签列表 标签title list
	public static final String lable_title_list = "com.allin.social:id/item_contact_title";
	
	// 标签列表页： 最多评论								
	public static final String lable_max_review = "com.allin.social:id/radio_btn_option_comment_most";
	
	// 标签列表页： 最多浏览
	public static final String lable_max_look = "com.allin.social:id/radio_btn_option_scanner_most";
	
	// 资源类型选择按钮：全部
	public static final String data_sort = "com.allin.social:id/tv_label_all";
	
	// 资源类型：病例 name
	public static final String data_sort_case = "病例";
	
	// 标签列表页：有图的病例资源浏览数
	public static final String lable_look_count = "com.allin.social:id/tv_case_one_p_v_scanner_amount";
			
	// 标签列表页：病例资源评论数 
	public static final String lable_review_count = "com.allin.social:id/tv_case_no_p_v_comments_amount";
	
	// 资源作者img
	public static final String item_user_img = "com.allin.social:id/tv_item_comm_user_img";
	
	private static AndroidDriver<AndroidElement> driver;
	private static final Logger log = Logger.getLogger(FindPageElements.class);
	
	public FindPageElements(AndroidDriver<AndroidElement> driver) {
		FindPageElements.driver = driver;
		new IndexPageElements(driver);
	}
	
	/**
	 * 验证标签列表资源排序
	 */
	public static boolean checkLableOrder(String order_by, String order_num) {
		boolean flag = false;
		
		driver.findElement(By.id(order_by)).click();
		
		log.info("验证");
		if(Utils.waitElement(driver, By.id(order_num), 10)) {
			try {			
				List<AndroidElement> list_review_num = driver.findElements(By.id(order_num));
				List<String> review_num = new LinkedList<String>();		
				for(int i = 0; i < list_review_num.size() - 1; i++) {
					if (list_review_num.get(i).getText().equals("") == false) {
						if(list_review_num.get(i).getText().contains("万+")) {
							review_num.add(list_review_num.get(i).getText().replace("万+", "0000"));
						}else {
							review_num.add(list_review_num.get(i).getText().replace("千+", "000"));
						}							
					}
				}		

				List<String> statusList = new LinkedList<String>();
				for(int i = 0; i < review_num.size() - 1; i++) {	
//					System.out.println("第" + (i + 1) + "个帖子:" + review_num.get(i));
//					System.out.println("第" + (i + 2) + "个帖子:" + review_num.get(i + 1));
					flag = Integer.parseInt(review_num.get(i)) >= Integer.parseInt
							(review_num.get(i + 1));
					
					if (i <= 5) {
						break;		//比较前5个
					}
					
					statusList.add(String.valueOf(flag));
				}
			
				if (statusList.contains("false")) {
					System.out.println("statusList = " + statusList);
					System.out.println("review_num = " + review_num);
					System.out.println("statusList = " + statusList);
					return false;
				} else {
					return true;
				}
			} catch (StaleElementReferenceException e) {
//				e.getStackTrace();
			}
		}
		
		
		return flag;
	}
	
	
	
	/**
	 * 排序规则验证
	 * @param order_number
	 * @param order_num
	 */
	public static boolean checkOrder(int order_number, String order_num) {
		boolean flag = false;
		driver.findElement(By.xpath(sorting_rule)).click();
		
		List<AndroidElement> filter_style = driver.findElements(By.id(sort_style_list));
		filter_style.get(order_number).click();
		
		log.info("验证");
		if(Utils.waitElement(driver, By.id(order_num), 10)) {
			try {			
				List<AndroidElement> list_review_num = driver.findElements(By.id(order_num));
				List<String> review_num = new LinkedList<String>();		
				for(int i = 0; i < list_review_num.size() - 1; i++) {
					if (list_review_num.get(i).getText().equals("") == false) {
						if(list_review_num.get(i).getText().contains("万+")) {
							review_num.add(list_review_num.get(i).getText().replace("万+", "0000"));
						}else {
							review_num.add(list_review_num.get(i).getText().replace("千+", "000"));
						}							
					}
				}		

				List<String> statusList = new LinkedList<String>();
				for(int i = 0; i < review_num.size() - 1; i++) {	
//					System.out.println("第" + (i + 1) + "个帖子:" + review_num.get(i));
//					System.out.println("第" + (i + 2) + "个帖子:" + review_num.get(i + 1));
					flag = Integer.parseInt(review_num.get(i)) >= Integer.parseInt
							(review_num.get(i + 1));
					
					if (i <= 5) {
						break;		//比较前5个
					}
					
					statusList.add(String.valueOf(flag));
				}
			
				if (statusList.contains("false")) {
					System.out.println("statusList = " + statusList);
					System.out.println("review_num = " + review_num);
					System.out.println("statusList = " + statusList);
					return false;
				} else {
					return true;
				}
			} catch (StaleElementReferenceException e) {
//				e.getStackTrace();
			}
		}
		
		return flag;

	}
	
	
	/**
	 * 分类筛选资源
	 * @param by
	 */
	public static void sort(By by) {
		IndexPageElements.gotoFindPage();
		driver.findElement(by).click();
		
		Utils.waitElement(driver, By.id(sort_style_list), 10);
		String pageName = driver.findElement(By.id(page_title)).getText();
		log.info(pageName);
		
		List<AndroidElement> filter_style = driver.findElements(By.id(sort_style_list));
		filter_style.get(1).click();
		
		switch(pageName) {
		case "按专业":
			List<AndroidElement> filter_style1 = driver.findElements(By.id(sort_style_list));
			filter_style1.get(7).click();
			break;
		case "按术式":
			List<AndroidElement> filter_style2 = driver.findElements(By.id(sort_style_list));
			filter_style2.get(6).click();
			break;
		default:
			List<AndroidElement> filter_style3 = driver.findElements(By.id(sort_style_list));
			filter_style3.get(4).click();
			break;
		}
		
		Utils.sleep(1);
	}
	
}
