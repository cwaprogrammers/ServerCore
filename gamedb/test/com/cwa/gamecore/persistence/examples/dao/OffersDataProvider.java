/**
 * Nov 16, 2011 10:25:20 AM
 */
package com.cwa.gamecore.persistence.examples.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.alibaba.cobar.client.entities.Offer;
import com.cwa.gamecore.context.AppContext;
import com.cwa.gamecore.persistence.DbApplicationContext;
/**
 * 
 * @author Landu
 * @descirption offerDAO
 */
public class OffersDataProvider {
        private static OffersDataProvider instance = null;
        private static OffersDao dao = (OffersDao)AppContext.getInstance().getApplicationContext().getBean("offersDao");
        
        
        public static OffersDataProvider getInstance() {
                if (instance == null)
                        instance = new OffersDataProvider();
                return instance;
        }
        //单条sql语句insert
        public long create(Offer offer){
                return dao.create(offer);
        }
        //批量insert处理
        public int batchCreate(List<Offer> offers){
              return  dao.batchCreate(offers);
        }
        //根据sql模版来批量insert
        public void batchCreateBysql(List<Offer> offers){
                dao.batchCreateBysql(offers);
        }
        
        //查询所有分区里的数据
        public List<Offer> findAll(){
                return dao.findAll();
        }
        //从范围里查找
        public List<Offer> findByMemberIdRange(long memberId){
                return dao.findByMemberIdRange(memberId);
        }
        //排序查找
        public List<Offer> findAllWithOrderByOnSubject(){
//                List<Offer> offers= dao.findAllWithOrderByOnSubject();
//                Collections.sort(offers, DbApplicationContext.getInstantce().getComparator());
//                return offers;
                return dao.findAllWithOrderByOnSubject();
         }
        
        //测试单条sql语句insert
        public void testCreate(){
                Offer offer = new Offer();
                offer.setGmtUpdated(new Date());
                offer.setMemberId(152L);
                offer.setSubject("some offer");
                offer.setBlobfield("clientcache".getBytes());
                this.create(offer); 
        }
        
        //测试批量insert处理
        public void testBatchCreate(){
//                Long[] memberIds  = new Long[] { 1L};
                List<Offer> offers = new ArrayList<Offer>();
//                for (Long mid : memberIds) {
                    Offer offer = new Offer();
                    offer.setGmtUpdated(new Date());
                    offer.setMemberId((long)23);
                    offer.setSubject("fake offer"+(long)23);
                    offers.add(offer);
                    
                    Offer offer2 = new Offer();
                    offer2.setGmtUpdated(new Date());
                    offer2.setMemberId((long)23);
                    offer2.setSubject("fake22 offer"+(long)23);
                    offers.add(offer2);
//                }
                int result=this.batchCreate(offers);
        }
        //sql模版批量insert
        public void testBatchCreateBysql(){
                Long[] memberIds  = new Long[] { 1L, 129L, 257L, 2L, 130L, 258L,386L};
                List<Offer> offers = new ArrayList<Offer>();
                for (Long mid : memberIds) {
                    Offer offer = new Offer();
                    offer.setGmtUpdated(new Date());
                    offer.setMemberId(mid);
                    offer.setSubject("fake offer"+(long)mid);
                    offers.add(offer);
                }
                this.batchCreateBysql(offers);
        }
        //测试findAll
        public void testFindAll(){
                List<Offer> offers=this.findAll();
                for(Offer offer:offers){
                        System.out.println(offer.getMemberId());
                }
        }
        
        //测试findByMemberIdRange
        public void testFindByMemberIdRange(){
                List<Offer> offers=this.findByMemberIdRange(300L);
                for(Offer offer:offers){
                        System.out.println(offer.getMemberId());
                }
        }
        
        //测试findAllWithOrderByOnSubject
        public void testFindAllWithOrderByOnSubject(){
                List<Offer> offers=this.findAllWithOrderByOnSubject();
                for(Offer offer:offers){
                        System.out.println(offer.getMemberId());
                }
        }
        
      //测试事务
        public void testOfferCreationOnMultipleShardsWithTransactionRollback() {
                dao.multipleShardsWithTransactionRollback();
        }
        
        
        public static void main(String[] args){
                OffersDataProvider provider=new OffersDataProvider();
                provider.testCreate();
        }
}
