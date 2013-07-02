/**
 * Nov 14, 2011 3:52:13 PM
 */
package com.cwa.gamecore.persistence;

import com.alibaba.cobar.client.CobarSqlMapClientDaoSupport;
import com.alibaba.cobar.client.transaction.MultipleDataSourcesTransactionManager;
import com.cwa.gamecore.context.AppContext;
import java.util.Comparator;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

/**
 * @author Landu
 * @descirption db处理context
 */
public class DbApplicationContext {
        private static DbApplicationContext instance = new DbApplicationContext();
        /** spring的上下文*/
        private ApplicationContext applicationContext;
        /** 非批量操作的sql模版处理bean*/
        private SqlMapClientTemplate sqlMapClientTemplate;
        /** 批量执行处理的dao*/
        private CobarSqlMapClientDaoSupport batchExecuteDao;
        /** 排序时的比较类*/
        Comparator comparator;
        /** 事务处理器：尽量不要用事务*/
        private MultipleDataSourcesTransactionManager transactionProcessor;

        private DbApplicationContext() {
                applicationContext =AppContext.getInstance().getApplicationContext();
                //setSqlMapClientTemplate((SqlMapClientTemplate) applicationContext.getBean("sqlMapClientTemplate"));
                batchExecuteDao = new CobarSqlMapClientDaoSupport();
                //comparator=(Comparator)applicationContext.getBean("comparator");
                //transactionProcessor=(MultipleDataSourcesTransactionManager) (applicationContext.getBean("transactionManager"));
        }

        public static DbApplicationContext getInstantce() {
                return instance;
        }

        public void setApplicationContext(ApplicationContext applicationContext) {
                this.applicationContext = applicationContext;
        }

        public ApplicationContext getApplicationContext() {
                return applicationContext;
        }

        public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
                this.sqlMapClientTemplate = sqlMapClientTemplate;
        }

        public SqlMapClientTemplate getSqlMapClientTemplate() {
                return sqlMapClientTemplate;
        }

        public CobarSqlMapClientDaoSupport getBatchExecuteDao() {
                return this.batchExecuteDao;
        }

        public void setBatchExecuteDao(CobarSqlMapClientDaoSupport batchExecuteDao) {
                this.batchExecuteDao = batchExecuteDao;
        }

        public Comparator getComparator() {
                return this.comparator;
        }

        public void setComparator(Comparator comparator) {
                this.comparator = comparator;
        }

        public static DbApplicationContext getInstance() {
                return instance;
        }

        public static void setInstance(DbApplicationContext instance) {
                DbApplicationContext.instance = instance;
        }

        public MultipleDataSourcesTransactionManager getTransactionProcessor() {
                return this.transactionProcessor;
        }

        public void setTransactionProcessor(MultipleDataSourcesTransactionManager transactionProcessor) {
                this.transactionProcessor = transactionProcessor;
        }
}
