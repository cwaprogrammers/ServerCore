package com.cwa.ladder.selector;

import com.cwa.ladder.Ladder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 固定选择排在挑战者前面的玩家以供挑战。
 * 假设天梯中有100个玩家，挑战者排第50名，选择5名玩家，则选出排名第49到45名的玩家。
 */
public class FixedUserSelector implements UserSelector {

    @Override
    public Map<Integer, Integer> select(Ladder ladder, int challenger, int n) {
        List<Integer> uidList = ladder.getUidList();
        int idxOfUser = ladder.getUidList().indexOf(challenger);
        
        // 玩家不在天梯系统中
        if (idxOfUser < 0) {
            return Collections.EMPTY_MAP;
        }
        
        // 玩家已经是天梯第一名了
        if (idxOfUser == 0) {
            return Collections.EMPTY_MAP;
        }
        
        int fromIdx = Math.max(0, idxOfUser - n);
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = fromIdx; i < idxOfUser; i++) {
            map.put(uidList.get(i), i + 1);
        }
        return map;
    }
    
}
