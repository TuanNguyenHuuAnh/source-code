package vn.com.unit.ep2p.service;

import java.util.ArrayList;
import java.util.List;

import vn.com.unit.ep2p.log.entity.LogDB;

public class TrackingContext {
	private static final ThreadLocal<List<LogDB>> tempDB = ThreadLocal.withInitial(ArrayList::new);
	
	public static List<LogDB> getTemp() {
		return tempDB.get();
	}
	
	public static void clearTempDB() {
		tempDB.get().clear();
	}
}