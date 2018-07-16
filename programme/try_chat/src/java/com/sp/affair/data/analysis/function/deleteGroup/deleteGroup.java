package com.sp.affair.data.analysis.function.deleteGroup;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sp.affair.data.analysis.function.factoryInter.factory;
import com.sp.data.data.DATA;
import com.sp.data.groupAndmember.member;
import com.sp.message.returnMessage.returnMessage;

public class deleteGroup extends factory{
	public deleteGroup(JSONObject json){
		this.act = Integer.parseInt(json.getString("act"));
		this.grp_id = json.getString("grp_id");
		this.master = json.getString("master");
		this.u_id = json.getString("u_id");
		this.master = json.getString("master");
		/*
		 * 将message中的u_list转换为list<member>形式
		 */
		member m;
		JSONArray ulist = json.getJSONArray("u_list");
		for(int i=0 ; i<ulist.size();i++){
			JSONObject tempJson = JSONObject.fromObject(ulist.get(i));
			m = new member(tempJson.getString("u_id"),tempJson.getInt("type"));
			this.u_list.add(m);
		}
//		this.u_list.add("") ;
		
		data = DATA.getInstance();
	}
	@Override
	public returnMessage doAction() {
		// TODO Auto-generated method stub
		return null;
	}

}
