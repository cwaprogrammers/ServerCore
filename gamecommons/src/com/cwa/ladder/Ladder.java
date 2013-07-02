package com.cwa.ladder;

import java.util.ArrayList;
import java.util.List;

/**
 * 天梯系统。
 */
public class Ladder {
    
    private int ladderId; // 天梯ID
    private int capacity; // 天梯能容纳的玩家数量
    private List<Integer> uidList; // 天梯中的玩家ID列表

    // 默认能容纳2000个玩家
    public Ladder(int ladderId) {
        this(ladderId, 2000); 
    }

    public Ladder(int ladderId, int capacity) {
        this.ladderId = ladderId;
        this.capacity = capacity;
        uidList = new ArrayList<Integer>();
    }

    public int getLadderId() {
        return ladderId;
    }

    public void setLadderId(int ladderId) {
        this.ladderId = ladderId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Integer> getUidList() {
        return uidList;
    }

    public void setUidList(List<Integer> uidList) {
        this.uidList.clear();
        this.uidList.addAll(uidList);
    }
    
    /**
     * 把玩家加到天梯末尾，返回玩家在天梯中的排名。
     * @param uid 玩家ID
     * @return -1 如果玩家已经在天梯中
     */
    public int addUser(int uid) {
        if (uidList.size() >= capacity) {
            throw new RuntimeException("ladder " + ladderId + " is full.");
        }
        
        if (uidList.contains(uid)) {
            // 玩家已经在天梯中了
            return -1;
        }
        
        uidList.add(uid);
        return uidList.size();
    }
    
    /**
     * 把玩家从天梯里删除，返回是否删除成功。
     * @param uid
     * @return 
     */
    public boolean removeUser(int uid) {
        return uidList.remove((Integer) uid);
    }
    
    /**
     * 返回玩家在天梯中的排名，排名从1开始。
     * @return -1 如果玩家不在天梯里
     */
    public int getStep(int uid) {
        int idxOfUser = uidList.indexOf(uid);
        if (idxOfUser < 0) {
            // 玩家不在天梯中
            return idxOfUser;
        }
        
        // 排名从1开始
        return idxOfUser + 1;
    }
    
    /**
     * 交换两个玩家在天梯中的名次，返回是否交换成功。
     * @param uidA
     * @param uidB 
     * @return false 如果其中一个玩家（或两个玩家都）不在天梯中
     */
    public boolean swapUsers(int uidA, int uidB) {
        int idxOfA = uidList.indexOf(uidA);
        if (idxOfA < 0) {
            // uidA不在天梯中
            return false;
        }
        
        int idxOfB = uidList.indexOf(uidB);
        if (idxOfB < 0) {
            // uidB不在天梯中
            return false;
        }
        
        uidList.set(idxOfA, uidB);
        uidList.set(idxOfB, uidA);
        
        return true;
    }
    
}
