package com.cwa.ladder.selector;

import com.cwa.ladder.Ladder;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class FixedUserSelectorTest {
    
    @Test
    public void select() {
        Ladder ladder = new Ladder(1);
        ladder.setUidList(Arrays.asList(1, 2, 3, 6, 5, 4, 7, 8, 9));
        
        UserSelector selector = new FixedUserSelector();
        
        // user not in ladder
        assertThat(selector.select(ladder, 100, 10), is(Collections.EMPTY_MAP));
        
        // user is NO.1
        assertThat(selector.select(ladder, 1, 10), is(Collections.EMPTY_MAP));
        
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1, 1);
        assertThat(selector.select(ladder, 2, 5), is(map));
        
        map = new HashMap<Integer, Integer>();
        map.put(1, 1);
        map.put(2, 2);
        assertThat(selector.select(ladder, 3, 5), is(map));
        
        map = new HashMap<Integer, Integer>();
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(6, 4);
        assertThat(selector.select(ladder, 5, 5), is(map));
        
        map = new HashMap<Integer, Integer>();
        map.put(3, 3);
        map.put(6, 4);
        map.put(5, 5);
        map.put(4, 6);
        map.put(7, 7);
        assertThat(selector.select(ladder, 8, 5), is(map));
    }
    
}
