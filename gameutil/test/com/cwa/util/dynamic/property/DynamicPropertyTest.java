package com.cwa.util.dynamic.property;

import com.cwa.util.dynamic.property.impl.FileDynamicProperty;
import com.cwa.util.dynamic.property.impl.UrlDynamicProperty;
import java.io.File;
import org.junit.Assert;
import org.junit.Test;

public class DynamicPropertyTest {
    
    @Test
    public void urlProperty() throws Exception {
        UrlDynamicProperty urlProperty = new UrlDynamicProperty("http://www.baidu.com");
        urlProperty.refresh();
        Assert.assertTrue(urlProperty.getValue().contains("百度"));
    }
    
    @Test
    public void fileProperty() throws Exception {
        File testFile = new File(ClassLoader.getSystemResource("./test.txt").toURI());
        FileDynamicProperty fileProperty = new FileDynamicProperty(testFile);
        fileProperty.refresh();
        Assert.assertEquals("cwa", fileProperty.getValue());
    }
    
}
