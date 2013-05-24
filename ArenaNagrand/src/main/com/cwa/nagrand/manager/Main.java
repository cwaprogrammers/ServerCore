/**
 * 
 */
package com.cwa.nagrand.manager;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.cwa.gamecore.context.AppContext;
import com.cwa.nagrand.db.DBSessionManager;
import com.cwa.nagrand.db.arena.ArenaDAO;
import com.cwa.nagrand.log.Log;
import com.cwa.nagrand.memory.Arena;
import com.cwa.nagrand.memory.ArenaManager;

/**
 * @author zero
 *
 */
public class Main
{
	public static void main(String args[])
	{
	    Log.debug("Initialize Spring...");
	    AppContext.getInstance();
	    Log.debug("Start Mina Server...");
	    
	    // load all arenas
	    SqlSession session = DBSessionManager.INSTANCE.getSession();
	    ArenaDAO arenaDAO = session.getMapper(ArenaDAO.class);
	    List<String> allIds = arenaDAO.getAllIds();
	    for (String arenaId : allIds) {
	    	Arena arena = arenaDAO.get(arenaId);
	    	arena.setArenaId(arenaId);
	    	ArenaManager.INSTANCE.addArena(arena);
	    }
	    session.close();
	}
}