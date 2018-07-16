package com.sp.affair.data.analysis.function.createGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sp.affair.data.analysis.function.factoryInter.factory;
import com.sp.data.data.DATA;
import com.sp.data.groupAndmember.GroupA;
import com.sp.data.groupAndmember.member;
import com.sp.data.groupsList.group;
import com.sp.data.userList.user;
import com.sp.message.returnMessage.returnMessage;

/*
 * 创建群组
 */
public class createGroup extends factory{
	
	public createGroup(JSONObject json){
		this.act = Integer.parseInt(json.getString("act"));
		
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
	public String toString(){
		StringBuffer str= new StringBuffer();
		str.append(this.act);
		str.append(this.u_list);
		str.append(this.master);
		return str.toString();
	}
	
	@Override
	public returnMessage doAction(){
		returnMessage ret ;
		
		
/*
 * 判断用户是否存在
 */
		boolean owner=false;
		
		for(int i=0 ;i<this.data.userlist.list.size();i++){// 判断用户是否存在
			user u = this.data.userlist.list.get(i);
			if(u.ID.equals(this.master)){
				owner = true;
				break;
			}
		}
		if(!owner){//创建人不存在
			ret = new returnMessage(false, this.master+"创建人不存在！");
			return ret;
		}
		List<member> addUserlist = new ArrayList();
		/*
		 * 添加创建人
		 */
		addUserlist.add(new member(this.master,1));
		
		//无法添加为组员的成员名单列表retmessage
		List<user> retmessage= new ArrayList();
		
		 Calendar cal = Calendar.getInstance();
		
		//新建群号。命名为：创建人+当前年月日时分秒（如：user1_20180116151159）是唯一标识
		String now =getTime();
		String groupID = this.master+"_"+now;
		
		
		for(int i=1 ;i<this.u_list.size();i++){// 判断需要添加进群组的用户是否存在
			member m = this.u_list.get(i);
			user u=null;
			boolean tag= false;
			for(int j = 0 ; j < this.data.userlist.list.size() ; j++){//遍历用户表判断需添加用户是否存在
				u = this.data.userlist.list.get(j);
				if(m.u_id.equals(u.ID)){
					
					addUserlist.add(new member(m.u_id,0));
					tag = true;
					
					break;
				}
				
			}
			if(!tag){//用户m不存在
				retmessage.add(new user(m.u_id));
			}
		}
		
		
		if(addUserlist.size()<2){//组员都不存在
			ret = new returnMessage(false, "需添加已创建的组员（不包含组长）");
			return ret;
		}
		else{
			/*
			 * 将群组信息分别写入表userlist、groupAndmember,和对应的数据库表中
			 */
			
			GroupA g= new GroupA(groupID,addUserlist);
			this.data.groupandmember.addGroup(g);
			
			group gr= new group(groupID, this.master);
			
			this.data.grouplist.addGroup(gr);
			
			String temp = "";
			if(addUserlist.size()==this.u_list.size()){
				temp = "全部添加成功！";
			}
			else{
				temp = "部分添加成功，添加不成功名单：";
				for(int i=0;i<retmessage.size();i++){
					temp+=retmessage.get(i).ID+"\t";
				}
				temp+="\n";
			}
			ret =  new returnMessage(true, temp);
			coutSize();
			return ret;
		}
		
			
			
	}
	
}
