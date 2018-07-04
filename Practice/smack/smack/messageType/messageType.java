package com.test.smack.messageType;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.*;;
public class messageType {
	
	
	public static void main(String[] args){
		AccountManager account;
		ConnectionConfiguration connect;
		connect = new ConnectionConfiguration("192.168.1.101",Integer.parseInt("5222"),"cnblog@163.com");
		connect.setReconnectionAllowed(true);//自动连接服务器
		connect.setSendPresence(false);//true：告诉服务器连接状态
		
		Connection con =new XMPPConnection(connect);
		try{
			con.connect();
			account = con.getAccountManager();
			con.login("admin","123456");
		}
		catch(XMPPException e){
			throw new IllegalStateException(e);
		}
		Message message = new Message();
		message.setTo("wcs@192.168.1.101");
		message.setSubject("通知");
		message.setBody("下午2点30开会");
		/*normal可以存放在ofoffline表中，
		 * headline在线才能接受
		 */
		message.setType(Message.Type.normal);
		con.sendPacket(message);
		con.disconnect();
	}
}
