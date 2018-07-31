package com.sp.data.data;

import org.jivesoftware.openfire.plugin.bean.GroupBean;

import com.sp.data.groupAndmember.groupANDmember;


public class cacheDATA {
	private static cacheDATA instance=null;
	
	public static groupANDmember groupandmember; //群组及成员表[{groupid1,userlist[id1,id2]},{}]
	private cacheDATA(){
		
		groupandmember.InitGrouplist();
	}
	
	public static cacheDATA getInstance(){
		if(null == instance){
			instance = new cacheDATA();
		}
		return instance;
	}
	public static void addGroup(GroupBean g){
		groupANDmember.addGroup(g);
	}
}
