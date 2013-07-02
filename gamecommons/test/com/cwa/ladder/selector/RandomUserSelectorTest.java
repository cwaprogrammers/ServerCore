package com.cwa.ladder.selector;

import com.cwa.ladder.Ladder;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class RandomUserSelectorTest {
    
    @Test
    public void select() {
        Ladder ladder = new Ladder(1);
        ladder.setUidList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        
        UserSelector selector = new RandomUserSelector();
        
        // user not in ladder
        assertThat(selector.select(ladder, 100, 10), is(Collections.EMPTY_MAP));
        
        // user is NO.1
        assertThat(selector.select(ladder, 1, 10), is(Collections.EMPTY_MAP));
        
        // not enough users
        assertThat(selector.select(ladder, 2, 5), is(Collections.singletonMap(1, 1)));
        
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1, 1);
        map.put(2, 2);
        assertThat(selector.select(ladder, 3, 5), is(map));
        
        // random users
        assertThat(selector.select(ladder, 9, 5).size(), is(5));
        assertThat(selector.select(ladder, 9, 5).containsKey(9), is(false));
    }
    
}
