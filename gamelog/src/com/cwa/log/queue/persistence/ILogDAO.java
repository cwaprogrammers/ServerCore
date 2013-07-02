/**
 * 2011-8-11 下午03:49:25
 */
package com.cwa.log.queue.persistence;

import java.util.Queue;

import com.cwa.log.queue.domain.BaseLog;

/**
 * @author Pai
 * 
 * 12.12.24
 */
public interface ILogDAO {
        public void batchSave(Queue<BaseLog> logQueue);
}
