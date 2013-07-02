package com.cwa.util.automsg;

import com.cwa.util.automsg.AutoMessage;
import com.cwa.util.automsg.RequestFields;
import com.cwa.util.automsg.ResponseFields;

@RequestFields( {"bool", "byteInt", "shortInt", "intInt", "longInt", "str", "bytes"})
@ResponseFields({"bool", "byteInt", "shortInt", "intInt", "longInt", "str", "bytes"})
public class SimpleMessage extends AutoMessage {

    private boolean bool;
    private byte byteInt;
    private short shortInt;
    private int intInt;
    private long longInt;
    private String str;
    private byte[] bytes;
    
    public SimpleMessage() {
        super(101);
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public byte getByteInt() {
        return byteInt;
    }

    public void setByteInt(byte byteInt) {
        this.byteInt = byteInt;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public int getIntInt() {
        return intInt;
    }

    public void setIntInt(int intInt) {
        this.intInt = intInt;
    }

    public long getLongInt() {
        return longInt;
    }

    public void setLongInt(long longInt) {
        this.longInt = longInt;
    }

    public short getShortInt() {
        return shortInt;
    }

    public void setShortInt(short shortInt) {
        this.shortInt = shortInt;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
    
}
