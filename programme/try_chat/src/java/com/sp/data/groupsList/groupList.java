package com.sp.data.groupsList;
import java.util.*;

import com.sp.data.userList.user;
/*群组表，结构如下。用于对群组进行crud时使用。表内元素包括群组ID，群主ID
 * [{group1,group2,...}]
 * user:{groupID,owerID}
 */
public class groupList {
	public static List<group> list = new ArrayList();
	
	public groupList(){
		super();
	}
	public static List<group> addGroup(group g){
		
		list.add(g);
		return list;
	}
	
	public static List<group> delGroup(String groupID){
		for(int i = 0 ; i < list.size() ; i++){
			if(list.get(i).groupID.equals(groupID)){
				list.remove(i);
				break;
			}
		}
		return list;
	}
	
	public static List<group> getgrouplist(){//初始化群组表
		list = new ArrayList();
		
		for(int i=0 ;i<10;i++){
			String groupid="group_"+(i+1);
			String userid= "user_"+(i+1);
			group g = new group(groupid,userid);
			list.add(g);
		}
		
		return list;
		
	}
	
	public static String tostring(){
		StringBuffer string = new StringBuffer();
		string.append("[ ");
		int size= list.size();
		for(int i=0;i<size;i++){
			String str=" { "+list.get(i).groupID+" , ";
			str += list.get(i).owerID+ " }, ";
			string.append(str);
		}
		
		string.append(" ]");
		return string.toString();
	}
}
