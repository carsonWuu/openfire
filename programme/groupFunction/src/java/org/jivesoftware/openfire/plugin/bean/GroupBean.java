package org.jivesoftware.openfire.plugin.bean;

import java.util.List;


public class GroupBean {
	private String grpId;
	private List<UserBean> userList;
	private String masterId;
	private int state;
	
	public GroupBean(){
		
	}
	public GroupBean(String grpid,List<UserBean> userlist){
		this.grpId = grpid;
		this.userList = userlist;
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
}
