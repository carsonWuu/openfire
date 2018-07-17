package com.sp.affair.data.analysis.function.deleteGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sp.affair.data.analysis.function.factoryInter.factory;
import com.sp.data.data.DATA;
import com.sp.data.groupAndmember.GroupA;
import com.sp.data.groupAndmember.member;
import com.sp.data.groupsList.group;
import com.sp.data.userList.user;
import com.sp.message.returnMessage.returnMessage;

public class deleteGroup extends factory{
	public deleteGroup(JSONObject json){
		this.act = Integer.parseInt(json.getString("act"));
		this.grp_id = json.getString("grp_id");
		this.master = json.getString("master");

		data = DATA.getInstance();
	}
	@Override
	public String toString(){
		StringBuffer str= new StringBuffer();
		str.append(this.act);
		str.append(this.grp_id);
		
		str.append(this.master);
		return str.toString();
	}
	
	@Override
	public returnMessage doAction(){
		returnMessage ret =null;	
		
	/*
	 * 判断用户是否存在
	 */
		boolean owner=false;
		
		for(int i=0 ;i<this.data.grouplist.list.size();i++){// 判断群组是否存在
			group g = this.data.grouplist.list.get(i);
			if( g.groupID.equals(this.grp_id)){//群组存在且操作者是群主
				if(g.owerID.equals(this.master) ){
				
					this.data.groupandmember.delGroup(this.grp_id);
					this.data.grouplist.delGroup(this.grp_id);
					owner = true;
					ret = new returnMessage(0, this.grp_id+"群组删除成功");
					
					cout();
					return ret;
				}
				
				else{//群组存在，但操作者不是群主
					ret= new returnMessage(4, "权限不够，群主才可以解散群组");
					return ret;
				}
			}
				
				
				
		}
		if(!owner){//创建人不存在
			ret = new returnMessage(2, this.grp_id+"操作群/组ID不存在");
			
		}
		
		return ret;
	
	}
	
	
}
