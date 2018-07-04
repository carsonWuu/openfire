package com.test.smack.cuid;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class openConnection {
	String host="192.168.1.101";
	int port=5222;
	public openConnection(){
		super();
	}
	public openConnection(String host){
		super();
		this.host=host;
		
	}
	public XMPPConnection getconnection(){
		ConnectionConfiguration config = new ConnectionConfiguration(this.host,this.port);
		XMPPConnection conn=null;
		try{
			conn= new XMPPConnection(config);
			conn.connect();
		}
		catch(XMPPException e){
			e.printStackTrace();
		}
		return conn;
	}
	public String getHost(){
		return this.host;
	}
	public void setHost(String host){
		this.host = host;
	}
	
}
