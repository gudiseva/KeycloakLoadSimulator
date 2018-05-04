package com.kodiak.samples;

import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;

import com.kodiak.utils.MapDBUtil;

public class Test {

	public Test() {

	}

	public static void main(String[] args) {
		
		DB db = MapDBUtil.getDB();
		ConcurrentMap<Integer, String> map = MapDBUtil.getMap(db);
		
		MapDBUtil.clearMap(map);
		
		System.out.println(map.size());
		System.out.println(map.get(2));

		MapDBUtil.closeDB(db);
	}

}
//