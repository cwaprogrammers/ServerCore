/**
 * 2011-8-11 下午02:08:32
 */
package com.cwa.log.queue.service;

import com.cwa.log.task.Task;

/**
 * @author Pai
 * @descirption 
 * 12.12.24
 */
public class LogBatchTask implements Task {

        @Override
        public void run() {
               LogService.getInstance().storeLog();
        }

}
