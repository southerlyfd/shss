package com.jianghongbo.common;

import java.beans.PropertyEditorSupport;

import com.jianghongbo.common.util.StringUtil;

/**
 * @ClassName: MyStringTrimmerEditor.java
 * @Description: 类说明
 * @author: jianghb
 * @date: 2019年4月12日
 */
public class MyStringTrimmerEditor extends PropertyEditorSupport {

	private final String charsToDelete;

	private final boolean emptyAsNull;


	/**
	 * Create a new StringTrimmerEditor.
	 * @param emptyAsNull <code>true</code> if an empty String is to be
	 * transformed into <code>null</code>
	 */
	public MyStringTrimmerEditor(boolean emptyAsNull) {
		this.charsToDelete = null;
		this.emptyAsNull = emptyAsNull;
	}

	/**
	 * Create a new StringTrimmerEditor.
	 * @param charsToDelete a set of characters to delete, in addition to
	 * trimming an input String. Useful for deleting unwanted line breaks:
	 * e.g. "\r\n\f" will delete all new lines and line feeds in a String.
	 * @param emptyAsNull <code>true</code> if an empty String is to be
	 * transformed into <code>null</code>
	 */
	public MyStringTrimmerEditor(String charsToDelete, boolean emptyAsNull) {
		this.charsToDelete = charsToDelete;
		this.emptyAsNull = emptyAsNull;
	}


	@Override
	public void setAsText(String text) {
		if (text == null) {
			setValue(null);
		}
		else {
			String value = text.trim();
			if (this.charsToDelete != null) {
				value = StringUtil.deleteAny(value, this.charsToDelete);
			}
			if (this.emptyAsNull && "".equals(value)) {
				setValue(null);
			}
			else {
				
				// 加入字符过滤替换
				value = StringUtil.replaceHtmlCh(value);
				
				setValue(value);
			}
		}
	}

	@Override
	public String getAsText() {
		Object value = getValue();
		return (value != null ? value.toString() : "");
	}

}
