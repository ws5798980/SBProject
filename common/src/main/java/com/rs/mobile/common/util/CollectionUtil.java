package com.rs.mobile.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CollectionUtil {

	/**
	 * 提取List<Map<String, String>>中的指定某个字段，转化为List
	 */
	public static List<String> listMapToList(List<Map<String, String>> data, String mapField) {
		List<String> resultList = new ArrayList<String>();
		try {
			for (Map<String, String> line : data) {
				resultList.add(line.get(mapField));
			}
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

		return resultList;
	}

	/**
	 * JSONArray数据转成List<Map<String, String>>
	 */
	public static List<Map<String, String>> jsonArrayToListMap(JSONArray arrayData) {
		List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < arrayData.length(); i++) {
			try {
				Object element = arrayData.get(i);
				// System.out.println(element.getClass().getName());
				if (element instanceof JSONObject) {
					Map<String, String> map = jsonObjectToMap((JSONObject) element);
					if (map != null) {
						returnList.add(map);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		}

		return returnList;
	}

	/**
	 * JSONArray数据转成List<Map<String, String>>
	 */
	public static List<Map<String, Object>> jsonArrayToListMapObject(JSONArray arrayData) {
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < arrayData.length(); i++) {
			try {
				Object element = arrayData.get(i);
				// System.out.println(element.getClass().getName());
				if (element instanceof JSONObject) {
					Map<String, Object> map = jsonObjectToMap1((JSONObject) element);
					if (map != null) {
						returnList.add(map);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		}

		return returnList;
	}

	/**
	 * JSONObject数据转成Map<String, String>
	 */
	public static Map<String, String> jsonObjectToMap(JSONObject objectData) {
		Map<String, String> returnMap = new HashMap<String, String>();
		Iterator<String> keys = objectData.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			try {
				Object value = objectData.get(key);
				returnMap.put(key, value.toString());
			} catch (JSONException e) {
				return null;
			}
		}

		return returnMap;
	}

	/**
	 * JSONObject数据转成Map<String, String>
	 */
	public static Map<String, Object> jsonObjectToMap1(JSONObject objectData) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Iterator<String> keys = objectData.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			try {
				Object value = objectData.get(key);
				returnMap.put(key, value);
			} catch (JSONException e) {
				return null;
			}
		}

		return returnMap;
	}

	/**
	 * JSONObject数据转成Map<String, String>
	 */
	public static Map<String, String> jsonObjectToMap_String(JSONObject objectData) {
		Map<String, String> returnMap = new HashMap<String, String>();
		Iterator<String> keys = objectData.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			try {
				Object value = objectData.get(key);
				returnMap.put(key, value.toString());
			} catch (JSONException e) {
				return null;
			}
		}

		return returnMap;
	}

	/**
	 * JSONObject数据转成Map<String, String>
	 */
	public static Map<String, Object> jsonObjectToMap001(JSONObject objectData) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Iterator<String> keys = objectData.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			try {
				Object value = objectData.get(key);
				returnMap.put(key, value.toString());
			} catch (JSONException e) {
				return null;
			}
		}

		return returnMap;
	}

	public static Map<String, String> createStringMap(String... stringObjects) {
		return createStringMap(false, stringObjects);
	}

	public static Map<String, String> createStringMapInOrder(String... stringObjects) {
		return createStringMap(true, stringObjects);
	}

	/**
	 * 按key, value, key, value的方式来创建一个Map<String, String>
	 * key必须是String类型，如果是null值或者空字符串，则忽略这一对key/value。
	 * value必须为String类型，可以为空字符串。如果是null值，则忽略这一对key/value。 如果key值重复，会出现值覆盖的情况。
	 * 
	 * @param isOrderly
	 *            创建的Map是否保持原有顺序
	 * @param stringObjects
	 * @return 如果出任何错误，返回null
	 */
	private static Map<String, String> createStringMap(boolean isOrderly, String... stringObjects) {
		if (stringObjects.length < 2 || stringObjects.length % 2 != 0) {
			// System.out.println("参数个数小于2或者参数个数不为偶数，退出。。。");
			return null;
		}
		Map<String, String> returnMap = null;
		try {
			if (isOrderly) {
				returnMap = new LinkedHashMap<String, String>();
			} else {
				returnMap = new HashMap<String, String>();
			}
			for (int i = 0; i < stringObjects.length - 1; i += 2) {
				if (StringUtil.isEmpty(stringObjects[i])) {
					// 如果key是null值或者空字符串，则忽略这一对key/value
					continue;
				}
				if (stringObjects[i + 1] == null) {
					// 可以value为空字符串。如果是null值，则忽略这一对key/value
					continue;
				}

				returnMap.put(stringObjects[i], stringObjects[i + 1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (returnMap != null) {
				returnMap.clear();
				returnMap = null;
			}
		}

		return returnMap;
	}

	public static Map<String, Object> createMap(Object... objects) {
		return createMap(false, objects);
	}

	public static Map<String, Object> createMapInOrder(Object... objects) {
		return createMap(true, objects);
	}

	/**
	 * 按key, value, key, value的方式来创建一个Map<String, Object>
	 * key必须是String类型，如果是null值或者不是非空字符串，则忽略这一对key/value
	 * 如果value是null值，则忽略这一对key/value。 如果key值重复，会出现值覆盖的情况。
	 * 
	 * @param isOrderly
	 *            创建的Map是否保持原有顺序
	 * @param objects
	 * @return 如果出任何错误，返回null
	 */
	private static Map<String, Object> createMap(boolean isOrderly, Object... objects) {
		if (objects.length < 2 || objects.length % 2 != 0) {
			// System.out.println("参数个数小于2或者参数个数不为偶数，退出。。。");
			return null;
		}
		Map<String, Object> returnMap = null;
		try {
			if (isOrderly) {
				returnMap = new LinkedHashMap<String, Object>();
			} else {
				returnMap = new HashMap<String, Object>();
			}
			for (int i = 0; i < objects.length - 1; i += 2) {
				if (objects[i] == null || "".equals(objects[i])) {
					// 如果key是null值或者不是非空字符串，则忽略这一对key/value
					continue;
				}
				if (objects[i + 1] == null) {
					// 如果value是null值，则忽略这一对key/value
					continue;
				}

				returnMap.put((String) objects[i], objects[i + 1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (returnMap != null) {
				returnMap.clear();
				returnMap = null;
			}
		}

		return returnMap;
	}
}
