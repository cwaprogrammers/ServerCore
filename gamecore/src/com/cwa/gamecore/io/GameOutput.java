package com.cwa.gamecore.io;

import java.util.Date;

/**
 * 这个接口抽象游戏输出流。服务器通过这个接口向客户端发送消息。
 */
public interface GameOutput {
    
    /**
     * 往流中写一个boolean值。
     */
    public void putBoolean(boolean b);
    
    /**
     * 往流中写一个int值。
     */
    public void put(byte b);
    
    /**
     * 往流中写一个short值。
     */
    public void putShort(short s);
    
    /**
     * 往流中写一个int值。
     */
    public void putInt(int i);
    
    /**
     * 往流中写一个long值。
     */
    public void putLong(long l);
    
    /**
     * 往流中写一个char值。
     */
    public void putChar(char value);
    
    /**
     * 往流中写一个float值。
     */
    public void putFloat(float value);
    
    /**
     * 往流中写一个double值。
     */
    public void putDouble(double value);
    
    /**
     * 将字节数组直接写入流中（不包含任何长度信息）。
     */
    public void putRawBytes(byte[] bytes);
    
    /**
     * 往流中写一个字节数组，假设字节数组的长度是n，那么：
     *   1）先将n以short型写入流中
     *   2）再将字节数组的n个字节依次写入流中
     */
    public void putBytes(byte[] bytes);
    
    /**
     * 先GZIP（用默认level）压缩字节数组，然后再写入流。
     */
    public void putGzip(byte[] bytes);
    
    /**
     * 往流中写一个字符串。
     */
    public void putString(String s);
    
    /**
     * 往流中写日期。
     */
    public void putDate(Date date);
    
}
