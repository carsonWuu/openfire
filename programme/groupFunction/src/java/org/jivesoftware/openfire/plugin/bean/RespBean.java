package org.jivesoftware.openfire.plugin.bean;

import java.util.List;


public class RespBean {
	private Integer code;
	private String msg;
	private Integer act;
	private String grp_id;
	private String u_id;
	private String master;
	private List<UserBean> u_list;
	private Integer type;
	private Integer linkid;
	private List<GpsBean> gps_list;
	private String alias;
	private String from;
	
	
	
	
	
	public String getFrom() {
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
	}


	public String getAlias() {
		return alias;
	}


	public void setAlias(String alias) {
		this.alias = alias;
	}


	public int getAct() {
		return act;
	}


	public void setAct(int act) {
		this.act = act;
	}


	public String getGrp_id() {
		return grp_id;
	}


	public void setGrp_id(String grpId) {
		grp_id = grpId;
	}


	public String getU_id() {
		return u_id;
	}


	public void setU_id(String uId) {
		u_id = uId;
	}


	public String getMaster() {
		return master;
	}


	public void setMaster(String master) {
		this.master = master;
	}


	public List<UserBean> getU_list() {
		return u_list;
	}


	public void setU_list(List<UserBean> uList) {
		u_list = uList;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public int getLinkid() {
		return linkid;
	}


	public void setLinkid(int linkid) {
		this.linkid = linkid;
	}


	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}


	public List<GpsBean> getGps_list() {
		return gps_list;
	}


	public void setGps_list(List<GpsBean> gpsList) {
		gps_list = gpsList;
	}


}
