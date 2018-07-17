package com.sp.data.groupAndmember;
import java.util.*;

import com.sp.data.groupsList.group;
import com.sp.data.userList.user;
/*
 * 所有的用户群组信息。
 * [{id,userlist{type,u_id}},...]
 */
public class groupAndmember {
	public static List<GroupA> list = new ArrayList();
	public static void addGroup(GroupA g){
		/*
		 * 向群组集合中添加一个群信息。
		 */
		boolean ret = true;
		list.add(g);
		
	}
	public static List<GroupA> delGroup(String ID){
		for(int i = 0 ; i < list.size() ; i++){
			if(list.get(i).ID.equals(ID)){
				list.remove(i);
				break;
			}
		}
		return list;
	}
	
	public static List<GroupA> getGROUPlist(){//初始化群组表
		list = new ArrayList();
		
		for(int i=0 ;i<10;i++){
			String id="group_"+(i+1);
			List<member> l = new ArrayList();
			for(int j = 0; j< 10;j++){
				
				int type = 0;
				if(j==0)type=1;
				member m = new member("user_"+(j+1),type);
				l.add(m);
			}
			
			
			GroupA g = new GroupA(id,l);
			list.add(g);
		}

		
		return list;
		
	}
	
	public static String tostring(){
		StringBuffer string = new StringBuffer();
		int size= list.size();
		string.append("[");
		for(int i=0;i<size;i++){
			String str="{"+list.get(i).ID+",[";
			int count = list.get(i).userlist.size();
			for(int j=0 ;j< count ;j++){
				str +="{";
				str += "u_id : "+list.get(i).userlist.get(j).u_id+" , ";
				str += "type : "+list.get(i).userlist.get(j).type;
				str +="},";
			}
			str +="]},";
			string.append(str);
		}
		string.append("]");
		return string.toString();
	}
}
