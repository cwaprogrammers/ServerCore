package com.cwa.log.queue.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cwa.log.queue.domain.BaseLog;
/**
 * @author Pai
 * @descirption 队列类
 * 12.12.24
 */

public class LogQueue {

	/**
	 * 两个列表进行切换工作 待修改
	 */
	private final List<BaseLog> firstList = Collections
			.synchronizedList(new ArrayList<BaseLog>(10000));

	private final List<BaseLog> secondList = Collections
			.synchronizedList(new ArrayList<BaseLog>(10000));
	/**
	 * 列表切换开关
	 */
	private volatile boolean swithFlag = false;

	/**
	 * 向队列中添加数据
	 * 
	 * @param obj
	 */
	public void add(BaseLog obj) {
		if (swithFlag)
			firstList.add(obj);
		else
			secondList.add(obj);
	}

	/**
	 * 获取待发送的队列，同时切换状态
	 * 
	 * @return
	 */
	public List<BaseLog> getSendList() {
		if (swithFlag) {
			swithFlag = false;
			return firstList;
		} else {
			swithFlag = true;
			return secondList;
		}
	}

}