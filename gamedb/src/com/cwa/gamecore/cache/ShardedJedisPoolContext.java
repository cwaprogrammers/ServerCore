/**
 * Nov 23, 2011 5:01:10 PM
 */
package com.cwa.gamecore.cache;

import com.cwa.gamecore.context.AppContext;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @author Landu
 * @descirption 得到redis的context
 */
public class ShardedJedisPoolContext {
        private static ShardedJedisPoolContext instance = new ShardedJedisPoolContext();
        private ShardedJedisPool shardedJedisPool;

        private ShardedJedisPoolContext() {
                shardedJedisPool = (ShardedJedisPool) AppContext.getInstance().getApplicationContext().getBean("shardedJedisPool");
        }

        public static ShardedJedisPoolContext getInstance() {
                return instance;
        }

        public ShardedJedisPool getShardedJedisPool() {
                return this.shardedJedisPool;
        }

        public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
                this.shardedJedisPool = shardedJedisPool;
        }
}
