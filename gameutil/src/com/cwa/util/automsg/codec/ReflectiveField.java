package com.cwa.util.automsg.codec;

import com.cwa.gamecore.io.GameInput;
import com.cwa.gamecore.io.GameOutput;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类封装Java的Field，不过也提供了Field缓存功能，可以提高性能。
 */
public class ReflectiveField {
    
    private final Field field;
    private final Class<?> fieldClass;
    private final Class<?> elemClass; // 仅对List字段有效
    
    // 该字段依赖的字段，目前只能依赖boolean类型的字段
    private ReflectiveField dependency;
    
    public ReflectiveField(Field field) {
        this.field = field;
        this.fieldClass = field.getType();
        this.field.setAccessible(true);
        if (List.class.isAssignableFrom(fieldClass)) {
            elemClass = getListElemType(field);
        } else {
            elemClass = null;
        }
    }
    
    private static Class<?> getListElemType(Field listField) {
        Type type = listField.getGenericType();
        
        if (type instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) type;
            Type[] types = paramType.getActualTypeArguments();
            return (Class<?>) types[0];
        }
        
        throw new RuntimeException("Can not get element's type of list " + listField.getName());
    }

    public ReflectiveField getDependency() {
        return dependency;
    }

    public void setDependency(ReflectiveField dependency) {
        this.dependency = dependency;
    }
    
    public boolean getBoolean(Object msg) {
        try {
            return field.getBoolean(msg);
        } catch (Exception e) {
            throw new RuntimeException("Fail to read " + field.getName() + " from GameInput.", e);
        }
    }

    // 从输入流中读一个对象
    public void get(GameInput in, Object msg) {
        try {
            if (fieldClass == boolean.class) {
                field.setBoolean(msg, in.getBoolean());
            } else if (fieldClass == byte.class) {
                field.setByte(msg, in.get());
            } else if (fieldClass == short.class) {
                field.setShort(msg, in.getShort());
            } else if (fieldClass == int.class) {
                field.setInt(msg, in.getInt());
            } else if (fieldClass == long.class) {
                field.setLong(msg, in.getLong());
            } else if (fieldClass == String.class) {
                field.set(msg, in.getString());
            } else if (fieldClass == byte[].class) {
                field.set(msg, in.getBytes());
            } else if (List.class.isAssignableFrom(fieldClass)) {
                field.set(msg, getList(in));
            } else {
                throw new RuntimeException("Unknown field type: " + fieldClass);
            }
        } catch (InstantiationException e) {
            throw new RuntimeException("Fail to read " + field.getName() + " from GameInput.", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Fail to read " + field.getName() + " from GameInput.", e);
        }
    }
    
    // 往输出流中写一个对象
    public void put(GameOutput out, Object msg) {
        try {
            if (fieldClass == boolean.class) {
                out.putBoolean(field.getBoolean(msg));
            } else if (fieldClass == byte.class) {
                out.put(field.getByte(msg));
            } else if (fieldClass == short.class) {
                out.putShort(field.getShort(msg));
            } else if (fieldClass == int.class) {
                out.putInt(field.getInt(msg));
            } else if (fieldClass == long.class) {
                out.putLong(field.getLong(msg));
            } else if (fieldClass == String.class) {
                out.putString((String) field.get(msg));
            } else if (fieldClass == byte[].class) {
                out.putBytes((byte[]) field.get(msg));
            } else if (List.class.isAssignableFrom(fieldClass)) {
                putList(out, (List<?>) field.get(msg));
            } else {
                throw new RuntimeException("Unknown field type: " + fieldClass);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Fail to write " + field.getName() + " into GameOutput.", e);
        }
    }
    
    // 从输入流中读一个List
    public List<?> getList(GameInput in) throws InstantiationException, IllegalAccessException {
        int size = in.getShort();
        List<Object> list = new ArrayList<Object>(size);
        
        if (size > 0) {
            ReflectiveCodec codec = ReflectiveCodecRepository.getCodec(elemClass);
            for (int i = 0; i < size; i++) {
                Object obj = elemClass.newInstance();
                codec.decodeBody(in, obj);
                
                list.add(obj);
            }
        }
        
        return list;
    }
    
    // 往输出流中写一个List
    public void putList(GameOutput out, List<?> list) {
        out.putShort((short) list.size());
        if (list.size() > 0) {
            ReflectiveCodec codec = ReflectiveCodecRepository.getCodec(elemClass);
            for (Object obj : list) {
                codec.encodeBody(out, obj);
            }
        }
    }
    
    @Override
    public String toString() {
        return "AutoMsgField{" + "field=" + field.getName() + '}';
    }
    
}
