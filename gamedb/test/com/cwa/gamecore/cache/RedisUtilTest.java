package com.cwa.gamecore.cache;

import java.util.ArrayList;
import java.util.List;
import org.junit.Ignore;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.Sharded;

@Ignore
public class RedisUtilTest {

    public static void main(String[] args) {
        RedisUtil test = new RedisUtil();
        RedisUtil.storeString("u", "zoo23456");
        RedisUtil.storeString("u{fuck}", "zoo23456");
        // test.setData("carr{bar}", "carr54321");
        // System.out.println(test.getData("foo"));
        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        JedisShardInfo si = new JedisShardInfo("192.168.0.98", "192.168.0.98");
        // si.setPassword("foobared");
        shards.add(si);

        si = new JedisShardInfo("192.168.0.99", "192.168.0.99");
        // si.setPassword("foobared");
        shards.add(si);
        ShardedJedis jedis = new ShardedJedis(shards, Sharded.DEFAULT_KEY_TAG_PATTERN);
        jedis.set("a{fuck}", "bar");
        JedisShardInfo s1 = jedis.getShardInfo("a{fuck}");
        jedis.set("d{aick}", "bar1");
        JedisShardInfo s2 = jedis.getShardInfo("d{aick}");
        jedis.disconnect();
        System.err.println(RedisUtil.class.getCanonicalName());
    }
    
}
