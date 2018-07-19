package org.jivesoftware.openfire.plugin.util;

import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

public class GsonUtil {
	public static Gson gson = new Gson();
	
	/**
	 * 避免泛型在编译期类型被擦除
	 * @param <T>
	 * @param json json字符串
	 * @param type 需要转成的集合实体类数组.class  例: UserBean[].class
	 * @return
	 */
	public static <T> List<T> getListFromJSON(String json, Class<T[]> type) {
		T[] list = GsonUtil.gson.fromJson(json, type);
		return Arrays.asList(list);
	}
}
