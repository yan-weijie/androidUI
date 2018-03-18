package com.allinmd.page;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;

import com.allinmd.file.SaveUserName;
import com.allinmd.httpclient.GetResetPasswd;
import com.allinmd.util.Utils;
import com.runtime.listener.Assertion;

/**
 * 注册流程
 * @author sun
 *
 */
@Listeners({com.runtime.listener.AssertionListener.class})
public class RegPageElements {
	
	// 立即注册按钮
	public static final String REG_BTN = "com.allin.social:id/tv_new_register";
	
	// 注册用户名输入框
	public static final String CREATE_USER = "com.allin.social:id/edt_new_login_name";
	
	// 提示文字(xpath)
	public static final String CON_TEXT = "//*[contains(@text,'输入你的用户名,创建新账户')]";
	
	// 下一步按钮
	public static final String CREATE_USER_NEXT = "com.allin.social:id/btn_createuser_next";
	
	// 注册时手机验证码
	public static final String PHONE_CODE = "com.allin.social:id/et_input_phone_code";
	
	// 注册的密码输入框
	public static final String CREATE_PW = "com.allin.social:id/edt_new_login_passward";
	
	// 注册时手机验证码
	public static final String EMAIL_CODE = "com.allin.social:id/rl_number";
	
	// 创建账户按钮
	public static final String CREATE_CONFIRM = "com.allin.social:id/btn_new_into";
	
	// 医师认证标题
	public static final String AUTH_PAGE = "com.allin.social:id/tv_auth_title";
	
	// 错误提示
	public static final String ERROR_MESSAGE = "com.allin.social:id/tv_error_message";
	
	private static final Logger LOG = Logger.getLogger(RegPageElements.class);
	
    private static AndroidDriver<AndroidElement> driver;
  
    public RegPageElements(AndroidDriver<AndroidElement> driver){  
    	RegPageElements.driver = driver; 
    	new SaveUserName();
    	new GetResetPasswd();
    	new AuthPageElements(driver);
    }
    
    /**
     * 判断"注册"按钮是否存在
     * @return 
     */
    public static boolean regBtnIsExist() {
    	return Utils.isElementExist(driver, By.id(REG_BTN));
    }
    
    /**
     * 注册邮箱账户
     * @param username
     * @param password
     */
    public static void regEmailFlow(String username, String password) {
		WebElement reg_button = driver.findElement(By.id(REG_BTN));  	
    	reg_button.click();
    	
    	// 用户名
    	WebElement create_username = driver.findElement(By.id(CREATE_USER)); 
    	create_username.sendKeys(username);
    	Utils.sleep(1);

    	// 焦点转换
    	reg_button.click();
    	Utils.sleep(1);
    	
    	// 密码
    	WebElement create_password = driver.findElement(By.id(CREATE_PW)); 
    	create_password.sendKeys(password);
    	Utils.sleep(1);
    	
    	// 创建账户按钮
    	WebElement create_confirm = driver.findElement(By.id(CREATE_CONFIRM)); 
    	
    	if (create_confirm.isDisplayed()) {
    		LOG.info("创建用户！");
    		create_confirm.click();
    		Utils.sleep(2);
    	}
    	
    	if(Utils.waitElement(driver, By.id(AuthPageElements.START_AUTH), 15)){
    		LOG.info("注册成功，已跳转到医师认证选择页 ");
    	} else {
    		Assertion.assertEquals(true, false, "跳转到医师认证选择页 失败！请检查！");
			LOG.error("跳转到医师认证选择页 失败！请检查！");
		}
    	
    }
      
    /**
     * 注册手机账户
     * @param username
     * @param password
     */
    public static void regPhoneFlow(String username, String password) {
		WebElement reg_button = driver.findElement(By.id(REG_BTN));  	
    	reg_button.click();
    	Utils.sleep(1);
    	
    	// 用户名
    	WebElement create_username = driver.findElement(By.id(CREATE_USER)); 
    	create_username.sendKeys(username); 
    	Utils.sleep(1);
    	 
    	// 焦点转换
    	reg_button.click();
    	Utils.sleep(1);
    	
    	WebElement create_password = driver.findElement(By.id(CREATE_PW)); 
    	create_password.sendKeys(password);
    	Utils.sleep(1);
    	
//    	WebElement con_text = driver.findElement(By.xpath(CON_TEXT)); 
//    	con_text.click();					//因为邮箱自动联动后缀，必须转移下焦点。
    	   	
    	// 创建账户按钮
    	WebElement create_confirm = driver.findElement(By.id(CREATE_CONFIRM)); 
    	
    	if (create_confirm.isDisplayed()) {
    		create_confirm.click();
    	}
    	if (Utils.waitElement(driver, By.id(PHONE_CODE), 15)) {
    		WebElement phone_code = driver.findElement(By.id(PHONE_CODE));
        	if (GetResetPasswd.getPhoneCode(username).equals("404")) {
        		LOG.error("接口未返回数据，请检查！");
        		Assertion.assertEquals(false, true, "接口未返回数据，请检查！");
        	} else {
        		phone_code.sendKeys(GetResetPasswd.getPhoneCode(username));
        		
        		//加上回等待时间很长
        		//Utils.waitElement(driver, By.id(CREATE_PW));
    	    	if(Utils.isElementExist(driver, By.id(ERROR_MESSAGE)) == true){
    	    		LOG.error("注册失败，失败原因： "+ Utils.getText(driver, By.id(ERROR_MESSAGE)));
    	        	Assertion.assertEquals(true, false, Utils.getText(driver, By.id(ERROR_MESSAGE)));
    	    	}
    	    	else {
    				LOG.info("注册成功");
    			}
    	    	//Assertion.assertEquals(Utils.isElementExist(driver, By.id(ERROR_MESSAGE)), false, Utils.getText(driver, By.id(ERROR_MESSAGE)));

    	    	//Assertion.assertEquals(assertRegPhone(username), true);
        	}	
		}
    	else {
    		LOG.error("未跳转到验证码页面，跳转错误，请查看！");
        	Assertion.assertEquals(true, false, "未跳转到验证码页面，跳转错误，请查看！");
		}
    	
    	
    	
    }
    
    /**
     * 验证注册后页面跳转
     */
    public static boolean assertRegEmail(String username) {
    	boolean status = false;
    	if (AuthPageElements.skipAuthIsExist()) {
    		SaveUserName.emailName(username);
    		status = true;
    	}
    	
		return status;
    }
    
    /**
     * 验证注册后页面跳转
     */
    public static boolean assertRegPhone(String username) {
    	boolean status = false;
    	if (AuthPageElements.skipAuthIsExist()) {
//    		SaveUserName.phoneName(username);
    		status = true;
    	}
    	
		return status;
    }
}
