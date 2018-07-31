package org.jivesoftware.openfire.plugin.bean;

public class UserBean {
	private String u_id;
	private int type;
	private int state;
	private String alias;//
	private int open;
	
	
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
//	public UserBean(String u_id ,String alias,int type,int open){
//		this.u_id = u_id;
//		this.type = type;
//		this.open = open;
//	};
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String uId) {
		u_id = uId;
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
	@Override
	public String toString(){
		return "{ u_id: "+this.u_id+", "+" type : " +this.type +"},";
	}
	
}
