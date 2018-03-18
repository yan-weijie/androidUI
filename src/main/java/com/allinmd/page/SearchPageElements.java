package com.allinmd.page;

import java.util.LinkedList;
import java.util.List;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.allinmd.android.SearchAutherCase;
import com.allinmd.util.Utils;
import com.runtime.listener.Assertion;

public class SearchPageElements {
	private static final Logger log = Logger.getLogger(SearchAutherCase.class);
	
	// 搜索输入框
	public static final String search_input = "com.allin.social:id/et_search";
	
	// 取消按钮可返回主页
	public static final String cancle_btn = "com.allin.social:id/tv_cancel";
	
	//搜索结果页tag list
	public static final String search_tab_list = "com.allin.social:id/tv_tab_title";
	
	//搜索结果页病例、视频、话题、文库资源title
	public static final String title_name = "com.allin.social:id/tv_item_comm_title";
	
	//搜索结果页病例、视频、话题、文库资源作者名
	public static final String auther_name = "com.allin.social:id/tv_item_comm_user_name";
	
	//搜索结果页医师tag名字
	public static final String doctor_name = "com.allin.social:id/tv_item_comm_fan_atten_like_name";
	
	//无结果提示
	public static final String empty_img = "com.allin.social:id/iv_empty_img";
	
	public static AndroidDriver<AndroidElement> driver;
	
	public SearchPageElements(AndroidDriver<AndroidElement> driver) {
		SearchPageElements.driver = driver;
	}
	
	/**
	 * 输入关键字并搜索
	 * @param keyword
	 */
	public static void searchMethod(String keywordAuthName) {
		if(Utils.isElementExist(driver, By.id(IndexPageElements.INDEX_SEARCH_BTN))) {
			driver.findElement(By.id(IndexPageElements.INDEX_SEARCH_BTN)).click();
		}
				
		WebElement eSearchInput = driver.findElement(By.id(search_input));
		eSearchInput.sendKeys(keywordAuthName);
		Utils.sleep(2);

		Utils.setInputMethod();
		driver.pressKeyCode(66);
		Utils.setInputAppiumMethod();
		
		if(Utils.waitElement(driver, By.id(search_tab_list), 10)){
			log.info("跳转到搜索结果页！");
		}else {
			Assertion.assertEquals(true, false, "搜索后未能跳转到or搜索结果页元素不符，请检查！");
		}
	}
	
	/**
	 * 遍历搜索结果数据
	 * @return
	 */
	public static boolean ergodicResult() {
		boolean falg = true;
		List<AndroidElement> tab_list = driver.findElements(By.id(search_tab_list));
		for(int i=0; i<tab_list.size(); i++) {
			tab_list.get(i).click();
			Utils.sleep(1);
			String tab_name = tab_list.get(i).getText();
			if (Utils.isElementExist(driver, By.id(empty_img))) {
				log.error("搜索结果\"" + tab_name + "\"tab页没有数据，请检查！");
				falg = false;
				break;
			}
		}
		
		return falg;
		
	}
	
	/**
	 * 得到搜索结果病例、视频、话题列表作者名
	 * @param tabNumber
	 * @return
	 */
	public static List<String> getResourceName(int tabNumber) {
		List<AndroidElement> tab_list = driver.findElements(By.id(search_tab_list));
		tab_list.get(tabNumber).click();
		
		String tab_name = tab_list.get(tabNumber).getText();
		
		List<String> eInfo = new LinkedList<String>();
		
		if (Utils.isElementExist(driver, By.id(empty_img))) {
			log.error("搜索结果\"" + tab_name + "\"tab页没有数据，请检查！");
		} else {
			List<AndroidElement> eAuth = driver.findElements(By.id(auther_name));
			List<AndroidElement> eTitle = driver.findElements(By.id(title_name));
			for (int i = 0; i < eAuth.size() - 1; i ++) {
				eInfo.add(eTitle.get(i).getText() + "_" + eAuth.get(i).getText());
			}
		}
		return eInfo;
	}
	
	/**
	 * 得到搜索结果医师列表名字
	 * @return
	 */
	public static List<String> getDoctorName() {
		List<AndroidElement> tab_list = driver.findElements(By.id(search_tab_list));
		tab_list.get(4).click();
		
		List<String> eDoctorName = new LinkedList<String>();
		
		if (Utils.isElementExist(driver, By.id(empty_img))) {
			log.error("搜索结果\"医师\"tab页没有数据，请检查！");
		} else {
			List<AndroidElement> eAuth = driver.findElements(By.id(doctor_name));		
			for (int i = 0; i < eAuth.size() - 1; i ++) {
				eDoctorName.add(eAuth.get(i).getText());
			}
		}
		return eDoctorName;	
	}
	
}
