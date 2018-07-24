package com.sp.affair.data.analysis.function.delMember;

import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jivesoftware.openfire.plugin.bean.GroupBean;
import org.jivesoftware.openfire.plugin.bean.RecvBean;
import org.jivesoftware.openfire.plugin.bean.ReqBean;
import org.jivesoftware.openfire.plugin.bean.UserBean;







import com.sp.affair.data.analysis.function.factoryInter.Factory;
import com.sp.data.data.cacheDATA;


public class delMember extends Factory{
	public delMember(ReqBean req,List<GroupBean> grpList){
		this.act = req.getAct();
		
		this.grp_id = req.getGrp_id();
		this.u_id = req.getU_id();
		this.master = req.getMaster();
			
		this.grpList = grpList;
		
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
//		List<GroupBean> groupList = this.data.groupandmember.list;//存放群组信息的集合
		int indexU=-1;
		for(int i = 0 ; i< grpList.size(); i++){
//			System.out.println(groupList.get(i).getGrpId()+" ?== "+this.grp_id);
			if(grpList.get(i).getGrpId().equals(this.grp_id)){//群号存在
				
				groupOwner = true;//群号存在
				
				List<UserBean> userList =grpList.get(i).getUserList();
				for(int j = 0 ;j< userList.size();j++){
					if(userList.get(j).getType()==1){//群主
					
						if(userList.get(j).getU_id().equals(this.master)){//master在群组中，可以添加新用户进群组
							masterExist = true;
							
						}
						else{
							ret = new RecvBean(4,"权限不够,群主才可以踢人",this.grp_id);
							return ret ;
						}
					}
					
					if(userList.get(j).getU_id().equals(this.u_id)){//需要踢出的用户在群组内
							u_idExist = true;
							indexU = j ;
					}
					
				}
				
				if(masterExist){//master存在
					if(u_idExist){//用户已存在可以踢人****************
											
						storeGroup = grpList.get(i);//将需要解散的群组信息保存，以备后面的处理
						System.out.println(storeGroup.getUserList().size());
						grpList.get(i).getUserList().remove(indexU);//踢出用户
												
						ret = new RecvBean(0,"踢出用户成功",this.grp_id);
						return ret ;
					}
					else{//操作者不是群主
						
						
						
						ret = new RecvBean(6, "该用户不再群/组", this.grp_id);
						return ret ; 
						
					}
				}
					
				else{//master不存在
					ret = new RecvBean(1, "操作者不在该群/组", this.grp_id);
					return ret;
					
				}
//				groupOwner = true;
//				break;
			}
			
			
		}
		if(!groupOwner){
			ret = new RecvBean(2, "操作群/组ID不存在", this.grp_id);
			return ret;
		}

		
		ret =  new RecvBean(0, "踢出用户成功",this.grp_id);
			
		return ret;
	}
		
			
			
}
	

	
