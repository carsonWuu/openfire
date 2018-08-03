package com.sp.data.groupAndmember;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jivesoftware.openfire.plugin.bean.GroupBean;
import org.jivesoftware.openfire.plugin.bean.ReqBean;
import org.jivesoftware.openfire.plugin.bean.UserBean;
import org.jivesoftware.openfire.plugin.db.DBHelper;
import org.jivesoftware.openfire.plugin.util.GsonUtil;

import com.google.gson.Gson;


/*
 * 所有的用户群组信息。
 * [{id,userlist{type,u_id}},...]
 */
public class groupANDmember {

	public static List<GroupBean> list = new ArrayList();
	public static List<GroupBean> InitGrouplist(){//初始化群组表
		list = new ArrayList();
		
		String sql="select c_group_id,c_group_name,text_member,c_admin_id from app_groupandmember";
		
		try {
			List<Map<String,Object>> selectList =DBHelper.queryForList(sql);
			for(int i_list =0 ; i_list <selectList.size(); i_list++){
				String c_group_id = (String) selectList.get(i_list).get("c_group_id");
				String c_group_name = (String) selectList.get(i_list).get("c_group_name");
				String member=(String) selectList.get(i_list).get("text_member");
				String c_admin_id=(String) selectList.get(i_list).get("c_admin_id");
				List<UserBean> userList =userlistFromSql(member); 
				GroupBean g = new GroupBean(c_group_id,c_group_name,userList,c_admin_id);
				list.add(g);
			}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		System.out.println(list);
		return list;
		
	}
	public static List<UserBean> userlistFromSql(String text_member){
		List<UserBean> list = new ArrayList();
		String regEx ="(?<=\\{)(.+?)(?=\\})";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(text_member);
		while(matcher.find()){
			System.out.println("matcher.group():" + matcher.group());
			UserBean userBean =GsonUtil.gson.fromJson("{"+matcher.group()+"}", UserBean.class);
			list.add(userBean);
		}
		
		
		return list;
	}
	public static int addGroup(GroupBean g){
		/*
		 * 一、数据库更新：
		 * 
		 * 1.app_groupandmember
		 * 
		 * 二、缓存更新：群组集合中添加一个群信息。
		 */
		
		int ret =0;
		
		String c_group_id = g.getGrpId();
		String c_group_name =g.getAlias();
		String c_admin_id = g.getMasterId();
		
		Map<String,Object> map =new HashMap();
		
		map.put("c_group_id", c_group_id);
		map.put("c_group_name", c_group_name);
		map.put("c_admin_id",c_admin_id);
		
		
		List<UserBean> userList = g.getUserList();
		map.put("text_member", userList.toString());
		System.out.println(map);

		
		try {
			DBHelper.insert("app_groupandmember", map);
			list.add(g);
//			DBHelper.insert("app_usergroups", map);
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("创建群组失败，原因："+e);
			ret= 99;
			
		}
		finally{
			return ret;
		}
		
		
	}
	public static int delGroup(String ID,int index){//根据群组ID删除
		/*
		 * 一、数据库更新：同时更新两张表
		
		 * 1.app_groupandmember
		 * 
		 * 二、缓存更新：群组集合中添加一个群信息。
		 */
		int ret = 0;
		String sql="delete from app_groupandmember where c_group_id=?";
		Object obj[]={ID};
		
		try{
			DBHelper.executeUpdate(sql, obj);
			
			list.remove(index);
		}
		catch(SQLException e){
			ret = 99;
			System.out.println("解散群组失败，原因："+e);
		}
		finally{
			return ret;
		}
		
	}
	
	public static int addMember(String grp_id,String u_id){
		int ret =0;
		String tables="app_groupandmember";
		
		Map<String,Object> map =new HashMap();
		map.put("grp_id", grp_id);
		map.put("u_id", u_id);
		
		
		try {
			DBHelper.insert(tables,map);
		} catch (SQLException e) {
			ret = 99;
			System.out.println("添加成员失败，原因：");
		
		
		}
			
		finally{
			return ret;
		}
		
			
		
	}
	public static int delMember(String grp_id ,String u_id,int i,int index){
		int ret = 0;
		String sql[]=new String[1];
		sql[0]="delete from app_groupandmember where grp_id= \""+grp_id+"\" && u_id =\""+u_id+"\"";
		try{
			DBHelper.updateCommit(sql);
			list.get(i).getUserList().remove(index);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("创建群组失败，原因："+e);
			ret= 99;
			
		}
		finally{
			return ret;
		}
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
