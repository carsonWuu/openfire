package com.sp.data.groupAndmember;

/*
 * 群组成员结构
 * {type,u_id}:标记群员或群主，成员ID
 */
public class member {
	public int type;
	public String u_id;
	
	public member(String u_id, int type){
		this.type = type;
		this.u_id = u_id;
	}
}
