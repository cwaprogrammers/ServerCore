package com.cwa.gamecore.util;

import java.util.List;
import org.junit.Ignore;

@Ignore
public class ClassFinderTest {

    @SuppressWarnings("rawtypes")
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException {
        List<Class> retList = ClassFinder.findClasses("com.cwa", "Message");
        for (Class clazz : retList) {
            System.out.println(clazz.getName() + ":" + clazz.getSuperclass());
        }

        System.err.println("res:" + Thread.currentThread().getContextClassLoader().getResource(""));
        System.err.println("res:" + ClassLoader.getSystemResource("org/apache/mina/transport/socket/nio/NioSocketAcceptor.class"));
    }
    
}
