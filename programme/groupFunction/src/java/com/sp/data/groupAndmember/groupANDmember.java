package com.sp.data.groupAndmember;
import java.sql.SQLException;
import java.util.*;

import org.jivesoftware.openfire.plugin.bean.GroupBean;
import org.jivesoftware.openfire.plugin.bean.UserBean;
import org.jivesoftware.openfire.plugin.db.DBHelper;

/*
 * 所有的用户群组信息。
 * [{id,userlist{type,u_id}},...]
 */
public class groupANDmember {
//	public static void main(String args[]){
//		groupANDmember.InitGrouplist();
//	}
	public static List<GroupBean> list = new ArrayList();
	public static void addGroup(GroupBean g){
		/*
		 * 一、数据库更新：同时更新两张表
		 * 1.app_usergroups
		 * 2.app_groupandmember
		 * 
		 * 二、缓存更新：群组集合中添加一个群信息。
		 */
		
		
		String UG_ID = g.getGrpId();
		String UG_Name =g.getAlias();
		String UG_AdminID = g.getMasterId();
		List<Map<String,Object>> insertList = new ArrayList();
		Map<String,Object> map =new HashMap();
		
		map.put("UG_ID", UG_ID);
		map.put("UG_Name", UG_Name);
		map.put("UG_AdminID",UG_AdminID);
		
		insertList.add(map);
		List<UserBean> userList = g.getUserList();
		int userLength= userList.size();
		String tables[] =new String[userLength+1];
		tables[0]="app_usergroups";
		int i =0;
		for(;i<userLength;i++){
			tables[i+1]="app_groupandmember";
		}
		
		for(i = 0; i< userLength;i++){
//			Map<String,Object> user =new HashMap();
//			DBHelper.insert("app_groupandmember",user);
			String grp_id = UG_ID;
			String u_id = userList.get(i).getU_id();
			int type =  userList.get(i).getType();
			int state=  userList.get(i).getState();
			int open =  userList.get(i).getOpen();
			
			map =new HashMap();
			map.put("grp_id", grp_id);
			map.put("u_id", u_id);
			map.put("type",type);
			map.put("state", state);
			map.put("open", open);
			insertList.add(map);
			
		}
		System.out.println("tables:"+tables.length);
		System.out.println("insertList:"+insertList);
		try {
			DBHelper.insertCommit(tables, insertList);
			list.add(g);
//			DBHelper.insert("app_usergroups", map);
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("创建群组失败，原因："+e);
		}
		finally{
			
		}
		
		
	}
	public static List<GroupBean> delGroup(String ID){//根据群组ID删除
		for(int i = 0 ; i < list.size() ; i++){
			if(list.get(i).getGrpId().equals(ID)){
				list.remove(i);
				break;
			}
		}
		return list;
	}
	public static List<GroupBean> delGroup(int index){//根据群组ID删除
		
		list.remove(index);
		
		return list;
	}
	
	
	public static List<GroupBean> InitGrouplist(){//初始化群组表
		list = new ArrayList();
		
		String sql_userGroups="select UG_ID,UG_Name from app_usergroups group by UG_ID,UG_Name";
		
		try {
			List<Map<String,Object>> selectList =DBHelper.queryForList(sql_userGroups);
			for(int i_list =0 ; i_list <selectList.size(); i_list++){
				String UG_ID = (String) selectList.get(i_list).get("UG_ID");
				String UG_Name = (String) selectList.get(i_list).get("UG_Name");
				String sql1="select u_id,type,open,state from app_groupandmember where grp_id =\""+UG_ID+"\"";
				List<Map<String,Object>> userList = DBHelper.queryForList(sql1);
				List<UserBean> u_list = new ArrayList();
				for(int i_userlist=0; i_userlist<userList.size();i_userlist++){
					String u_id =(String) userList.get(i_userlist).get("u_id");
					int type =(int) userList.get(i_userlist).get("type");
					int open =(int) userList.get(i_userlist).get("open");
					int state =(int) userList.get(i_userlist).get("state");
					UserBean u =new UserBean(u_id, type, open);
					u_list.add(u);
				}
				GroupBean g = new GroupBean(UG_ID,UG_Name,u_list);
				list.add(g);
			}
//			for(int i=0 ;i<10;i++){
//				String id="group_"+(i+1);
//				List<UserBean> l = new ArrayList();
//				for(int j = 0; j< 4;j++){
//					
//					int type = 0;
//					if(j==0)type=1;
//					UserBean m = new UserBean("user"+(j+1),type);
//					l.add(m);
//				}
//				
//				
//				GroupBean g = new GroupBean(id,id,l);
//				list.add(g);
//			}
//	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		System.out.println(list);
		return list;
		
	}
	
	public static String tostring(){
		StringBuffer string = new StringBuffer();
		int size= list.size();
		string.append("[");
		for(int i=0;i<size;i++){
			String str="{"+list.get(i).getGrpId()+",[";
			int count = list.get(i).getUserList().size();
			for(int j=0 ;j< count ;j++){
				str +="{";
				str += "u_id : "+list.get(i).getUserList().get(j).getU_id()+" , ";
				str += "type : "+list.get(i).getUserList().get(j).getType() ;
				str +="},";
			}
			str +="]},";
			string.append(str);
		}
		string.append("]");
		return string.toString();
	}
}
