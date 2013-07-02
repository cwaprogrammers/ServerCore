package com.cwa.ladder.selector;

import com.cwa.ladder.Ladder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 骐骥大航海项目的挑战者筛选器。
 * 假设玩家的名次是n：
 * 如果n属于[  1,100]，则选择n-1,n-2...
 * 如果n属于[101,200]，则选择n-2,n-3...
 * 以此类推，
 */
public class QijiUserSelector implements UserSelector {

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
        
        int gap = idxOfUser/100;
        int fromIdx = Math.max(0, idxOfUser - n - gap);
        int toIdx = idxOfUser - gap;
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = fromIdx; i < toIdx; i++) {
            map.put(uidList.get(i), i + 1);
        }
        return map;
    }
    
}
