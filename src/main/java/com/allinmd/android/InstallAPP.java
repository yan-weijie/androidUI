package com.allinmd.android;

import org.testng.annotations.Test;

import com.allinmd.util.MonitorApp;

public class InstallAPP {
	private MonitorApp installApp = new MonitorApp();
	@Test
	public void InstallApp(){
		// 安装AndroidApp
		installApp.monitorAllinmdAndroidApp();
	}

}
