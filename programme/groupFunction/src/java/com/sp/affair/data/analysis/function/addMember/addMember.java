package com.sp.affair.data.analysis.function.addMember;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jivesoftware.openfire.plugin.bean.GroupBean;
import org.jivesoftware.openfire.plugin.bean.RecvBean;
import org.jivesoftware.openfire.plugin.bean.ReqBean;
import org.jivesoftware.openfire.plugin.bean.UserBean;






import com.sp.affair.data.analysis.function.factoryInter.Factory;
import com.sp.data.data.cacheDATA;


public class addMember extends Factory{
	public addMember(ReqBean req){
		this.act = req.getAct();
		
		this.grp_id = req.getGrp_id();
		this.u_id = req.getU_id();
		this.master = req.getMaster();
			
		data = cacheDATA.getInstance();
		
	}
	@Override
	public String toString(){
		StringBuffer str= new StringBuffer();
		str.append(this.act);
		str.append(this.grp_id);
		str.append(this.u_id);
		str.append(this.master);
		return str.toString();
	}
	
	@Override
	public RecvBean doAction(){
		RecvBean ret = null ;
				
				
		/*①判断该群组存在
		 *②判断master在该群组中
		 *③判断u_id是否在该群
		 */
		
		boolean groupOwner = false;//判断群号
		boolean masterExist = false ;//判断操作者存在
		boolean u_idExist = false;//判断需添加用户不存在
		List<GroupBean> groupList = this.data.groupandmember.list;//存放群组信息的集合
		for(int i = 0 ; i< groupList.size(); i++){
//			System.out.println(groupList.get(i).getGrpId()+" ?== "+this.grp_id);
			if(groupList.get(i).getGrpId().equals(this.grp_id)){//群号存在
				
				List<UserBean> userList =groupList.get(i).getUserList();
				for(int j = 0 ;j< userList.size();j++){
//					if(userList.get(j).getType()==1){//群主
					
						if(userList.get(j).getU_id().equals(this.master)){//master在群组中，可以添加新用户进群组
							masterExist = true;
							
						}
						if(userList.get(j).getU_id().equals(this.u_id)){//master在群组中，可以添加新用户进群组
							u_idExist = true;
						}
				}
				
				if(masterExist){//master存在
					if(u_idExist){//用户已存在不用添加
						ret = new RecvBean(3, "该成员已经在群/组", this.grp_id);
						return ret ; 
					}
					else{//可以添加用户*******************
						
						this.data.groupandmember.list.get(i).getUserList().add(new UserBean(this.u_id,0));//添加用户
						
						storeGroup = this.data.groupandmember.list.get(i);//将需要解散的群组信息保存，以备后面的处理
						
						
						ret = new RecvBean(0,"添加用户成功",this.grp_id);
						return ret ;
					}
				}
					
				else{//master不存在
					ret = new RecvBean(1, "操作者不在该群/组", this.grp_id);
					return ret;
					
				}
				
			}
			
			
		}
		if(!groupOwner){
			ret = new RecvBean(2, "操作群/组ID不存在", this.grp_id);
			return ret;
		}

		
		ret =  new RecvBean(0, "添加用户成功",this.grp_id);
			
		return ret;
	}
		
			
			
}
	

	
