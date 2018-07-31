package com.sp.affair.data.analysis.function.createGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jivesoftware.openfire.plugin.bean.GroupBean;
import org.jivesoftware.openfire.plugin.bean.RecvBean;
import org.jivesoftware.openfire.plugin.bean.ReqBean;
import org.jivesoftware.openfire.plugin.bean.UserBean;




import com.sp.affair.data.analysis.function.factoryInter.Factory;
import com.sp.data.data.cacheDATA;
import com.sp.message.returnMessage.returnMessage;

/*
 * 创建群组
 */
public class createGroup extends Factory{
	
	public createGroup(ReqBean req,List<GroupBean> grpList){
		this.act = req.getAct();
		
		this.master = req.getMaster();
		
		/*
		 * 将message中的u_list转换为list<member>形式
		 */
		
		this.u_list = req.getU_list();

		
		
		this.alias = req.getAlias();
		
		this.grpList = grpList;
		
		this.cachedata =cacheDATA.getInstance();
		
	}
	@Override
	public String toString(){
		StringBuffer str= new StringBuffer();
		str.append("act: "+this.act+" , ");
		str.append("u_list: "+this.u_list+" , ");
		str.append("master: "+this.master+" , ");
		str.append("alias: "+this.alias+"\n");
		return str.toString();
	}
	
	@Override
	public RecvBean doAction(){
		RecvBean ret ;
		
		cout();
				
		boolean owner=false;
		
		
		List<UserBean> addUserlist = this.u_list; //u_lsit
		List<GroupBean> addGrouplist = new ArrayList(); //groupANDmember
			
		
		/*
		 * 创建群号，唯一
		 *新建群号。命名为：创建人+当前年月日时分秒（如：user1_20180116151159）是唯一标识
		 */
		
		String groupID = createGroupID();
		
		storeGroup =new GroupBean(groupID,this.alias,this.u_list,this.master);
		
//		this.grpList.add(storeGroup);
		this.cachedata.addGroup(storeGroup);
		ret =  new RecvBean(0, "创建群组成功",groupID);
			
		return ret;
	}
	public String createGroupID(){
		return this.master+"_"+getTime();
	}
		
			
			
	
	
}
