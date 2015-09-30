package com.alex.smartwomanmiddleeastfem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class Local {
	public static SharedPreferences sharedPreferences;
	public static Editor editor;

	public static SharedPreferences sharedPreferencesfirst;
	public static Editor editorfirst;

	public static SharedPreferences sharedPreferenceschat;
	public static Editor editorchat;

	@SuppressLint("CommitPrefEdits")
	public static void save(Context c, String key, String value) {

		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);

		editor = sharedPreferences.edit();

		editor.putString(key, value);

		editor.commit();

	}

	public static String fetch(Context c, String key) {
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);

		return sharedPreferences.getString(key, "");

	}

	@SuppressLint("CommitPrefEdits")
	public static void saveChatStatus(Context c, String key, String value) {

		sharedPreferencesfirst = PreferenceManager
				.getDefaultSharedPreferences(c);

		editorfirst = sharedPreferencesfirst.edit();

		editorfirst.putString(key, value);

		editorfirst.commit();

	}

	public static String fetchChatStatus(Context c, String key) {
		sharedPreferencesfirst = PreferenceManager
				.getDefaultSharedPreferences(c);

		return sharedPreferencesfirst.getString(key, "");

	}

	@SuppressLint("CommitPrefEdits")
	public static void saveChat(Context c, String key, String value) {

		sharedPreferenceschat = PreferenceManager
				.getDefaultSharedPreferences(c);

		editorchat = sharedPreferenceschat.edit();

		editorchat.putString(key, value);

		editorchat.commit();

	}

	public static String fetchChat(Context c, String key) {
		sharedPreferenceschat = PreferenceManager
				.getDefaultSharedPreferences(c);

		return sharedPreferenceschat.getString(key, "");

	}

}
