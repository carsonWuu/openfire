package com.sp.affair.data.analysis.function.deleteGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jivesoftware.openfire.plugin.bean.GroupBean;
import org.jivesoftware.openfire.plugin.bean.RecvBean;
import org.jivesoftware.openfire.plugin.bean.ReqBean;
import org.jivesoftware.openfire.plugin.bean.UserBean;



import com.sp.affair.data.analysis.function.factoryInter.Factory;



public class deleteGroup extends Factory{
	
	
	public deleteGroup(ReqBean req,List<GroupBean> grpList){
		this.act = req.getAct();
		
		this.master = req.getMaster();
		
		/*
		 * 将message中的u_list转换为list<member>形式
		 */
		
		this.u_list = req.getU_list();
		this.grp_id = req.getGrp_id();
		
		this.grpList = grpList;
		
		
	}
	
	@Override
	public String toString(){
		StringBuffer str= new StringBuffer();
		str.append(this.act);
		str.append(this.grp_id);
		str.append(this.u_list);
		str.append(this.master);
		return str.toString();
	}
	
	@Override
	public RecvBean doAction(){
		RecvBean ret ;
		
		cout();
				
		
		
		List<UserBean> addUserlist = this.u_list; //u_lsit
		List<GroupBean> addGrouplist = new ArrayList(); //groupANDmember
		
		/*
		 * 1.判断群号是否存在
		 * 2.判断操作者是否群主
		 */
		boolean groupOwner = false;//判断群号
		boolean masterExist = false ;//判断群主
//		List<GroupBean> groupList = this.data.groupandmember.list;//存放群组信息的集合
		for(int i = 0 ; i< this.grpList.size(); i++){
//			System.out.println(groupList.get(i).getGrpId()+" ?== "+this.grp_id);
			if(this.grpList.get(i).getGrpId().equals(this.grp_id)){//群号存在
				List<UserBean> userList =this.grpList.get(i).getUserList();
				for(int j = 0 ;j< userList.size();j++){
					if(userList.get(j).getType()==1){//群主
						if(userList.get(j).getU_id().equals(this.master)){//是群主，可以解散群组
							
							storeGroup = this.grpList.get(i);//将需要解散的群组信息保存，以备后面的处理
							
							this.grpList.remove(i);
							
							ret =  new RecvBean(0, "解散群组成功",this.grp_id);
							
							return ret;
						}
						else{//不是群主
							ret = new RecvBean(7, "权限不够，群主才可以解散群组", this.grp_id);
							return ret;
						}
					}
				}
				
				groupOwner = true;
				break;
			}
		}
		if(!groupOwner){
			ret = new RecvBean(2, "操作群/组ID不存在", this.grp_id);
			return ret;
		}

		
		ret =  new RecvBean(0, "解散群组成功",this.grp_id);
			
		return ret;
	}
	
	
}
