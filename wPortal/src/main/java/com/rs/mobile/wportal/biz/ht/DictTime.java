package com.rs.mobile.wportal.biz.ht;

public class DictTime {
	public DictTime(String systemDictionaryID, String dictType, String dictKey, String dictValue) {
		super();
		SystemDictionaryID = systemDictionaryID;
		DictType = dictType;
		DictKey = dictKey;
		DictValue = dictValue;
	}

	private String SystemDictionaryID;
	private String DictType;
	private String DictKey;
	private String DictValue;

	public String getSystemDictionaryID() {
		return SystemDictionaryID;
	}

	public void setSystemDictionaryID(String systemDictionaryID) {
		SystemDictionaryID = systemDictionaryID;
	}

	public String getDictType() {
		return DictType;
	}

	public void setDictType(String dictType) {
		DictType = dictType;
	}

	public String getDictKey() {
		return DictKey;
	}

	public void setDictKey(String dictKey) {
		DictKey = dictKey;
	}

	public String getDictValue() {
		return DictValue;
	}

	public void setDictValue(String dictValue) {
		DictValue = dictValue;
	}

}
