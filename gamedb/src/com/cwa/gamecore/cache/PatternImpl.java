/**
 * Nov 28, 2011 10:25:37 AM
 */
package com.cwa.gamecore.cache;

import java.util.regex.Pattern;

/**
 * 
 * @author Landu
 * @descirption pattern:key静态方法
 */
public class PatternImpl {
    
    private static final Pattern DEFAULT_PATTERN = Pattern.compile("\\{(.+?)\\}");
    
    public static Pattern getDefaultPattern(){
            return DEFAULT_PATTERN;
    }
    
}
