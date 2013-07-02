package com.cwa.config;

import com.cwa.config.csv.CsvLoader;
import java.io.File;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CsvLoaderTest {
    
    private File csvFile;
    
    @Before
    public void initCsvFile() throws Exception {
        csvFile = new File(CsvLoaderTest.class.getResource("/test.csv").toURI());
        assertNotNull(csvFile);
    }
    
    @Test
    public void loadCsvFail_missingCsvFileOrCsvSrc() throws Exception {
        try {
            CsvLoader.newLoader()
                    //.setCsvFile(csvFile)
                    .setSkipLines(1)
                    .setBeanClass(User.class)
                    .setColumnMapping(new String[] {"name", "age"})
                    .load();
            fail("没抛异常！");
        } catch (RuntimeException e) {
            assertEquals("csvFile or csvSrc must to be set!", e.getMessage());
        }
    }
    
    @Test
    public void loadCsvFail_missingBeanClass() throws Exception {
        try {
            CsvLoader.newLoader()
                    .setCsvFile(csvFile)
                    .setSkipLines(1)
                    //.setBeanClass(User.class)
                    .setColumnMapping(new String[] {"name", "age"})
                    .load();
            fail("没抛异常！");
        } catch (RuntimeException e) {
            assertEquals("beanClass must to be set!", e.getMessage());
        }
    }
    
    @Test
    public void loadCsvFail_missingColumnMapping() throws Exception {
        try {
            CsvLoader.newLoader()
                    .setCsvFile(csvFile)
                    .setSkipLines(1)
                    .setBeanClass(User.class)
                    //.setColumnMapping(new String[] {"name", "age"})
                    .load();
            fail("没抛异常！");
        } catch (RuntimeException e) {
            assertEquals("columnMapping must to be set!", e.getMessage());
        }
    }
    
    @Test
    public void loadCsvFail_badSkipLines() throws Exception {
        try {
            CsvLoader.newLoader()
                    .setCsvFile(csvFile)
                    .setSkipLines(-1)
                    .setBeanClass(User.class)
                    .setColumnMapping(new String[] {"name", "age"})
                    .load();
            fail("没抛异常！");
        } catch (RuntimeException e) {
            assertEquals("skipLines < 0 !", e.getMessage());
        }
    }
    
    @Test
    public void loadCsvOK() throws Exception {
        List<User> users = CsvLoader.newLoader()
                .setCsvFile(csvFile)
                .setSkipLines(1)
                .setBeanClass(User.class)
                .setColumnMapping(new String[] {"name", "age"})
                .load();
        
        assertEquals(3, users.size());
        User zxh = users.get(2);
        assertEquals("zxh", zxh.getName());
        assertEquals(31, zxh.getAge());
    }
    
}
