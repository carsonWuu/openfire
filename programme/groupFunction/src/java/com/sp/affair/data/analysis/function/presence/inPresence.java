package com.sp.affair.data.analysis.function.presence;

import java.util.List;

import org.jivesoftware.openfire.plugin.bean.GroupBean;
import org.jivesoftware.openfire.plugin.bean.RecvBean;
import org.jivesoftware.openfire.plugin.bean.ReqBean;

import com.sp.affair.data.analysis.function.factoryInter.Factory;

public class inPresence extends Factory{
	public inPresence(ReqBean req,List<GroupBean> grpList){
		this.act = req.getAct();
		
		this.u_id = req.getU_id();
		
		this.grp_id = req.getGrp_id();
		
		this.grpList = grpList;
		
	}
	@Override
	public String toString(){
		StringBuffer str= new StringBuffer();
		str.append(this.act);
		str.append(this.u_id);
		str.append(this.grp_id);
		return str.toString();
	}
	
	@Override
	public RecvBean doAction() {
		// TODO Auto-generated method stub
		String msg= "";
		if(this.act == 1)msg= "出席群组";
		else{
			msg="离席群组";
		}
		RecvBean ret =new RecvBean(0,msg,this.grp_id);
		for(GroupBean groupBean :grpList){
			if(groupBean.getGrpId().equals(this.grp_id)){//find the grp_id
				this.storeGroup = groupBean ;
				break;
			}
		}
		return ret;
	}
}
