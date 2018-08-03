package org.jivesoftware.openfire.plugin.bean;

import java.util.List;


public class GroupBean {
	private String grpId;
	private List<UserBean> userList;
	private String masterId;
	private int state;//暂时不用
	private String alias;//群名称
	
	
	public GroupBean(){
		
	}
	public GroupBean(String grpid,String alias,List<UserBean> userlist){
		this.grpId = grpid;
		this.userList = userlist;
		this.alias = alias;
	}
	public GroupBean(String grpid,String alias,List<UserBean> userlist,String master){
		this.grpId = grpid;
		this.userList = userlist;
		this.alias = alias;
		this.masterId =master;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getGrpId() {
		return grpId;
	}
	public void setGrpId(String grpId) {
		this.grpId = grpId;
	}
	public List<UserBean> getUserList() {
		return userList;
	}
	public void setUserList(List<UserBean> userList) {
		this.userList = userList;
	}
	public String getMasterId() {
		return masterId;
	}
	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}
	public void setAlias(String alias){
		this.alias = alias;
	}
	public String getAlias(){
		return this.alias;
	}
	@Override
	public String toString(){
		return "{grpID: "+this.grpId+" , "+"alias: "+this.alias+" , "+" userList: "+this.userList+" }";
	}
}
