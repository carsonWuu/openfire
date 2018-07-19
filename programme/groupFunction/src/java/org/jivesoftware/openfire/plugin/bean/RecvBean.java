package org.jivesoftware.openfire.plugin.bean;

public class RecvBean {
	private int code;
	private String msg;
	private String grp_id;
	
	public RecvBean(){
		
	}
	public RecvBean(int code , String msg){
		this.code = code;
		this.msg = msg;
		
	}
	public RecvBean(int code , String msg ,String grp_id){
		this.code = code;
		this.msg = msg;
		this.grp_id = grp_id;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getGrp_id() {
		return grp_id;
	}
	public void setGrp_id(String grpId) {
		grp_id = grpId;
	}
	
}
