package com.cwa.gamecore.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * 文件操作工具类。
 */
public class FileUtil {
    
    private static final Logger logger = Logger.getLogger(FileUtil.class);
    
    /**
     * 把文件内容读到一个字符串列表中。
     */
    public static List<String> readLines(File file) throws IOException {
        final List<String> lines = new ArrayList<String>();
        
        read(file, new LineHandler() {
            @Override public void handle(String line) {
                lines.add(line);
            }
        });
        
        return lines;
    }
    
    /**
     * 把文件内容读到一个字符串中。
     */
    public static String readString(File file) throws IOException {
        final StringBuilder buf = new StringBuilder();
        
        read(file, new LineHandler() {
            @Override public void handle(String line) {
                buf.append(line).append("\n");
            }
        });
        
        return buf.toString();
    }
    
    public static void read(File file, LineHandler lineHandler) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                lineHandler.handle(line);
            }
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                logger.error("Can not close BufferedReader.", e);
            }
        }
    }
    
    public static interface LineHandler {
        public void handle(String line);
    }
    
}
