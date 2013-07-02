package com.cwa.util.automsg.codec;

import com.cwa.util.automsg.RequestFields;
import com.cwa.util.automsg.ResponseFields;
import java.lang.reflect.Field;

public class ReflectiveCodecBuilder {
    
    // 根据注解构造Codec。
    public static ReflectiveCodec build(Class<?> msgClass) {
        ReflectiveCodec codec = new ReflectiveCodec();
        
        RequestFields reqFieldsAnno = msgClass.getAnnotation(RequestFields.class);
        if (reqFieldsAnno != null) {
            for (String fieldSpec : reqFieldsAnno.value()) {
                codec.addRequestField(parseSpec(msgClass, fieldSpec));
            }
        }
        
        ResponseFields respFieldsAnno = msgClass.getAnnotation(ResponseFields.class);
        if (respFieldsAnno != null) {
            for (String fieldSpec : respFieldsAnno.value()) {
                codec.addResponseField(parseSpec(msgClass, fieldSpec));
            }
        }
        
        return codec;
    }
    
    private static ReflectiveField parseSpec(Class<?> msgClass, String fieldSpec) {
        // field1 -> field2
        if (fieldSpec.contains("->")) {
            String[] specs = fieldSpec.split("->");
            ReflectiveField field = parseSpec(msgClass, specs[0]);
            field.setDependency(parseSpec(msgClass, specs[1]));
            return field;
        }
        
        // field
        try {
            Field field = msgClass.getDeclaredField(fieldSpec.trim());
            return new ReflectiveField(field);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Field not found:" + fieldSpec, e);
        }
    }
    
}
