/**
 * Nov 23, 2011 4:47:41 PM
 */
package com.cwa.gamecore.context;

import com.cwa.gamecore.cache.ShardedJedisPoolContext;
import com.cwa.gamecore.persistence.DbApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author Landu
 * @descirption gamecore的核心启动及总控类
 */
public class AppContext {
        private static AppContext instance=new AppContext();
        /** spring的上下文*/
        private ApplicationContext applicationContext;
        
        private DbApplicationContext dbApplicationContext;
        
        private ShardedJedisPoolContext shardedJedisPoolContext;
        private AppContext(){
                setApplicationContext(new ClassPathXmlApplicationContext(new String[] { "config/root.xml" }));
        }
        public static AppContext getInstance(){
                return instance;
        }
        public void setApplicationContext(ApplicationContext applicationContext) {
                this.applicationContext = applicationContext;
        }
        public ApplicationContext getApplicationContext() {
                return applicationContext;
        }
        public DbApplicationContext getDbApplicationContext() {
                if(dbApplicationContext==null)
                        dbApplicationContext=DbApplicationContext.getInstance();
                return this.dbApplicationContext;
        }
        public void setDbApplicationContext(DbApplicationContext dbApplicationContext) {
                this.dbApplicationContext = dbApplicationContext;
        }
        
        public void setShardedJedisPoolContext(ShardedJedisPoolContext shardedJedisPoolContext) {
                this.shardedJedisPoolContext = shardedJedisPoolContext;
        }
        public ShardedJedisPoolContext getShardedJedisPoolContext() {
                if(shardedJedisPoolContext==null)
                        shardedJedisPoolContext=ShardedJedisPoolContext.getInstance();   
                return shardedJedisPoolContext;
        }

}
