package com.dleistn1.grimdawnloottableeditor.services;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Daniel Leistner
 */
public class LocaleServiceImpl implements LocaleService {

	private final static Map<String, String> LOCALES = new HashMap<String, String>();
	private final static String KEY_NOT_FOUND_VALUE = "NOT_FOUND";

	public LocaleServiceImpl() {
		LOCALES.put(LocaleKeys.MESSAGE_RECORDS_LOADED, "%s Records loaded");
		LOCALES.put(LocaleKeys.MESSAGE_LOOTTABLE_CREATED, "Loottable with %s records written");
	}

	@Override
	public String get(String key) {
		return isKeyValid(key) ? LOCALES.get(key) : KEY_NOT_FOUND_VALUE;
	}

	@Override
	public String get(String key, Object... args) {
		return isKeyValid(key) ? String.format(LOCALES.get(key), args) : KEY_NOT_FOUND_VALUE;
	}

	private boolean isKeyValid(String key) {
		return LOCALES.containsKey(key);
	}
}
