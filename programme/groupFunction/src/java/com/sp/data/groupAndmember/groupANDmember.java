package com.sp.data.groupAndmember;
import java.util.*;

import org.jivesoftware.openfire.plugin.bean.GroupBean;
import org.jivesoftware.openfire.plugin.bean.UserBean;

/*
 * 所有的用户群组信息。
 * [{id,userlist{type,u_id}},...]
 */
public class groupANDmember {
	public static List<GroupBean> list = new ArrayList();
	public static void addGroup(GroupBean g){
		/*
		 * 向群组集合中添加一个群信息。
		 */
		
		boolean ret = true;
		list.add(g);
		
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
		
		for(int i=0 ;i<10;i++){
			String id="group_"+(i+1);
			List<UserBean> l = new ArrayList();
			for(int j = 0; j< 2;j++){
				
				int type = 0;
				if(j==0)type=1;
				UserBean m = new UserBean("user"+(j+1),type);
				l.add(m);
			}
			
			
			GroupBean g = new GroupBean(id,l);
			list.add(g);
		}

		
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
