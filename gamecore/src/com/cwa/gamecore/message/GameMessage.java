package com.cwa.gamecore.message;

import java.nio.charset.Charset;

/**
 * GameMessage将请求和响应结合在一起，并且定义了一些常量。
 */
public interface GameMessage extends GameRequest, GameResponse {
    
    /**
     * GameMessage的魔数。
     * 每个消息（无论请求还是响应）都以这两个字节开头，否则应该被视为错误，
     * 应该强制关闭游戏Session或者抛出异常。
     */
    public static final byte[] MAGIC_NUMBERS = { 0x23, 0x24 };
    
    /**
     * 编码、解码字符串的默认字符集。
     */
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    
    
    /**
     * GameCore预留的一些Command ID。
     */
    public static final int CMD_RSA_ACTION    =  1; // 请求经过RSA加密
    public static final int CMD_MD5_ACTION    =  2; // 请求包含MD5消息验证码
    public static final int CMD_SHA256_ACTION =  3; // 请求包含SHA256消息验证码
    public static final int CMD_SIGNED_ACTION =  4; // 响应经过服务器RSA签名
    public static final int CMD_BATCH_ACTION  = 10; // 处理批量请求
    public static final int CMD_GZIP_ACTION   = 11; // GZIP压缩请求
    
    // 调试命令
    public static final int DEBUG_EXEC_JS      =80; // 执行JavaScript脚本
    public static final int DEBUG_SLEEP_ACTION =81; // 模拟耗时操作
    
}
