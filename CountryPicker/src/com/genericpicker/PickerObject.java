package com.genericpicker;

import android.graphics.drawable.Drawable;

public class PickerObject {
	private String key;
	private String value;
	private Drawable icon;

	public String getKey() {
		return key;
	}

	public void setKey(String code) {
		this.key = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String name) {
		this.value = name;
	}
	
	public Drawable getIcon() {
		return icon;
	}
	
	public void setDrawable(Drawable icon) {
		this.icon = icon;
	}

}