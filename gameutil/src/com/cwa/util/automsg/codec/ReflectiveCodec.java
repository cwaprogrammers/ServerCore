package com.cwa.util.automsg.codec;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.io.GameOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * 利用Java反射实现对象编码、解码。
 */
public class ReflectiveCodec {
    
    private final List<ReflectiveField> requestFields;
    private final List<ReflectiveField> responseFields;
    
    public ReflectiveCodec() {
        requestFields = new ArrayList<ReflectiveField>();
        responseFields = new ArrayList<ReflectiveField>();
    }
    
    public void addRequestField(ReflectiveField field) {
        requestFields.add(field);
    }
    
    public void addResponseField(ReflectiveField field) {
        responseFields.add(field);
    }
    
    // 解码对象
    public void decodeBody(GameInput in, Object msg) {
        for (ReflectiveField field : requestFields) {
            if (field.getDependency() != null) {
                if (field.getDependency().getBoolean(msg) == false) {
                    continue;
                }
            }
            field.get(in, msg);
        }
    }
    
    // 编码对象
    public void encodeBody(GameOutput out, Object msg) {
        for (ReflectiveField field : responseFields) {
            if (field.getDependency() != null) {
                if (field.getDependency().getBoolean(msg) == false) {
                    continue;
                }
            }
            field.put(out, msg);
        }
    }
    
}
