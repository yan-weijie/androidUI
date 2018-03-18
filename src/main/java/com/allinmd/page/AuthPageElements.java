package com.allinmd.page;

import java.io.IOException;
import java.util.List;

import io.appium.java_client.android.AndroidDriver;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.android.AndroidElement;

import com.allinmd.file.ReadWriteTxtFile;
import com.allinmd.util.Utils;
import com.runtime.listener.Assertion;

public class AuthPageElements {
	
	// 跳过认证按钮
	public static final String SKIP_AUTH = "com.allin.social:id/tv_right";

	// 身份认证：骨科
	public static final String START_AUTH = "com.allin.social:id/ll_hbune";
	
	// 身份认证：手外科
	public static final String START_AUTH_TWO = "com.allin.social:id/ll_hand_surgery";
	
	// 完善资料
	public static final String AUTH_CANCEL = "com.allin.social:id/tv_cancel";
		
	// 认证姓名							
	public static final String AUTH_NAME = "com.allin.social:id/et_auth_name";
	
	// 年龄
	public static final String AGE = "com.allin.social:id/et_auth_age";
	
	// 单位
	public static final String COMPANY = "com.allin.social:id/tv_auth_hospital";

	// 我在医院or学校 list
	public static final String HOSPITAL = "com.allin.social:id/tv_content";

	// 医院搜索按钮
	public static final String SEARCH_HOSPITAL = "com.allin.social:id/rl_search";

	//医院搜索输入框
	public static final String SEARCH_HOSPITAL_INPUT = "com.allin.social:id/et_search";

	//输入指定的医院没有结果添加
	public static final String HOSPITAL_ADD = "com.allin.social:id/tv_add";

	//搜索结果医院列表 list
	public static final String HOSPITAL_LIST = "com.allin.social:id/tv_name";

	// 添加医院保存按钮
	public static final String HOSPITAL_SAVE = "com.allin.social:id/btn_commit";
	
	// 职称
	public static final String JOB = "com.allin.social:id/tv_auth_medicaltitle";
	
	// 选择职称住院医师
	public static final String CB_RESIDENT = "com.allin.social:id/cb_resident";
	
	// 保存职称\保存专业
	public static final String DIALOG_SAVE = "com.allin.social:id/tv_right";
	
	// 专业
	public static final String MAJOR = "com.allin.social:id/tv_auth_major";
	
	// 选择专业 list
	public static final String CHECKBOX = "com.allin.social:id/checkbox";

	// 选择证件类型
	public static final String UNTH_TYPE = "com.allin.social:id/tv_auth_cardtype";
	
	// 医师资格证 xpath
	public static final String IDENTITY_PAPERS = "//*[contains(@text, '医师资格证')]";
	
	// 证件编号输入框
	public static final String UNTH_PHYNUMBER_INPUT = "com.allin.social:id/et_auth_cardnum";
	
	// 上传认证图片按钮
	public static final String UNTH_PHYPHOTO = "com.allin.social:id/iv_auth_uploadimg";
	
	// 图片从相册选择 list
	public static final String UNLOAD_CONFIM = "com.allin.social:id/tv_content";
	
	// 选择图片(xpath)
	public static final String SELECT_PHOTO = "//android.widget.RelativeLayout[contains(@index,1)]";
	
	// 提交（认证按钮）
	public static final String AUTH_BTN = "com.allin.social:id/btn_auth_commit";
	
	// 感兴趣的标签list
	public static final String SUBSCRIBE_LABLE = "com.allin.social:id/tv_tag";
	
	// 选择关注的专业——点击下一步
	public static final String LABLE_NEXT_BTN = "com.allin.social:id/tv_right";
	
	// 添加头像（从已有照片中选择）
	public static final String UPLOAD_PHOTO = "com.allin.social:id/btn_headportrait_native";
	
	// 取消上传头像按钮
	public static final String CLOSE_UPLOAD_PHOTO = "com.allin.social:id/iv_error_close";
	
	// 上传图片按钮 (xpath)
	public static final String UNLOAD__PHOTO_CONFIM = "//*[contains(@text,'从手机相册选择')]";
	
	// 下一步(上传头像)
	public static final String PHOTO_NEXT = "com.allin.social:id/tv_next";
	
	// 跳过(上传头像)
	public static final String PHOTO_SKIP = "com.allin.social:id/tv_cancel_or_complete";
		
	// 推荐医师list
	public static final String ATTENTION_DOCTOR = "com.allin.social:id/iv_attention";
		
	// 完成(推荐医师)
	public static final String FINISH_DOCTOR = "com.allin.social:id/btn_complete";
	
	// 跳过(推荐医师)
	public static final String SKIP_DOCTOR = "com.allin.social:id/tv_skip";
		
	// 认证错误提示
	public static final String ERROR_MESSAGE = "com.allin.social:id/tv_login_errormessage";
	
	private static final Logger log = Logger.getLogger(AuthPageElements.class);
	
	private static AndroidDriver<AndroidElement> driver;
    
    public AuthPageElements(AndroidDriver<AndroidElement> driver){  
    	AuthPageElements.driver = driver; 
    	new IndexPageElements(driver);
    }
       
    /**
     * 判断"跳过认证"按钮是否存在
     * @return 
     */
    public static boolean skipAuthIsExist() {
    	return Utils.isElementExist(driver, By.id(SKIP_AUTH));
    }
    
    /**
     * 跳过认证
     */
    public static void skipDoctorCer() {	
		if(skipAuthIsExist()) {
			log.info("跳过认证!");
			driver.findElement(By.id(SKIP_AUTH)).click();
		}else {
			Assertion.assertEquals(true, false, "未找到跳过认证按钮！请检查页面是否正确。");
		}
		Utils.sleep(2);
		if(Utils.isElementExist(driver, By.id(PHOTO_NEXT))) {
			driver.findElement(By.id(PHOTO_NEXT)).click();
			log.info("选择关注专业");
			selectMajor();
			//跳过推荐医师
			WebElement skip_doctor = driver.findElement(By.id(SKIP_DOCTOR));
			skip_doctor.click();

			Utils.waitElement(driver, By.id(IndexPageElements.MARKABLE_TAB_LIST), 10);
			if(Utils.isElementExist(driver, By.id(IndexPageElements.MARKABLE_TAB_LIST))) {
				log.info("账户注册跳过认证：true");
			}else {
				Assertion.assertEquals(true, false, "账户注册跳过认证后未能跳转到首页or首页数据加载失败请检查！");
			}
		}
		
    }
    
    /**
     * 医师认证Flow
     */
	public static void doctorCer() {
		if (Utils.isElementExist(driver, By.id(START_AUTH))) {
			WebElement start_auth = driver.findElement(By.id(START_AUTH));
			start_auth.click();
			Utils.sleep(2);
		}
		Utils.waitElement(driver, By.id(AUTH_NAME), 10);
		log.info("填写基本信息");
		// 姓名
		WebElement auth_name = driver.findElement(By.id(AUTH_NAME));
		String name = ReadWriteTxtFile.getLastName()+ReadWriteTxtFile.getFirstName();
		auth_name.sendKeys(name);
		Utils.sleep(1);
		
		// 年龄
		WebElement auth_age = driver.findElement(By.id(AGE));
		auth_age.click();
		Utils.sleep(1);
		auth_age.sendKeys("44");
		Utils.sleep(1);
		
		// 单位
		WebElement hospital = driver.findElement(By.id(COMPANY));
		hospital.click();
		Utils.sleep(1);

		List<AndroidElement> in_hospital = driver.findElements(By.id(HOSPITAL));
		in_hospital.get(1).click();
		WebElement search_hospital = driver.findElement(By.id(SEARCH_HOSPITAL));
		search_hospital.click();
		Utils.sleep(1);
		WebElement search_hospital_input = driver.findElement(By.id(SEARCH_HOSPITAL_INPUT));
		search_hospital_input.sendKeys("平凉地区疾病防控中心");

		if (Utils.waitElement(driver, By.id(HOSPITAL_ADD),5)) {
			driver.findElement(By.id(HOSPITAL_ADD)).click();
			Utils.sleep(1);
			driver.findElement(By.id(HOSPITAL_SAVE)).click();
		}else if (Utils.isElementExist(driver, By.id(HOSPITAL_LIST))){
			List<AndroidElement> hospital_list = driver.findElements(By.id(HOSPITAL_LIST));
			hospital_list.get(0).click();
		}

		Utils.sleep(1);
		//选择职称（住院医师，副教授，硕士生导师）
		WebElement job = driver.findElement(By.id(JOB));		
		job.click();
		Utils.sleep(1);
		
		WebElement cb_resident = driver.findElement(By.id(CB_RESIDENT));
		cb_resident.click();
		Utils.sleep(1);
		
		WebElement dialog_save = driver.findElement(By.id(DIALOG_SAVE));
		dialog_save.click();
		Utils.sleep(1);
		
		//选择专业
		WebElement major = driver.findElement(By.id(MAJOR));
		major.click();

		Utils.waitElement(driver, By.id(CHECKBOX), 5);
		List<AndroidElement> checkbox = driver.findElements(By.id(CHECKBOX));
		checkbox.get(4).click();
		Utils.sleep(1);
		
		WebElement dialog_save2 = driver.findElement(By.id(DIALOG_SAVE));
		dialog_save2.click();
		Utils.sleep(2);

		WebElement auth_btn = driver.findElement(By.id(AUTH_BTN));
		auth_btn.click();
		// 资格证上传
		quaUpload();
		
		log.info("上传头像");
    	uploadPhoto();
    	log.info("选择关注专业");
		selectMajor();
		//进入推荐医师页面
		recommendDoctor();
		Utils.waitElement(driver, By.id(IndexPageElements.INDEX_MY_BTN), 10);
		if(Utils.isElementExist(driver, By.id(IndexPageElements.INDEX_MY_BTN))) {
			log.info("完成认证！");
		}else {
			Assertion.assertEquals(true, false, "完成认证后未能跳转到首页or首页数据加载失败请检查！");
		}
		
	}
	
	/**
	 * 推荐关注标签
	 */
	public static void selectMajor() {
		
		//选择感兴趣的标签
		List<AndroidElement> select_major = driver.findElements(By.id(SUBSCRIBE_LABLE));
		select_major.get(0).click();
		Utils.sleep(1);
		
		WebElement select_major_next_btn = driver.findElement(By.id(LABLE_NEXT_BTN));
		select_major_next_btn.click();
		Utils.sleep(3);
	}
	
	/**
	 * 推荐医师
	 */
	public static void recommendDoctor() {
		log.info("跳过推荐医师");
		if(Utils.waitElement(driver, By.id(ATTENTION_DOCTOR), 10)) {
			WebElement skip_doctor = driver.findElement(By.id(SKIP_DOCTOR));
			skip_doctor.click();
//			List<AndroidElement> attention_doctor = driver.findElements(By.id(ATTENTION_DOCTOR));
//			attention_doctor.get(0).click();
//			Utils.sleep(2);
//			driver.findElement(By.id(FINISH_DOCTOR)).click();
		}
	}
	
	/**
	 * 资格证上传
	 */
	public static void quaUpload() {
		log.info("填写资格证信息");
		//选择证件类型
		if(Utils.waitElement(driver,By.xpath(IDENTITY_PAPERS), 5)){
			WebElement identity_papers = driver.findElement(By.xpath(IDENTITY_PAPERS));
			identity_papers.click();
		}else {
			driver.findElement(By.id(UNTH_TYPE)).click();
			Utils.sleep(1);
			WebElement identity_papers1 = driver.findElement(By.xpath(IDENTITY_PAPERS));
			identity_papers1.click();
		}
		//证件编号
		WebElement unth_phynumber = driver.findElement(By.id(UNTH_PHYNUMBER_INPUT));
		unth_phynumber.click();
		unth_phynumber.clear();
		unth_phynumber.sendKeys("cs741258963");
		Utils.sleep(2);
		
		// 上传照片
		WebElement unth_phyPhoto = driver.findElement(By.id(UNTH_PHYPHOTO));
		unth_phyPhoto.click();
		Utils.sleep(2);
		
		//相册选择
		List<AndroidElement> upload_confim = driver.findElements(By.id(UNLOAD_CONFIM));
		upload_confim.get(1).click();
		Utils.sleep(2);
		
		SAUPPhoto(1);
		
		log.info("提交证件");
		// 证件完成后提交
		WebElement auth_btn_card = driver.findElement(By.id(AUTH_BTN));
		auth_btn_card.click();
		Utils.sleep(3);
		
	}
	
	/**
	 * 上传头像
	 * @throws IOException 
	 */
	public static void uploadPhoto() {
		log.info("新用户注册添加头像");
		// 跳过上传头像
		// WebElement close_upload_photo = driver.findElement(By.id(CLOSE_UPLOAD_PHOTO));
		// close_upload_photo.click();
		// 上传头像
		WebElement upload_photo = driver.findElement(By.id(UPLOAD_PHOTO));
		upload_photo.click();
		Utils.sleep(1.5);
		
		SAUPPhoto(0);
		
		if (Utils.isElementExist(driver, By.id(PHOTO_NEXT))) {
			// 进入下一步
			WebElement photo_next = driver.findElement(By.id(PHOTO_NEXT));
			photo_next.click();
		}

	}
	/**
     * 未认证医师认证Flow
     */
	public static void noAuthDoctorCer() {
		if (Utils.isElementExist(driver, By.id(START_AUTH))) {
			WebElement start_auth = driver.findElement(By.id(START_AUTH));
			start_auth.click();
			Utils.sleep(2);
		}
		Utils.waitElement(driver, By.id(AUTH_NAME), 10);
		log.info("填写基本信息");
		// 姓名
		WebElement auth_name = driver.findElement(By.id(AUTH_NAME));
		String name = ReadWriteTxtFile.getLastName()+ReadWriteTxtFile.getFirstName();
		auth_name.sendKeys(name);
		Utils.sleep(1);

		// 年龄
		WebElement auth_age = driver.findElement(By.id(AGE));
		auth_age.click();
		Utils.sleep(1);
		auth_age.sendKeys("44");
		Utils.sleep(1);

		// 单位
		WebElement hospital = driver.findElement(By.id(COMPANY));
		hospital.click();
		Utils.sleep(1);

		List<AndroidElement> in_hospital = driver.findElements(By.id(HOSPITAL));
		in_hospital.get(1).click();
		WebElement search_hospital = driver.findElement(By.id(SEARCH_HOSPITAL));
		search_hospital.click();
		Utils.sleep(1);
		WebElement search_hospital_input = driver.findElement(By.id(SEARCH_HOSPITAL_INPUT));
		search_hospital_input.sendKeys("平凉地区疾病防控中心");

		if (Utils.waitElement(driver, By.id(HOSPITAL_ADD),5)) {
			driver.findElement(By.id(HOSPITAL_ADD)).click();
			Utils.sleep(1);
			driver.findElement(By.id(HOSPITAL_SAVE)).click();
		}else if (Utils.isElementExist(driver, By.id(HOSPITAL_LIST))){
			List<AndroidElement> hospital_list = driver.findElements(By.id(HOSPITAL_LIST));
			hospital_list.get(0).click();
		}

		Utils.sleep(1);
		//选择职称（住院医师，副教授，硕士生导师）
		WebElement job = driver.findElement(By.id(JOB));
		job.click();
		Utils.sleep(1);

		WebElement cb_resident = driver.findElement(By.id(CB_RESIDENT));
		cb_resident.click();
		Utils.sleep(1);

		WebElement dialog_save = driver.findElement(By.id(DIALOG_SAVE));
		dialog_save.click();
		Utils.sleep(1);

		//选择专业
		WebElement major = driver.findElement(By.id(MAJOR));
		major.click();
		Utils.sleep(1);

		List<AndroidElement> checkbox = driver.findElements(By.id(CHECKBOX));
		checkbox.get(4).click();
		Utils.sleep(1);

		WebElement dialog_save2 = driver.findElement(By.id(DIALOG_SAVE));
		dialog_save2.click();
		Utils.sleep(2);

		WebElement auth_btn = driver.findElement(By.id(AUTH_BTN));
		auth_btn.click();
		// 资格证上传
		quaUpload();

	}
	
	/**
	 * 三星A5上传照片选择
	 * @param num temp相册选择指定的照片（1-5）
	 */
	public static void SAUPPhoto(int num) {
		String time = "com.sec.android.gallery3d:id/drop_down_menu_button";
		String select_album = "com.sec.android.gallery3d:id/list_item_title"; //800 450

		//选择照片文件夹
		Utils.sleep(2);
		if(Utils.isElementExist(driver, By.id("com.allin.social:id/iv_arrow_drwn"))) {
			driver.findElement(By.id("com.allin.social:id/iv_arrow_drwn")).click();
			//选择文件夹
			Utils.waitElement(driver, By.id("com.allin.social:id/tv_directory_name"), 10);
			driver.swipe(500, 1000, 500, 350, 500);
			Utils.sleep(2);
			List<AndroidElement> temp = driver.findElements(By.id("com.allin.social:id/tv_directory_name"));
			for(int i=0; i<temp.size(); i++) {
				if(temp.get(i).getText().equals("temp")) {
					temp.get(i).click();
					break;
				}
			}
			Utils.sleep(1);
			//选择想要的照片
			List<AndroidElement> photo = driver.findElements(By.id("com.allin.social:id/iv_photoview"));		
			photo.get(num).click();
			Utils.sleep(1);
			if(Utils.isElementExist(driver, By.id("com.allin.social:id/tv_select"))) {
				//使用
				driver.findElement(By.id("com.allin.social:id/tv_select")).click();
			}else if(Utils.isElementExist(driver, By.id("com.allin.social:id/tv_publish"))) {
				//完成
				driver.findElement(By.id("com.allin.social:id/tv_publish")).click();
			}		
			Utils.sleep(1);
		}else if(Utils.isElementExist(driver, By.id("com.sec.android.gallery3d:id/drop_down_menu_button"))) {
			driver.tap(1, 130, 400, 200);
			Utils.sleep(2);
		}
		if(Utils.isElementExist(driver, By.id("com.sec.android.gallery3d:id/save"))) {
			//头像裁剪完成
			driver.findElement(By.id("com.sec.android.gallery3d:id/save")).click();
		}
		
	}
}
