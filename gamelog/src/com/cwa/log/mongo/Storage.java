package com.cwa.log.mongo;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.util.JSON;

/**
 * @author Pai
 * @descirption MongoDB存储类 12.12.24
 */

public class Storage {
	private final static Logger logger = LoggerFactory.getLogger(Storage.class);
	private Mongo mongo;
	private DB db;
	private Map<String, DBCollection> collections;

	private static Storage _instance;

	private Storage(String host, int port, String dbName, int poolSize) {
		try {
			this.mongo = new Mongo(host, port);
			this.db = mongo.getDB(dbName);
			this.collections = new HashMap<String, DBCollection>();
			MongoOptions opt = mongo.getMongoOptions();
			opt.autoConnectRetry = true;
			opt.connectionsPerHost = poolSize;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public static Storage getInstance(String host, int port, String dbName,
			int poolSize) {
		if (_instance == null) {
			_instance = new Storage(host, port, dbName, poolSize);
		}
		return _instance;
	}

	/**
	 * 获取collection，若没有，新建一个
	 * 
	 * @param colName
	 * @return
	 */

	private DBCollection getCol(String colName) {
		DBCollection col = collections.get(colName);
		if (null == col) {
			col = db.getCollection(colName);
			collections.put(colName, col);
//			logger.info("gamelog try create collection");
		}
//		logger.error("gamelog collection" + colName);
		return col;
	}

	public void save(String colName, String json) {
		DBCollection col = getCol(colName);
		// 只有插入操作，没有更新操作
		DBObject dbObject = (DBObject) JSON.parse(json);
		col.insert(dbObject);
//		logger.error("gamelog write in mongodb");
	}
}
