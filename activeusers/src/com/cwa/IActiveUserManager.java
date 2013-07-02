package com.cwa;

import java.util.List;

/**
 * 对外使用的活跃玩家随机抓取工具接口。
 */
public interface IActiveUserManager {
    
    /**
     * 玩家上线，更新其内存池中的数据。
     *
     * @param roleId
     * @param level
     */
    public void userOnline(int roleId, int level);
    
    /**
	 * 玩家离线，更新内存池中的数据 
     * 
	 * @param roleId
	 * @param level
	 */
    public void userOffline(int roleId, int level);
    
    /**
	 * 指定一个等级和人数，随机对应的一个玩家列表出来。
     * 
	 * @param amount	指定随机的数量
	 * @param level 	指定的随机玩家等级
	 * @return 		返回随机出来的玩家Id的列表，如果没有，返回0元素的列表。
	 */
    public List<Integer> randomUsers(int amount, int level);
    
    /**
     * 指定一个等级和人数amount，尽最大可能找到amount个玩家。
     * 会优先在指定的等级中找，如果找不够则在等级±1中找，
     * 还不够则在等级±1中找，以此类推。
     * 
     * @param amount
     * @param level
     * @return 
     */
    public List<Integer> randomUsersAroundLevel(int amount, int level);
    
}
