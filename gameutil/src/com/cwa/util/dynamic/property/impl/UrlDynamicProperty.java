package com.cwa.util.dynamic.property.impl;

import com.cwa.util.dynamic.property.DynamicProperty;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 从URL中读取属性值。
 */
public class UrlDynamicProperty extends DynamicProperty {

    private URL url;
    
    public UrlDynamicProperty(String url) throws MalformedURLException {
        this.url = new URL(url);
    }
    
    public UrlDynamicProperty(URL url) {
        this.url = url;
    }
    
    @Override
    public String reload() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        try {
            return reader.readLine();
        } finally {
            reader.close();
        }
    }
    
}
