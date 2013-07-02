package com.cwa.log.mongo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Pai
 * @descirption MongoDB存储管理类 12.12.24
 */
public class StorageManager {

	private String host;
	private int port;
	private String gameName;
	private int poolSize;
	private ConcurrentHashMap<String, Storage> pool;

	private static StorageManager _instance;

	private StorageManager(String host, int port, String gameName, int poolSize) {
		this.host = host;
		this.port = port;
		this.gameName = gameName;
		this.poolSize = poolSize;
		pool = new ConcurrentHashMap<String, Storage>();
	}

	public static StorageManager getInstance(String host, int port,
			String gameName, int poolSize) {
		if (_instance == null) {
			_instance = new StorageManager(host, port, gameName, poolSize);
		}
		return _instance;
	}

	/**
	 * 通过游戏名称获得对应的Log库。
	 * 
	 * @param
	 * @return
	 */
	public Storage getStorage() {
		if (!pool.contains(gameName)) {
			pool.putIfAbsent(gameName,
					Storage.getInstance(host, port, gameName, poolSize));
		}
		return pool.get(gameName);
	}

	public void saveStorage(String colName, String log) {
		Storage.getInstance(host, port, gameName, poolSize).save(colName, log);
	}
}
