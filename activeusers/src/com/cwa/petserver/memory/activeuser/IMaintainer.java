/**
 * Author: zero
 * Created: 2012-8-22
 */
package com.cwa.petserver.memory.activeuser;

/**
 * 维护内存中活跃玩家容器的工具，将非活跃玩家移除。
 * @author zero
 */
public interface IMaintainer
{
	public void filter(ActiveUserPool pool);
}
