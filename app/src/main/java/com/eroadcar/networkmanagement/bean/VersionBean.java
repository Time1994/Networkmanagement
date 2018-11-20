package com.eroadcar.networkmanagement.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 升级bean
 * 
 * @author amos
 * 
 */
public class VersionBean implements Serializable {
	private String versionName;
	private String versionCode;
	private String apkLink;
	private String remark;

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getApkLink() {
		return apkLink;
	}

	public void setApkLink(String apkLink) {
		this.apkLink = apkLink;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
