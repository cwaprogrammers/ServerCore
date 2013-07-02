package com.cwa.ladder.selector;

import com.cwa.ladder.Ladder;
import java.util.Map;

/**
 * 玩家筛选算法。用来从天梯中选出几个玩家以供挑战者挑战。
 */
public interface UserSelector {
    
    /**
     * 从天梯中选择n个玩家以供挑战者挑战。
     * @param ladder
     * @param challenger
     * @param n
     * @return Map<uid, ladderStep>
     */
    public Map<Integer, Integer> select(Ladder ladder, int challenger, int n);
    
}
