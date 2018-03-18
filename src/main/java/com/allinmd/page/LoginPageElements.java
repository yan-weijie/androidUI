package com.allinmd.page;

import com.sun.jna.platform.win32.WinBase;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;

import com.allinmd.util.Utils;
import com.runtime.listener.Assertion;

@Listeners({com.runtime.listener.AssertionListener.class})
public class LoginPageElements {
	
	//首次安装启动引导首页
	public static final String WELCOME_PAGE = "com.allin.social:id/linearlayout";
	
	//开启唯医体验之旅
	public static final String GO_EXPER_BTN = "com.allin.social:id/btn_experience";
	
	// "登录"按钮
	public static final String LOGIN_ALLINMD_BTN = "com.allin.social:id/tv_new_login";
	
	// "无法登录"按钮
	public static final String CANNOT_LOGIN__BTN = "com.allin.social:id/tv_cannot_login";

	// 体验一下按钮
	public static final String EXPER_BTN = "com.allin.social:id/tv_experience";
	
	// 体验一下--温馨提示--知道了
	public static final String REMIND_KNOW = "com.allin.social:id/tv_cenler";
	
	// 唯医登录输入邮箱
	public static final String LOGIN_ALLINMD_USER = "com.allin.social:id/edt_new_login_name";
	
	// 唯医登录输入密码
	public static final String LOGIN_ALLINMD_PW = "com.allin.social:id/edt_new_login_passward";
	
	// "立即登录"按钮
	public static final String LOGIN_ALLINMD_INTO = "com.allin.social:id/btn_new_into";
	
	// CAOS会员登录按钮
	public static final String LOGON_CAOS_BTN = "com.allin.social:id/ll_login_caos";
	
	// CAOS输入邮箱
	public static final String LOGIN_CAOS_USER = "com.allin.social:id/edt_new_login_name";
	
	// CAOS输入密码
	public static final String LOGIN_CAOS_PW = "com.allin.social:id/edt_new_login_passward";
	
	// CAOS会员登录按钮
	public static final String CAOS_BTN = "com.allin.social:id/btn_login_caos";
	
	// 登录错误信息
	public static final String ERROR_MESSAGE = "com.allin.social:id/tv_error_message";
	
	private static final Logger LOG = Logger.getLogger(LoginPageElements.class);
	
	private static AndroidDriver<AndroidElement> driver;
	
    public LoginPageElements(AndroidDriver<AndroidElement> driver){  
    	LoginPageElements.driver = driver; 
    	new AuthPageElements(driver);
    }

    /**
     * 判断"登录"按钮是否存在
     * @return 
     */
    public static boolean allmdBtnIsExist() {
    	return Utils.isElementExist(driver, By.id(LOGIN_ALLINMD_BTN));
    }
    
    /**
     * 唯医登录
     * @param username
     * @param password
     */
    public static void allinLogin(String username, String password) {
    	LOG.info("唯医用户 : " + username + " 进行登录");
		if(Utils.waitElement(driver, By.id(LOGIN_ALLINMD_USER), 5)) {
			// 登录账号
			WebElement login_name_text = driver.findElement(By.id(LOGIN_ALLINMD_USER));
			login_name_text.sendKeys(username);
			Utils.sleep(1);
			
			driver.findElement(By.id(LOGIN_ALLINMD_BTN)).click();
			Utils.sleep(1);	
			
			// 登录密码
			WebElement login_pwd_text = driver.findElement(By.id(LOGIN_ALLINMD_PW));
			login_pwd_text.click();
			login_pwd_text.sendKeys(password);
			Utils.sleep(1);
			// 立即登录按钮
			driver.findElement(By.id(LOGIN_ALLINMD_INTO)).click();
			Utils.sleep(1);

			if (Utils.isElementExist(driver, By.id(ERROR_MESSAGE))) {
				LOG.error("用户:【" + username + "】登录失败 ! \n错误原因:"+Utils.getText(driver, By.id(ERROR_MESSAGE)));
		    	Assertion.assertEquals(true, false, Utils.getText(driver, By.id(ERROR_MESSAGE)));
			}else if(Utils.isElementExist(driver, By.id(AuthPageElements.SKIP_AUTH))) {
				AuthPageElements.skipDoctorCer();
				LOG.info("未认证账户:【" + username + "】登录成功 !");
			}else if(Utils.waitElement(driver, By.id(IndexPageElements.MARKABLE_TAB_LIST), 10)) {
				LOG.info("用户:【" + username + "】登录成功 !");
			}else if(Utils.isElementExist(driver, By.id(IndexPageElements.NO_RESULT_PNG))) {
				driver.findElement(By.id(IndexPageElements.NO_RESULT_RETRY)).click();
				if(Utils.waitElement(driver, By.id(IndexPageElements.MARKABLE_TAB_LIST), 10)) {
					LOG.info("用户:【" + username + "】登录成功 !");
				}else {
					Assertion.assertEquals(true, false, "首页数据加载失败请检查！");
				}
			}
		}else {
			Assertion.assertEquals(true, false, "未找到登陆页面账号输入框，请检查页面是否正确！");
		}		
    }
    
    /**
     * CAOS登录
     * @param username
     * @param password
     */
    public static void caosLogin(String username, String password) {
    	
    	LOG.info("[" + username + "] 开始CAOS用户登录");
    	driver.findElement(By.id(LOGON_CAOS_BTN)).click();
    	Utils.sleep(1);
    	driver.findElement(By.id(LOGIN_CAOS_USER)).sendKeys(username);
    	Utils.sleep(1);
    	driver.findElement(By.id(LOGIN_CAOS_PW)).sendKeys(password);
    	Utils.sleep(1);
    	driver.findElement(By.id(CAOS_BTN)).click();
    	
    	Assertion.assertEquals(Utils.isElementExist(driver, By.id(ERROR_MESSAGE)), false, Utils.getText(driver, By.id(ERROR_MESSAGE)));
    	
    	if(Utils.waitElement(driver, By.id(IndexPageElements.MARKABLE_TAB_LIST), 10)) {
			LOG.info("CAOS用户:【" + username + "】登录成功 !");
		}else {
			Assertion.assertEquals(true, false, "账户登录后未能跳转到首页or首页数据加载失败请检查！");
		}
    }
    
    /**
     * 跳过登陆
     */
    public static void skipLogin() {
    	if(allmdBtnIsExist()){
    		LOG.info("\"跳过登录，体验一下！\"");
    		driver.findElement(By.id(EXPER_BTN)).click();
    		Utils.sleep(1);
    		driver.findElement(By.id(REMIND_KNOW)).click();
    		if(Utils.waitElement(driver, By.id(IndexPageElements.MARKABLE_TAB_LIST), 10)) {
    			LOG.info("体验一下成功进入首页 !");
    		}else {
    			Assertion.assertEquals(true, false, "体验一下后未能跳转到首页or首页数据加载失败请检查！");
    		}
    	}
    }
    
    /**
     *首次进入唯医APP完成初始引导 
     */ 
    public static void assertSetupGui() {
    	Utils.sleep(2);
    	boolean flag = false;
    	if(Utils.isElementExist(driver, By.id(WELCOME_PAGE))) {
    		LOG.info("首次进入唯医app");
    		flag = true;
    	}
    	while(flag) { 
    		Dimension size = driver.manage().window().getSize();
	    	driver.swipe(size.getWidth() / 2 + 500, size.getHeight() / 2, 100, size.getHeight() / 2, 0);
	    	Utils.sleep(1);
	    	if (Utils.isElementExist(driver, By.id(GO_EXPER_BTN))) {
	    		LOG.info("\"开启唯医之旅\"");
	    		
	        	driver.findElement(By.id(GO_EXPER_BTN)).click();
	        	flag = false;
	    	}
		}
	}
	
}
