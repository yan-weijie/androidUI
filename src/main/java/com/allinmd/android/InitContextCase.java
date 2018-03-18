package com.allinmd.android;

import org.testng.annotations.Test;

import com.allinmd.file.DelLogScreenShot;
import com.allinmd.httpclient.CleanResetPasswordData;

public class InitContextCase {
	private DelLogScreenShot del = new DelLogScreenShot();
	private CleanResetPasswordData cleanData = new CleanResetPasswordData();
	// private static Logger log = Logger.getLogger(InitContextCase.class);
	
	/**
	 * 删除日志、截图等临时文件
	 */
	@Test (priority = 1)
	public void delTempFile() {
		del.delFile();
	}
	
	/**
	 * 清除测试账号的验证码已发送记录
	 */
	@Test (priority = 2)
	public void cleanData() {
		cleanData.cleanData();
	}
	
}
