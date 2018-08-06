package org.jivesoftware.openfire.plugin.bean;

import java.io.Serializable;

public class UserBean implements Serializable{
	private String u_id;
	private String name;//名称
	private String nickname;//群昵称
	private int type;
	
	
	private int state;
	
	private int open;
	private static final long serialVersionUID = 1L;
	public static void main(String args[]){
		Object obj[]=new Object[10];
		UserBean userBean = new UserBean(obj);
	}
	
	public UserBean(){};
	public UserBean(String u_id ,int type){
		this.u_id = u_id;
		this.type = type;
	};
	public UserBean(String u_id ,int type,int open){
		this.u_id = u_id;
		this.type = type;
		this.open = open;
	};
	public UserBean(Object...params){
		this.u_id = u_id;
		this.type = type;
		this.open = open;
	};
	public UserBean(String u_id,String name,String nickname,int type,int state,int open){
		this.u_id = u_id;
		this.name = name;
		this.nickname=nickname;
		
		this.type = type;
		this.state =state;
		this.open = open;
	}
//	public UserBean(String u_id ,String alias,int type,int open){
//		this.u_id = u_id;
//		this.type = type;
//		this.open = open;
//	};
	
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String uId) {
		u_id = uId;
	}
	public String getNickname() {
		return this.nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getname() {
		return this.name;
	}
	public void setname(String name) {
		this.name = name;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setOpen(int open){
		this.open= open;
	}
	public int getOpen(){
		return this.open;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	@Override
	public String toString(){
		return "{ \"u_id\":\""+this.u_id+"\",\""+"name\":\""+this.name+"\",\"nickname\":\""+this.nickname+"\",\"type\":"+this.type+",\"state\":"+ this.state+",\"open\":"+this.open+"}";
	}
	
}
