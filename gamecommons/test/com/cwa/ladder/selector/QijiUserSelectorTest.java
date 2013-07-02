package com.cwa.ladder.selector;

import com.cwa.ladder.Ladder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class QijiUserSelectorTest {
    
    private Ladder ladder;
    private UserSelector selector;
    
    @Before
    public void initLadderWith2kUsers() {
        selector = new QijiUserSelector();
        
        // 天梯中有2000个玩家，排名从1到512
        ladder = new Ladder(1);
        ladder.setUidList(new ArrayList<Integer>());
        for (int i = 0; i < 2000; i++) {
            ladder.getUidList().add(i + 1);
        }
    }
    
    @Test
    public void challengerIsNotInLadder() {
        assertThat(selector.select(ladder, 2001, 5), is(Collections.EMPTY_MAP));
    }
    
    @Test
    public void challengerIsNo1() {
        assertThat(selector.select(ladder, 1, 5), is(Collections.EMPTY_MAP));
    }
    
    @Test
    public void challengerIsNo2() {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1, 1);
        assertThat(selector.select(ladder, 2, 5), is(map));
    }
    
    @Test
    public void challengerIsNo3() {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1, 1);
        map.put(2, 2);
        assertThat(selector.select(ladder, 3, 5), is(map));
    }
    
    @Test
    public void challengerIsNo5() {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(4, 4);
        assertThat(selector.select(ladder, 5, 5), is(map));
    }
    
    @Test
    public void challengerIsNo6() {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(4, 4);
        map.put(5, 5);
        assertThat(selector.select(ladder, 6, 5), is(map));
    }
    
    @Test
    public void challengerIsNo7() {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(2, 2);
        map.put(3, 3);
        map.put(4, 4);
        map.put(5, 5);
        map.put(6, 6);
        assertThat(selector.select(ladder, 7, 5), is(map));
    }
    
    @Test
    public void challengerIsNo100() {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(95, 95);
        map.put(96, 96);
        map.put(97, 97);
        map.put(98, 98);
        map.put(99, 99);
        assertThat(selector.select(ladder, 100, 5), is(map));
    }
    
    @Test
    public void challengerIsNo101() {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(95, 95);
        map.put(96, 96);
        map.put(97, 97);
        map.put(98, 98);
        map.put(99, 99);
        assertThat(selector.select(ladder, 100, 5), is(map));
    }
    
    @Test
    public void challengerIsNo200() {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(194, 194);
        map.put(195, 195);
        map.put(196, 196);
        map.put(197, 197);
        map.put(198, 198);
        assertThat(selector.select(ladder, 200, 5), is(map));
    }
    
    @Test
    public void challengerIsNo256() {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(249, 249);
        map.put(250, 250);
        map.put(251, 251);
        map.put(252, 252);
        map.put(253, 253);
        assertThat(selector.select(ladder, 256, 5), is(map));
    }
    
    @Test
    public void challengerIsNo2000() {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1980, 1980);
        map.put(1979, 1979);
        map.put(1978, 1978);
        map.put(1977, 1977);
        map.put(1976, 1976);
        assertThat(selector.select(ladder, 2000, 5), is(map));
    }
    
}
