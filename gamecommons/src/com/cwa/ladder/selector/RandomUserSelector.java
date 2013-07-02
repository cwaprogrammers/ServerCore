package com.cwa.ladder.selector;

import com.cwa.ladder.Ladder;
import java.util.*;

/**
 * 随机选择排在挑战者前面的玩家以供挑战。
 * 假设天梯中有100个玩家，挑战者排第50名，则从1到49名中随机选择n个玩家。
 */
public class RandomUserSelector implements UserSelector {

    @Override
    public Map<Integer, Integer> select(Ladder ladder, int challenger, int n) {
        List<Integer> uidList = ladder.getUidList();
        int idxOfUser = uidList.indexOf(challenger);
        
        // 玩家不在天梯系统中
        if (idxOfUser < 0) {
            return Collections.EMPTY_MAP;
        }
        
        // 玩家已经是天梯第一名了
        if (idxOfUser == 0) {
            return Collections.EMPTY_MAP;
        }
        
        // 排在玩家前面的人很少
        if (idxOfUser <= n) {
            Map<Integer, Integer> map = new HashMap<Integer, Integer>();
            for (int i = 0; i < idxOfUser; i++) {
                map.put(uidList.get(i), i + 1);
            }
            return map;
        }
        
        // 随机选取
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        Random random = new Random();
        while (map.size() < n) {
            int randomIdx = random.nextInt(idxOfUser);
            int randomUID = uidList.get(randomIdx);
            map.put(randomUID, randomIdx + 1);
        }
        return map;
    }
    
}
