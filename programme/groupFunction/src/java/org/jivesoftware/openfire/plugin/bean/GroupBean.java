package org.jivesoftware.openfire.plugin.bean;

import java.util.List;
import java.io.*;



public class GroupBean implements Serializable{
	private String grpId;
	private List<UserBean> userList;
	private String masterId;
	private int state;//暂时不用
	private String alias;//群名称
	
	private static final long serialVersionUID = 1L;
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
	 public Object deepClone() {
		 // 将对象写到流里
		 Object ret =null;
		 try{
			 ByteArrayOutputStream bo = new ByteArrayOutputStream();
			 ObjectOutputStream oo = new ObjectOutputStream(bo);
			 oo.writeObject(this);
			 // 从流里读出来
			 ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
			 ObjectInputStream oi = new ObjectInputStream(bi);
			 ret=oi.readObject();
		 }
		 catch(IOException e){
			 e.printStackTrace();
		 }
		 catch(ClassNotFoundException e){
			 e.printStackTrace();
		 }
		 return ret;
	 }
	@Override
	public String toString(){
		return "{grpID: "+this.grpId+" , "+"alias: "+this.alias+" , "+" userList: "+this.userList+" }";
	}
}
