package com.cwa.config.csv;

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

public class CsvLoader {
    
    private InputStream csvSrc;
    private File csvFile;
    private Charset charset;
    private int skipLines = CSVReader.DEFAULT_SKIP_LINES;
    private Class<?> beanClass;
    private String[] columnMapping;
    
    // 设置CSV文件
    public CsvLoader setCsvFile(File csvFile) {
        return setCsvFile(csvFile, Charset.forName("utf-8"));
    }
    
    // 设置CSV文件
    public CsvLoader setCsvFile(File csvFile, Charset charset) {
        if (csvSrc != null) {
            throw new RuntimeException("csvSrc has been set!");
        }
        this.csvFile = csvFile;
        this.charset = charset;
        return this;
    }
    
    public CsvLoader setCsvSrc(InputStream csvSrc) {
        return this.setCsvSrc(csvSrc, Charset.forName("utf-8"));
    }
    
    public CsvLoader setCsvSrc(InputStream csvSrc, Charset charset) {
        if (csvFile != null) {
            throw new RuntimeException("csvFile has been set!");
        }
        this.csvSrc = csvSrc;
        this.charset = charset;
        return this;
    }
    
    // 跳过开始的第n行数据
    public CsvLoader setSkipLines(int skipLines) {
        this.skipLines = skipLines;
        return this;
    }
    
    // JavaBean class
    public CsvLoader setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
        return this;
    }
    
    // 设置CSV列和JavaBean属性名的对应关系
    public CsvLoader setColumnMapping(String[] columnMapping) {
        this.columnMapping = columnMapping;
        return this;
    }
    
    public <T> List<T> load() {
        if (beanClass == null) {
            throw new RuntimeException("beanClass must to be set!");
        }
        return (List<T>) load(beanClass);
    }
    
    private <T> List<T> load(Class<T> beanClass) {
        ColumnPositionMappingStrategy<T> strat = new ColumnPositionMappingStrategy<T>();
        strat.setType(beanClass);
        
        if (columnMapping == null) {
            throw new RuntimeException("columnMapping must to be set!");
        }
        strat.setColumnMapping(columnMapping);
        
        if (csvFile == null && csvSrc == null) {
            throw new RuntimeException("csvFile or csvSrc must to be set!");
        }
        
        if (skipLines < 0) {
            throw new RuntimeException("skipLines < 0 !");
        }
        
        InputStream is = null;
        try {
            is = csvSrc != null ? csvSrc : new FileInputStream(csvFile);
            CSVReader reader = new CSVReader(new InputStreamReader(is, charset),
                    CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, skipLines);
            CsvToBean<T> csv = new CsvToBean<T>();
            return csv.parse(strat, reader);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("CSV File not found!", e);
        } finally {
            closeInputStream(is);
        }
    }
    
    private void closeInputStream(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException("Cannot close InputStream!", e);
            }
        }
    }
    
    // 工厂方法
    public static CsvLoader newLoader() {
        return new CsvLoader();
    }
    
}
