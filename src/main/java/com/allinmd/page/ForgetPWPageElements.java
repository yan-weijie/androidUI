package com.allinmd.page;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;

import com.allinmd.httpclient.GetResetPasswd;
import com.allinmd.util.Utils;
import com.runtime.listener.Assertion;

@Listeners({com.runtime.listener.AssertionListener.class})
public class ForgetPWPageElements {

	// "找回密码"按钮
	public static final String LOGIN_FIND_PW = "com.allin.social:id/tv_dialog_listview_item";
	
	// 用户名输入框
	private static final String USERNAME_INPUT = "com.allin.social:id/edt_phone";
	
	// 发送验证码确定按钮
	private static final String FORGET_PW_CONFIRM = "com.allin.social:id/btn_new_into";
	
	// 邮箱验证码输入框
	public static final String CODE_INPUT = "com.allin.social:id/ll_up_keyboard";
	
	// 设置新密码输入框
	private static final String NEW_PASSWORD_ONE = "com.allin.social:id/edt_pwd_one";
	
	// 设置新密码输入框
	private static final String NEW_PASSWORD_TWO = "com.allin.social:id/edt_pwd_two";
	
	// 重置密码确认按钮
	private static final String NEW_PW_CONFIRM = "com.allin.social:id/btn_new_into";
	
	// 邮箱修改密码修改成功提示"宁愿麻烦"
	private static final String EMAIL_MESSAGE = "com.allin.social:id/tv_cenler";
	
	private static AndroidDriver<AndroidElement> driver;
	
	private static final Logger LOG = Logger.getLogger(ForgetPWPageElements.class);
    
    public ForgetPWPageElements(AndroidDriver<AndroidElement> driver){  
    	ForgetPWPageElements.driver = driver; 
    	new AuthPageElements(driver);
    }
    
    /**
     * 找回密码，发送重置链接
     * @param username
     */
	public static void forgetPassword(String username,String password) {
		// 无法登陆
		WebElement login_allinmd_btn = driver.findElement(By.id(LoginPageElements.CANNOT_LOGIN__BTN));
		login_allinmd_btn.click();
		
		WebElement forget_pw_btn = driver.findElement(By.id(LOGIN_FIND_PW));
		forget_pw_btn.click();
		
		WebElement username_input = driver.findElement(By.id(USERNAME_INPUT));
		username_input.sendKeys(username);
		Utils.sleep(1);
		
		// 发送验证码按钮
		if(Utils.isElementExist(driver, By.id(FORGET_PW_CONFIRM))) {
			String code = "";
			WebElement forget_pw_confirm = driver.findElement(By.id(FORGET_PW_CONFIRM));
			forget_pw_confirm.click();
			Utils.sleep(3);
			if(username.contains("@")) {
				code = GetResetPasswd.getEmailCode(username);
	    	}else if (GetResetPasswd.getPhoneCode(username).equals("404")) {
	    		Assertion.assertEquals(true, false, "接口未返回数据，请检查！");	
			}else {	
				code = GetResetPasswd.getPhoneCode(username);
	    	}
			// 输入验证码
			LOG.info("输入验证码");
	    	WebElement code_num = driver.findElement(By.id(CODE_INPUT));
	    	code_num.sendKeys(code);
		}
	
    	if (Utils.isElementExist(driver, By.id(LoginPageElements.ERROR_MESSAGE))) {
			LOG.error("错误: " + Utils.getText(driver, By.id(LoginPageElements.ERROR_MESSAGE)));
	    	Assertion.assertEquals(true, false, Utils.getText(driver, By.id(LoginPageElements.ERROR_MESSAGE)));
		}
		
    	// 重置密码，输入新密码
		LOG.info("输入新密码: " + password);
		WebElement new_password_one = driver.findElement(By.id(NEW_PASSWORD_ONE));
		new_password_one.sendKeys(password);
		Utils.sleep(1);
		
		WebElement new_password_two = driver.findElement(By.id(NEW_PASSWORD_TWO));
		new_password_two.sendKeys(password);
		Utils.sleep(1);
		
		// 新密码确认
		WebElement new_password_confirm = driver.findElement(By.id(NEW_PW_CONFIRM));
		new_password_confirm.click();
		Utils.sleep(1);
		
		if(Utils.isElementExist(driver, By.id(EMAIL_MESSAGE))) {
			// 邮箱修改密码提示
			WebElement email_message = driver.findElement(By.id(EMAIL_MESSAGE));
			email_message.click();
			Utils.sleep(1);
		}
			
		if (Utils.isElementExist(driver, By.id(LoginPageElements.ERROR_MESSAGE))) {
			LOG.error("用户:【" + username + "】找回密码失败 ! \n错误原因:"+Utils.getText(driver, By.id(LoginPageElements.ERROR_MESSAGE)));
	    	Assertion.assertEquals(true, false, Utils.getText(driver, By.id(LoginPageElements.ERROR_MESSAGE)));
		}else if(AuthPageElements.skipAuthIsExist()) {
			LOG.info("未认证账户:【" + username + "】找回密码成功 !");
			AuthPageElements.skipDoctorCer();		
		}else if(Utils.isElementExist(driver, By.xpath(IndexPageElements.INDEX_TAB_HOT))) {
			LOG.info("用户:【" + username + "】登录成功 !");
		}else {
			Assertion.assertEquals(true, false, "账户登录后未能跳转到首页or首页数据加载失败请检查！");
		}
	}

}
