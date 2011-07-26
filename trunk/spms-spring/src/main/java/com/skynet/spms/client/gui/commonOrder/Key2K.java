package com.skynet.spms.client.gui.commonOrder;

import java.util.HashMap;
import java.util.Map;

public class Key2K {

	private static Map<String, String> BKMap = new HashMap<String, String>();
	private static Map<String, String> KBMap = new HashMap<String, String>();

	static {
		ContractIndexKey[] keys = ContractIndexKey.values();
		DirectiveBusinessType[] types = DirectiveBusinessType.values();
		for (int i = 0; i < types.length; i++) {
			BKMap.put(types[i].name(), keys[i].name());
			KBMap.put(keys[i].name(), types[i].name());
		}
	}

	public static String businessType2ContractKey(String key) {
		return BKMap.get(key);
	}

	public static String contractKey2BusinessType(String key) {
		return KBMap.get(key);
	}

}
