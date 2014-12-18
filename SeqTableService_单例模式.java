package com.yihu.account.service;

import com.coreframework.db.DB;
import com.coreframework.db.Sql;
import com.yihu.account.em.MyDatabaseEnum;

public class SeqTableService {
	public static final String CHARGEWATERSN="seq_acc_accountchargewater";//acc_accountchargewater表主键
	public static final String CARDSTATEWATERSN="seq_acc_membershipcardstatewater";//Acc_MembershipCardStateWater表主键
    private SeqTableService() {}  
    private static SeqTableService single=null;  
    public synchronized  static int getInstance(String name) throws Exception{  
         if (single == null) {    
             single = new SeqTableService();  
         }    
        return single.getSeqValue(name);  
    }  
    private int getSeqValue(String name) throws Exception{
    	Sql sql = new Sql("insert into "+name+"(createtime) values(getdate())");
    	return DB.me().insert(MyDatabaseEnum.boss, sql);
    }
}
