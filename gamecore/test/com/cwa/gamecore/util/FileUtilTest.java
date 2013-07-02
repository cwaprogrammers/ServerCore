package com.cwa.gamecore.util;

import java.io.File;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class FileUtilTest {
    
    @Test
    public void readString() throws Exception {
        File file = new File(ClassLoader.getSystemResource("log4j.xml").toURI());
        String context = FileUtil.readString(file);
        assertThat(context.isEmpty(), is(false));
    }
    
    @Test
    public void readLines() throws Exception {
        File file = new File(ClassLoader.getSystemResource("log4j.xml").toURI());
        List<String> lines = FileUtil.readLines(file);
        assertThat(lines.size(), is(19));
    }
    
}
