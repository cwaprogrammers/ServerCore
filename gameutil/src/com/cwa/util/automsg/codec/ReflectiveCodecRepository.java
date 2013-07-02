package com.cwa.util.automsg.codec;

import java.util.HashMap;
import java.util.Map;

/**
 * Codec仓库，缓存Codec实例以避免每次创建。
 */
public class ReflectiveCodecRepository {
    
    private static final Map<Class<?>, ReflectiveCodec> codecMap;
    static {
        codecMap = new HashMap<Class<?>, ReflectiveCodec>();
    }
    
    public static ReflectiveCodec getCodec(Class<?> msgClass) {
        if (!codecMap.containsKey(msgClass)) {
            synchronized (codecMap) {
                if (!codecMap.containsKey(msgClass)) {
                    ReflectiveCodec codec = ReflectiveCodecBuilder.build(msgClass);
                    codecMap.put(msgClass, codec);
                }
            }
        }
        
        return codecMap.get(msgClass);
    }
    
}
