/**
 * May 16, 2012 11:28:32 AM
 */
package com.cwa.minaserver;

/**
 * 
 * @author Landu
 * @descirption 静态变量
 */
public class GameCoreConstants {
    public final static int READ_BUFFER_SIZE = 2048 * 500;   //发送缓冲区的大小:1M
    public final static int RECEIVE_BUFFER_SIZE = 2048 * 500;//接收缓冲区的大小:1M
    public final static int MSG_ENCODE_BUFFER_SIZE = 2048;   //编码缓冲区的大小:2k
    public final static int MAX_MSG_DATA_SIZE = 2048 * 4;    //校验缓冲区的大小:8k
}
