package com.sp.data.data;

import com.sp.data.groupAndmember.groupAndmember;
import com.sp.data.groupsList.groupList;
import com.sp.data.userList.userList;

public class DATA {
	private static DATA instance=null;
	public userList userlist;//用户表[{id1},{id2}]
	public groupList grouplist;//群组表[{groupid1,ownerid1},{groupid2,ownerid2}]
	public groupAndmember groupandmember; //群组及成员表[{groupid1,userlist[id1,id2]},{}]
	private DATA(){
		userlist.getUserlist();
		grouplist.getgrouplist();
		groupandmember.getGROUPlist();
	}
	public static DATA getInstance(){
		if(null == instance){
			instance = new DATA();
		}
		return instance;
	}
}
