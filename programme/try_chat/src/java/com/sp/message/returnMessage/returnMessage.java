package com.sp.message.returnMessage;

/*
 * 处理结果的范阔
 * ret:处理成功或不成功
 * message:返回处理结果的反馈
 */
public class returnMessage {
	private int  code;
	private String message;
	private String groupID;
	public returnMessage(int code,String message){
		this.code = code;
		this.message = message;
	}
	public returnMessage(int code ,String message,String id){
		this.code = code;
		this.message = message;
		this.groupID = id;
	}
	public int  getRET(){
		return this.code;
	}
	public String getMESSAGE(){
		return this.message;
	}
	public String getGroupID(){
		return this.groupID;
	}
	
	@Override
	public String toString(){
		return "{ code : "+this.code+" , "+"msg : "+this.message+" , grp_id : "+this.groupID+" }";
	}
}
