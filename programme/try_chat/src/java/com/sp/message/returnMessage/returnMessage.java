package com.sp.message.returnMessage;

/*
 * 处理结果的范阔
 * ret:处理成功或不成功
 * message:返回处理结果的反馈
 */
public class returnMessage {
	private boolean ret;
	private String message;
	public returnMessage(boolean ret,String message){
		this.ret = ret;
		this.message = message;
	}
	public boolean getRET(){
		return this.ret;
	}
	public String getMESSAGE(){
		return this.message;
	}
}
