package org.jivesoftware.openfire.message;

import net.sf.json.JSONObject;

import org.xmpp.packet.Message;

public class messageAnalysis {
	public Message message;
	public messageAnalysis(Message m){
		this.message = m ;
	}
	public String getSubject(){
		return message.getSubject();
	}
	public JSONObject getBody2Json(){
		return JSONObject.fromObject(this.message.getBody());
	}
	public int getACT(){
		return getBody2Json().getInt("act");
	}
	
}
