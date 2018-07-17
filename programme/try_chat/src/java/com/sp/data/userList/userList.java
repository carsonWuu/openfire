package com.sp.data.userList;
import java.util.*;

import com.sp.data.groupAndmember.GroupA;
/*用户表，结构如下。用于对用户进行crud时使用。
 * [{user1,user2,...}]
 * user:{ID}
 */
public class userList {
	public static List<user> list = new ArrayList();
	
	public userList(){
		super();
	}
	public List<user> addUser(user u){
		list.add(u);
		return list;
	}
	public static List<user> delGroup(String ID){
		for(int i = 0 ; i < list.size() ; i++){
			if(list.get(i).ID.equals(ID)){
				list.remove(i);
				break;
			}
		}
		return list;
	}
	
	public static List<user> getUserlist(){//初始化用户表
		list = new ArrayList();
		for(int i=0 ;i<10;i++){
			user u = new user("user_"+(i+1));
			list.add(u);
		}
		
		return list;
		
	}
	
	public static String tostring(){
		StringBuffer string = new StringBuffer();
		string.append("[ ");
		int size= list.size();
		for(int i=0;i<size;i++){
			String str=list.get(i).ID + " , ";
			
			string.append(str);
		}
		
		string.append(" ]");
		return string.toString();
	}
}
