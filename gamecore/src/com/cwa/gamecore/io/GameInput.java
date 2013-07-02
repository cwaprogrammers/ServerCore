package com.cwa.gamecore.io;

import java.util.Date;

/**
 * 这个接口抽象游戏输入流。服务器通过这个接口读取客户端发送的消息。
 */
public interface GameInput {
    
    /**
     * 返回剩余字节数。
     */
    public int remaining();
    
    
    /**
     * 从流中读取一个boolean值。
     */
    public boolean getBoolean();
    
    /**
     * 从流中读取一个byte值。
     */
    public byte get();
    
    /**
     * 从流中读取一个short值。
     */
    public short getShort();
    
    /**
     * 从流中读取一个int值。
     */
    public int getInt();
    
    /**
     * 从流中读取一个long值。
     */
    public long getLong();
    
    /**
     * 从流中读char。
     */
    public char getChar();
    
    /**
     * 从流中读float。
     */
    public float getFloat();
    
    /**
     * 从流中读double。
     */
    public double getDouble();
    
    /**
     * 从流中读取n个字节。
     */
    public byte[] getRawBytes(int n);
    
    /**
     * 从流中读取一个字节数组：
     *   1）先从流中读一个short新整数n
     *   2）再从流中连续读取n个字节
     */
    public byte[] getBytes();
    
    /**
     * 从流中读取一个GZIP压缩过的字节数组，解压缩，然后返回。
     */
    public byte[] getGzip();
    
    /**
     * 从流中读取一个字符串。
     */
    public String getString();
    
    /**
     * 从流中读日期。
     */
    public Date getDate();
}
