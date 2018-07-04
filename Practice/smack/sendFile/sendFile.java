package com.test.smack.sendFile;

import java.io.File;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.*; 
import org.jivesoftware.smackx.filetransfer.FileTransfer;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
public class sendFile {
	public Connection con;
	public sendFile(){
		AccountManager account;
		ConnectionConfiguration connect;
		connect = new ConnectionConfiguration("192.168.1.101",Integer.parseInt("5222"),"cnblog@163.com");
		connect.setReconnectionAllowed(true);//自动连接服务器
		connect.setSendPresence(true);//true：告诉服务器连接状态
		
		 con=new XMPPConnection(connect);
		try{
			con.connect();
			account = con.getAccountManager();
			con.login("admin","123456");
		}
		catch(XMPPException e){
			throw new IllegalStateException(e);
		}
	}
	public static void main(String[] args){
		sendFile send =new sendFile();
		
		//System.out.println(con.getRoster().getPresence("wcs@192.168.1.101"));
		Presence pre= send.con.getRoster().getPresence("wcs@192.168.1.101");
		System.out.println(pre.getFrom());
		if(pre.getType() != Presence.Type.unavailable){
			FileTransferManager manager =new FileTransferManager(send.con);
			OutgoingFileTransfer transfer = manager.createOutgoingFileTransfer(pre.getFrom());
			try {
				transfer.sendFile(new File("d:\\test.png"),"图片");
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(transfer.getStatus());
			System.out.println(FileTransfer.Status.in_progress);
			System.out.println(transfer.isDone());
			while(!transfer.isDone()){
				if(transfer.getStatus() == FileTransfer.Status.in_progress){
					System.out.println(transfer.getStatus());
					System.out.println(transfer.getProgress());
					System.out.println(transfer.isDone());
				}
			}
		}
			
		
	}
}
