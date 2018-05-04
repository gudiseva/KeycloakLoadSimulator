package com.kodiak.utils;

import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

public class MapDBUtil {


	public static DB getDB() {
		
		DB db = DBMaker
		        .fileDB("file.db")
		        .fileMmapEnable()
		        .make();
		
		return db;
	}

	public static ConcurrentMap<Integer, String> getMap(DB db) {
		
		ConcurrentMap<Integer, String> map = db
		        .hashMap("map", Serializer.INTEGER, Serializer.STRING)
		        .createOrOpen();
		
		return map;
	}
	
	public static void clearMap(ConcurrentMap<Integer, String> map) {
		
		map.clear();
	}
	
	public static void closeDB(DB db) {
		db.close();
	}
	
}
