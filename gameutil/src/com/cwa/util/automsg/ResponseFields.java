package com.cwa.util.automsg;

import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 响应（输出）字段。
 */
@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseFields {
    
    String[] value();
    
}
