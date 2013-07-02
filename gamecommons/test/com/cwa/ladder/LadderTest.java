package com.cwa.ladder;

import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.Test;

public class LadderTest {
    
    @Test
    public void addUserIntoLadder() {
        Ladder ladder = new Ladder(1);
        
        assertEquals(1, ladder.addUser(1001));
        assertEquals(2, ladder.addUser(2222));
        assertEquals(3, ladder.addUser(1024));
        assertEquals(3, ladder.getUidList().size());
        
        assertEquals(-1, ladder.addUser(2222));
        assertEquals(3, ladder.getUidList().size());
        
        assertEquals(Arrays.asList(1001, 2222, 1024), ladder.getUidList());
    }
    
    @Test
    public void removeUserFromLadder() {
        Ladder ladder = new Ladder(1);
        ladder.setUidList(Arrays.asList(1, 2, 3, 4));
        
        assertFalse(ladder.removeUser(100));
        assertEquals(Arrays.asList(1, 2, 3, 4), ladder.getUidList());
        
        assertTrue(ladder.removeUser(1));
        assertEquals(Arrays.asList(2, 3, 4), ladder.getUidList());
        
        assertTrue(ladder.removeUser(3));
        assertEquals(Arrays.asList(2, 4), ladder.getUidList());
        
        assertTrue(ladder.removeUser(4));
        assertEquals(Arrays.asList(2), ladder.getUidList());
        
        assertTrue(ladder.removeUser(2));
        assertTrue(ladder.getUidList().isEmpty());
        
        assertFalse(ladder.removeUser(2));
    }
    
    @Test
    public void getUserStep() {
        Ladder ladder = new Ladder(1);
        ladder.setUidList(Arrays.asList(1, 2, 3, 4, 5));
        
        assertEquals(1, ladder.getStep(1));
        assertEquals(2, ladder.getStep(2));
        assertEquals(3, ladder.getStep(3));
        assertEquals(4, ladder.getStep(4));
        assertEquals(5, ladder.getStep(5));
        
        assertEquals(-1, ladder.getStep(6));
        assertEquals(-1, ladder.getStep(100));
    }
    
    @Test
    public void swapUsersInLadder() {
        Ladder ladder = new Ladder(1);
        ladder.setUidList(Arrays.asList(1, 2, 3, 4, 5));
        
        assertFalse(ladder.swapUsers(100, 1));
        assertFalse(ladder.swapUsers(4, 25));
        assertFalse(ladder.swapUsers(0, 6));
        assertEquals(Arrays.asList(1, 2, 3, 4, 5), ladder.getUidList());
        
        assertTrue(ladder.swapUsers(1, 2));
        assertEquals(Arrays.asList(2, 1, 3, 4, 5), ladder.getUidList());
        assertTrue(ladder.swapUsers(1, 4));
        assertEquals(Arrays.asList(2, 4, 3, 1, 5), ladder.getUidList());
        assertTrue(ladder.swapUsers(5, 1));
        assertEquals(Arrays.asList(2, 4, 3, 5, 1), ladder.getUidList());
    }
    
    @Test
    public void ladderCapacity() {
        Ladder smallLadder = new Ladder(3, 5);
        
        smallLadder.addUser(1);
        smallLadder.addUser(2);
        smallLadder.addUser(3);
        smallLadder.addUser(4);
        smallLadder.addUser(5);
        
        try {
            smallLadder.addUser(6);
        } catch (RuntimeException expected) {
            assertEquals("ladder 3 is full.", expected.getMessage());
        }
    }
    
}
