package com.cwa.gamecore.util;

import com.cwa.gamecore.io.FastByteArrayInputStream;
import com.cwa.gamecore.io.FastByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CompressionUtil {
    
    // GZIP压缩
    public static byte[] gzip(byte[] data) {
        FastByteArrayOutputStream baos = new FastByteArrayOutputStream();
        try {
            GZIPOutputStream os = new GZIPOutputStream(baos);
            os.write(data);
            os.finish();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Fail to GZIP data.", e);
        }
    }
    
    // GZIP解压缩
    public static byte[] unGzip(byte[] data) {
        try {
            GZIPInputStream in = new GZIPInputStream(new FastByteArrayInputStream(data));
            return StreamUtil.drain(in);
        } catch (IOException e) {
            throw new RuntimeException("Fail to UnGZIP data.", e);
        }
    }
    
}
