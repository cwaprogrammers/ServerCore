/**
 * Nov 22, 2011 4:30:48 PM
 */
package com.cwa.gamecore.cache.example;

import org.junit.Ignore;
import redis.clients.jedis.Jedis;

/**
 * 
 * @author Landu
 * @descirption 功能描述
 */
@Ignore
public class SimpleTest {
        public void testFoo(){
                Jedis jedis = new Jedis("192.168.0.98");
                jedis.set("foo", "bar");
                String value = jedis.get("foo");
                System.out.println(value);
        }

        /**
         * @param args
         * @author landu
         * @description 方法说明
         */
        public static void main(String[] args) {
                SimpleTest test=new SimpleTest();
                test.testFoo();

        }

}
