package com.allinmd.page;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.allinmd.util.Utils;
import com.runtime.listener.Assertion;
import com.allinmd.page.LoginPageElements;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class PerCenterPageElements {
	public static Logger log = Logger.getLogger(PerCenterPageElements.class);
	
	// 设置 xpath
	public static final String SET = "com.allin.social:id/ll_setting";
	
	// 账号安全
	public static final String ACCOUNT_SECURITY = "com.allin.social:id/tv_account_security";
	
	// 修改密码(点击修改)
	public static final String UPDATA_PW_BTN = "com.allin.social:id/tv_account_security_password";
	
	// 当前密码输入框
	public static final String CURRENT_PW_INPUT = "com.allin.social:id/et_current_password_hint";
	
	// 新密码输入框
	public static final String NEW_PW_INPUT = "com.allin.social:id/et_new_password_hint";
	
	// 确认密码输入框
	public static final String SRUE_PW_INPUT = "com.allin.social:id/et_srue_password_hint";
	
	// 保存
	public static final String SAVE_PW = "com.allin.social:id/btn_save_password";
	
	// 修改密码错误信息
	public static final String UP_PW_ERRORMS = "com.allin.social:id/tv_save_errormessage";
	
	// 退出当前账号
	public static final String LOGOUT_BTN = "com.allin.social:id/rl_quit_account";
	
	// 退出确定按钮 xpath
	public static final String LOGOUT_CONFIRM_BTN = "//*[contains(@text, '确定')]";
	
	// 去认证(未认证用户个人中心)
	public static final String PER_AUT_BTN = "com.allin.social:id/tv_goto_auth";
	
	// 个人中心主页 xpath
	public static final String PER_INDEX = "//*[contains(@text, 主页)]";
	
	// 个人中心tab list
	public static final String PER_TAB_LIST = "com.allin.social:id/tv_tab_title";
	/**
	 * 贡献
	 */
	// 贡献tab数据展示规则下拉菜单：全部
	public static final String CB_DOWNMENU_BTN = "com.allin.social:id/tv_all";
	
	// 个人中心列表资源title list
	public static final String FTV_TITLE_LIST = "com.allin.social:id/ftv_title";
	/**
	 * 我的收藏
	 */
	// 我收藏的 xpath
	public static final String MY_COLLECTION = "收藏的";
	
	// 收藏or关注的资源类型list
	public static final String COLLECTION_TYPE_TAB = "com.allin.social:id/tv_tab_title";
	
	// 删除收藏
	public static final String DELETE_COLLECTION_BTN = "com.allin.social:id/ll_delete";
	
	// 确认取消收藏
	public static final String DELETE_COLLECTION_CONFIRM = "//*[contains(@text, '取消收藏)]";
			
	// 下载的 xpath
	public static final String MY_DOWNLOAD = "//*[contains(@text, '下载的')]";
	
	// 关注的 xpath
	public static final String MY_FOLLOW = "关注的";
	
	// 我的评论 name
	public static final String MY_COMMENT = "我评论的";
	
	// 我的评论title list
	public static final String MY_CONTENT_TITLE = "com.allin.social:id/ftv_content";
	
	// 草稿 name
	public static final String MY_DRAFT_BTN = "草稿";
	
	// 草稿资源title list
	public static final String DRAFT_TITLE_LIST = "com.allin.social:id/tv_title";
	
	private static AndroidDriver<AndroidElement> driver;
	
	public PerCenterPageElements(AndroidDriver<AndroidElement> driver) {
		PerCenterPageElements.driver = driver;
		new IndexPageElements(driver);
	}
	
	/**
	 * 判断贡献展示规则下拉菜单是否打开,如打开点击收起。
	 */
	public static void downMenuIsExist() {
		if(Utils.isElementExist(driver, By.id(CB_DOWNMENU_BTN))) {
			driver.findElement(By.id(CB_DOWNMENU_BTN)).click();
		}
	}
	
	/**
	 * 检查保存的草稿
	 * @return
	 */
	public static boolean checkDraft(String title) {
		boolean flag = false;
		String draftname = "";
		driver.findElement(By.name(MY_DRAFT_BTN)).click();
		Utils.sleep(2);
		if(Utils.isElementExist(driver, By.id(DRAFT_TITLE_LIST))) {
			List<AndroidElement> ss = driver.findElements(By.id(DRAFT_TITLE_LIST));
			draftname = ss.get(0).getText();
			if(title.substring(0, 5).equals(draftname.substring(0, 5))) {
				ss.get(0).click();
				flag = true;
			}
		}
		return flag;	
	}
	
	/**
	 * 个人中心检查收藏or关注的资源
	 * @param memoryType 收藏or关注
	 * @param title 资源name
	 * @param dateType 资源类型
	 */
	public static void checkCollection(String memoryType, String title, String dateType) {
		String name = "";
		if(memoryType.contains("关注")) {
			driver.findElement(By.name(MY_FOLLOW)).click();
		}else {
			driver.findElement(By.name(MY_COLLECTION)).click();
		}
		
		List<AndroidElement> colle_type_tab = driver.findElements(By.id(COLLECTION_TYPE_TAB));
		for(int i=0; i<colle_type_tab.size(); i++) {
			if(colle_type_tab.get(i).getText().equals(dateType)) {
				colle_type_tab.get(i).click();
				break;
			}
		}
		
		if(Utils.waitElement(driver, By.id(FTV_TITLE_LIST), 5)) {
			List<AndroidElement> name_list = driver.findElements(By.id(FTV_TITLE_LIST));
			name = name_list.get(0).getText();
			int j = name.length();
			int y = 5;
			if (j < y) {
				y = j;
			}
			if(title.substring(0, y).equals(name.substring(0, y))) {
				log.info("找到"+memoryType+"的资源：\""+title+"\"，"+memoryType+"成功！");
				if(memoryType.contains("关注")) {
					name_list.get(0).click();
				}
			}else {
				log.error("我的"+memoryType+dateType+"第一条资源："+name+"与"+memoryType+"的资源："+title+" 不一致,请检查是否收藏成功！");
				Assertion.assertEquals(true, false, "我的"+memoryType+dateType+"第一条资源："+name+"与"+memoryType+"的资源："+title+" 不一致,请检查是否收藏成功！");
			}
		}else {
			log.error("我的"+memoryType+dateType+"没有数据，请检查是否"+memoryType+"成功！");
			Assertion.assertEquals(true, false, "我的"+memoryType+dateType+"没有数据，请检查是否"+memoryType+"成功！");
		}	
	}
	
	/**
	 * 个人中心取消资源收藏
	 */
	public static void deleteColection(String title) {
		String name = "";
		if(Utils.isElementExist(driver, By.id(FTV_TITLE_LIST))) {
			List<AndroidElement> name_list = driver.findElements(By.id(FTV_TITLE_LIST));
			name = name_list.get(0).getText();
			if(title.substring(0, 3).equals(name.substring(0, 3))) {
				log.info("开始删除收藏资源：\""+name+"\"");
				
				driver.swipe(500,535,100,535,500);
				
				if(Utils.isElementExist(driver, By.id(DELETE_COLLECTION_BTN))) {
					driver.findElement(By.id(DELETE_COLLECTION_BTN)).click();
					
					driver.tap(1, 500, 1650, 500);
					Utils.sleep(1);
					
					if(Utils.isElementExist(driver, By.id(FTV_TITLE_LIST))) {
						List<AndroidElement> name_list1 = driver.findElements(By.id(FTV_TITLE_LIST));
						name = name_list1.get(0).getText();
						int j = name.length();
						int y = 5;
						if (j < y) {
							y = j;
						}
						if(title.substring(0, y).equals(name.substring(0, y))) {
							log.error("我的收藏第一条资源："+name+"与收藏的资源："+title+"一致,请检查是否删除收藏成功！");
						}else {
							log.info("删除收藏的资源：\""+title+"\" 成功！");
						}			
					}else {
						log.info("删除收藏的资源：\""+name+"\" 成功！");
					}
				}else {
					log.error("我的收藏第一条资源左滑后未找到\"删除收藏\"按钮，抢检查！");	
				}
			}			
		}		
	}	
	
	/**
	 * 个人中心贡献检查发布的资源
	 * @param title
	 */
	public static boolean checkPublish(String title) {
		log.info("个人中心效验发布的资源：" + title);
		
		List<AndroidElement> per_tab_list = driver.findElements(By.id(PER_TAB_LIST));
		per_tab_list.get(1).click();
		
		Utils.sleep(1);
		
		boolean flag = false;
		if(Utils.waitElement(driver, By.id(FTV_TITLE_LIST), 5)) {
			List<AndroidElement> ftv_title_list = driver.findElements(By.id(FTV_TITLE_LIST));
			for(int i=0; i<ftv_title_list.size(); i++) {
				String ftv_title = ftv_title_list.get(i).getText();
				
				if(ftv_title.substring(0, 10).equals(title.substring(0, 10))) {
					ftv_title_list.get(i).click();
					flag = true;
					break;
				}
			}
			
		}else {
			log.error("没有发现贡献资源");
		}
		return flag;
	}
	
	/**
	 * 个人中心我的评论验证发布的评论
	 */
	public static String checkComment(String comment) {
		log.info("个人中心效验发布的评论：" + comment);
		String my_content = "";
		List<AndroidElement> per_tab_list = driver.findElements(By.id(PER_TAB_LIST));
		per_tab_list.get(0).click();

		if(Utils.waitElement(driver, By.name(MY_COMMENT), 5)) {
			driver.findElement(By.name(MY_COMMENT)).click();
		}else {
			Assertion.assertEquals(true, false, "未找到个人中心我评论的按钮，请检查！");
		}

		if(Utils.waitElement(driver, By.id(MY_CONTENT_TITLE), 5)) {
			List<AndroidElement> my_content_list = driver.findElements(By.id(MY_CONTENT_TITLE));
			my_content = my_content_list.get(0).getText();
			if ( my_content.substring(0, 8).equals(comment.substring(0, 8))) {
				log.info("我的评论验证: true");
			}
		}else {
			Assertion.assertEquals(true, false, "我的评论内容为空，请检查评论是否发布成功！");
		}
		return my_content;
	}
	
	/**
	 * 账户修改密码
	 */
	public static void upPassword(String oldPW, String newPW) {
		if(Utils.isElementExist(driver, By.id(SET)) == false) {
			log.error("设置按钮未找到or页面元素不符，请检查！");
		}
		driver.findElement(By.id(SET)).click();
		
		driver.findElement(By.id(ACCOUNT_SECURITY)).click();
		
		driver.findElement(By.id(UPDATA_PW_BTN)).click();
		
		driver.findElement(By.id(CURRENT_PW_INPUT)).sendKeys(oldPW);
		
		driver.findElement(By.id(NEW_PW_INPUT)).sendKeys(newPW);
		
		driver.findElement(By.id(SRUE_PW_INPUT)).sendKeys(newPW);
		
		driver.findElement(By.id(SAVE_PW)).click();
		Utils.sleep(2);
		if(Utils.isElementExist(driver, By.id(UP_PW_ERRORMS))) {
			String error_ms = driver.findElement(By.id(UP_PW_ERRORMS)).getText();
			Assertion.assertEquals(true, false, error_ms);
		}else if(Utils.isElementExist(driver, By.id(UPDATA_PW_BTN))) {
			log.info("修改密码成功！");
		}else {
			log.error("修改密码后未能跳转到账户安全or页面元素不符请检查！");
		}
		//后退到个人中心
		driver.findElement(By.id("com.allin.social:id/iv_back")).click();
		Utils.sleep(1);
		driver.findElement(By.id("com.allin.social:id/iv_back")).click();
	}
	
	/**
	 * 账户退出登录
	 */
	public static void logOut() {
		if(Utils.isElementExist(driver, By.id(SET)) == false) {
			IndexPageElements.gotoPer();
		}

		log.info("开始退出账户！");
		driver.findElement(By.id(SET)).click();
		driver.findElement(By.id(LOGOUT_BTN)).click();
		Utils.sleep(1);
		driver.findElement(By.xpath(LOGOUT_CONFIRM_BTN)).click();
		
		if(Utils.waitElement(driver, By.id(LoginPageElements.LOGIN_ALLINMD_BTN), 10)) {
			log.info("退出成功！");
		}else {
			Assertion.assertEquals(true, false, "退出登录后未能跳转到登陆页面，请检查！");
		}
	}
}
