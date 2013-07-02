package com.cwa.util.dynamic.property.impl;

import com.cwa.util.dynamic.property.DynamicProperty;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 从磁盘文件中读取属性值。
 */
public class FileDynamicProperty extends DynamicProperty {

    private File file;

    public FileDynamicProperty(File file) {
        this.file = file;
    }
    
    public FileDynamicProperty(String path) {
        this.file = new File(path);
    }

    @Override
    public String reload() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        try {
            return reader.readLine();
        } finally {
            reader.close();
        }
    }
    
}
