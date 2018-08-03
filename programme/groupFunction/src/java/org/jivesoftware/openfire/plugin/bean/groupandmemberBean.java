package org.jivesoftware.openfire.plugin.bean;

public class groupandmemberBean {
	private String u_id;
	private String name;//名称
	private String nickname;//群昵称
	private int type;
	
	
	private int state;
	
	private int open;
	public static void main(String args[]){
		Object obj[]=new Object[10];
		UserBean userBean = new UserBean(obj);
	}
	
	
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
		return "{ u_id: "+this.u_id+", "+"name:"+this.name+",nickname:"+this.nickname+",type:"+this.type+",state:"+ this.state+",open:"+this.open+"}";
	}
	
}

