/**
 * Nov 15, 2011 5:35:49 PM
 */
package com.cwa.gamecore.persistence.examples.dao;

import java.util.Date;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.cobar.client.entities.Offer;
import com.alibaba.cobar.client.support.vo.BatchInsertTask;
import com.alibaba.cobar.client.transaction.MultipleDataSourcesTransactionManager;
import com.cwa.gamecore.persistence.DbApplicationContext;

/**
 * @author Landu
 * @descirption 功能描述
 */
public class OffersDao {
        private static String namespace = "com.alibaba.cobar.client.entities.Offer.";
        private SqlMapClientTemplate sqlMapClientTemplate;

        /** 根据定义的路由进行单个sql语句的操作 */
        public long create(Offer offer) {
                return (Long) this.getSqlMapClientTemplate().insert(namespace + "create", offer);
        }

        /** 根据定义的路由批量insert数据到db中 */
        public int batchCreate(List<Offer> offers) {
                DbApplicationContext.getInstantce().getBatchExecuteDao().setSameByPrimaryValue(true);
                DbApplicationContext.getInstantce().getBatchExecuteDao().setSqlMapClientTemplate(this.getSqlMapClientTemplate());
                int result = DbApplicationContext.getInstantce().getBatchExecuteDao().batchInsert(namespace + "create", offers);
                return result;
        }

        /**
         * 批量操作建议采用这种方式:
         * 第二种批量操作db,sql语句写法如下：
         *<insert id="batchInsert" parameterClass="java.util.List">
         * INSERT INTO offers(memberId, subject, gmtUpdated) VALUES
         * <iterate conjunction=",">
         * (#[].memberId#,#[].subject#, #[].gmtUpdated#)
         * </iterate>
         * </insert>
         **/
        public void batchCreateBysql(List<Offer> offers) {
                BatchInsertTask task = new BatchInsertTask(offers);
                this.getSqlMapClientTemplate().insert(namespace + "batchInsert", task);
        }

        /** 从所有的分区中查询findAll：SELECT * FROM offer */
        public List<Offer> findAll() {
                return (List<Offer>) this.getSqlMapClientTemplate().queryForList(namespace + "findAll");
        }

        /** 范围里查找：SELECT * FROM offers where memberId < #value# */
        public List<Offer> findByMemberIdRange(long memberId) {
                return (List<Offer>) this.getSqlMapClientTemplate().queryForList(namespace + "findByMemberIdRange", memberId);
        }

        /** 排序查找：SELECT * FROM offers order by subject */
        public List<Offer> findAllWithOrderByOnSubject() {
                return (List<Offer>) this.getSqlMapClientTemplate().queryForList(namespace + "findAllWithOrderByOnSubject");
        }
        
      //测试事务
        public void multipleShardsWithTransactionRollback() {
//                List<DefaultTransactionStatus> list=new ArrayList<DefaultTransactionStatus>();
                 DefaultTransactionDefinition  transactionDefinition=new DefaultTransactionDefinition();
                 MultipleDataSourcesTransactionManager processor=DbApplicationContext.getInstance().getTransactionProcessor();
                 TransactionStatus status=processor.getTransaction(transactionDefinition);
//                try{
//                        Offer offer = new Offer();
//                        offer.setMemberId(79L);
//                        offer.setGmtUpdated(new Date());
//                        offer.setSubject("o1");
//                        getSqlMapClientTemplate().insert(
//                                "com.alibaba.cobar.client.entities.Offer.create", offer);
//
//                        offer = new Offer();
//                        offer.setMemberId(78L);
//                        offer.setGmtUpdated(new Date());
//                        offer.setSubject("o2");
//                        getSqlMapClientTemplate().insert(
//                                "com.alibaba.cobar.client.entities.Offer.create1", offer);     
//                }catch(Exception e){
//                        processor.rollback(status);
//                }
//                processor.commit(status);
                new TransactionTemplate(processor).execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {

                        try {
                            Offer offer = new Offer();
                            offer.setMemberId(81L);
                            offer.setGmtUpdated(new Date());
                            offer.setSubject("o1");
                            getSqlMapClientTemplate().insert(
                                    "com.alibaba.cobar.client.entities.Offer.create", offer);

                            offer = new Offer();
                            offer.setMemberId(83L);
                            offer.setGmtUpdated(new Date());
                            offer.setSubject("o2");
                            getSqlMapClientTemplate().insert(
                                    "com.alibaba.cobar.client.entities.Offer.create", offer);
                            throw new RuntimeException("exception to trigger rollback");
                        }finally {
                            status.setRollbackOnly();
                        }
                    }
                });
        }

        public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
                this.sqlMapClientTemplate = sqlMapClientTemplate;
        }

        public SqlMapClientTemplate getSqlMapClientTemplate() {
                return sqlMapClientTemplate;
        }
}
