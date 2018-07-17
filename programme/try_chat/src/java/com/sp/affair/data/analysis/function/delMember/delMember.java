package com.sp.affair.data.analysis.function.delMember;

import java.util.List;

import net.sf.json.JSONObject;

import com.sp.affair.data.analysis.function.factoryInter.factory;
import com.sp.data.data.DATA;
import com.sp.data.groupAndmember.GroupA;
import com.sp.data.groupAndmember.member;
import com.sp.data.groupsList.group;
import com.sp.message.returnMessage.returnMessage;

public class delMember extends factory{
	public delMember(JSONObject json){
this.act = Integer.parseInt(json.getString("act"));
		
		this.grp_id = json.getString("grp_id");
		this.u_id = json.getString("u_id");
		this.master = json.getString("master");
		
		
		
		data = DATA.getInstance();
		
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
	public returnMessage doAction(){
		returnMessage ret = null ;
				
				
		/*
		 * 判断用户是否在该群
		 */
		int i,j;
		boolean exist = false;//标记是否群组找到用户
		boolean m_exist =false;//标记操作者是否在该群组
		boolean m_pro =false;
		boolean grp_id_exist =false;//标记被操作者是否在该群则
		
		for(i =0;i<this.data.groupandmember.list.size();i++){
			String groupID =this.data.groupandmember.list.get(i).ID;
			if(this.grp_id.equals(groupID)){//找到群组
				List<member> tempList = this.data.groupandmember.list.get(i).userlist;
				
				
				
				for(j = 0;j < tempList.size() ; j++ ) {
					member m =tempList.get(j);
					String userId = m.u_id;
					int type = m.type;
					if(userId.equals(this.master)){
						if(type == 0){//不是群主，权限不够
							ret = new returnMessage(4, this.master+",权限不够,群主才可以踢人");
							return ret;
						}
						else if(type ==1 ){//群主
							m_pro = true;
						}
						m_exist = true;
					}
					if(userId.equals(this.u_id)){
						grp_id_exist = true;
					}
					if(m_exist && grp_id_exist ){//都存在,可以删除！！！！！！！！！！！！！！
						
						this.data.groupandmember.list.remove(i);//删除再添加
						tempList.remove(j);//删除该用户
						this.data.groupandmember.list.add(new GroupA(this.grp_id,tempList));
						
						ret = new returnMessage(0, this.u_id+",删除成功");
						return ret;
					}
				}
				
				
				if(!m_exist){//master操作者不在群组
					ret = new returnMessage(1, this.master+",操作者不在该群/组");
					return ret;
				}
				
				if(m_exist && !grp_id_exist){//需删除用户不存在，无法删除
					
					ret = new returnMessage(6, this.u_id+",该成员已经不在群/组");
					return ret;
				}
				
				exist = true;
				break;
				
			}
		}
		if(!exist){
			ret = new returnMessage(2, this.grp_id+",操作群/组ID不存在");
		}
		
		
		return ret;
		
	}
		
			
			
}
	

	
