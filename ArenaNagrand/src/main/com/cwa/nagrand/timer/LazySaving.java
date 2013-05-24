/**
 * 
 */
package com.cwa.nagrand.timer;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cwa.nagrand.db.DBSessionManager;
import com.cwa.nagrand.db.arena.ArenaDAO;
import com.cwa.nagrand.log.Log;
import com.cwa.nagrand.memory.Arena;
import com.cwa.nagrand.memory.ArenaManager;

/**
 * @author zero
 *
 */
public class LazySaving extends QuartzJobBean {
	private static boolean isStart = false;
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		if (isStart) 
			return;
		isStart = true;
		Log.debug("saving start.");
		doLogic();
		isStart = false;
	}
	
	private void doLogic() {
		Map<String, Arena> allArena = ArenaManager.INSTANCE.getAllArena();
		SqlSession session = DBSessionManager.INSTANCE.getSession();
		ArenaDAO dao = session.getMapper(ArenaDAO.class);
		for (Arena arena : allArena.values()) {
			try {
				dao.update(arena);
				session.commit();
			} catch (Exception e) {
				session.rollback();
				e.printStackTrace();
			}
		}
		session.close();
		Log.fatal("saving arena:" + allArena.size());
	}
}