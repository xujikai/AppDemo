package com.xjk.android.utils;

import java.util.ArrayList;
import java.util.List;

public class DataUtils {

	public static List<String> getData(){
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			list.add("aaa");
		}
		return list;
	}
	
	public static List<String> getData(int size){
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < size; i++) {
			list.add("aaa");
		}
		return list;
	}

	public static List<String> getAlarmData(int size){
		List<String> list = new ArrayList<String>();
		for (int i = 1; i <= size; i++) {
			list.add("铃声" + i);
		}
		return list;
	}

}
