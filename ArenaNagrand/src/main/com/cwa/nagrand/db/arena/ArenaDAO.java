/**
 * 
 */
package com.cwa.nagrand.db.arena;

import java.util.List;

import com.cwa.nagrand.memory.Arena;

/**
 * @author zero
 */
public interface ArenaDAO
{
	public List<String> getAllIds();
	public Arena get(String arenaId);
	public void add(Arena arena);
	public void update(Arena arena);
}
