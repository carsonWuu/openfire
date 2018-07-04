package com.test.smack.FileListen;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.filetransfer.FileTransfer.Status;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;

import com.test.smack.sendFile.sendFile;

public class FileListen {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
	XMPPConnection con;
	public FileListen(){
		ConnectionConfiguration	connectionConfig =  new ConnectionConfiguration("192.168.1.101",Integer.parseInt("5222"));
		connectionConfig.setSASLAuthenticationEnabled(false);// 不使用SASL验证，设置为false
		connectionConfig
				.setSecurityMode(ConnectionConfiguration.SecurityMode.enabled);
		// 允许自动连接
		connectionConfig.setReconnectionAllowed(false);
		connectionConfig.setSendPresence(false);// 设置离线
		// 收到好友邀请后manual表示需要经过同意,accept_all表示不经同意自动为好友
		

		con = new XMPPConnection(connectionConfig);
	}
	public void startRecvFileListen(XMPPConnection conn){
		//conn必须是已经登录了的
		FileTransferManager manager = new FileTransferManager(conn);
		//添加文件接收监听器
	    manager.addFileTransferListener(new FileTransferListener() {
	    	//每次有文件发送过来都会调用些方法
	        public void fileTransferRequest(FileTransferRequest request) {
	        	//调用request的accetp表示接收文件，也可以调用reject方法拒绝接收
	            final IncomingFileTransfer inTransfer = request.accept();
	            try {
	            	System.out.println("接收到文件发送请求，文件名称："+request.getFileName());
	            	//接收到的文件要放在哪里
	                String filePath = "D:\\"+request.getFileName();
	                inTransfer.recieveFile(new File(filePath));
	                //如果要时时获取文件接收的状态必须在线程中监听，如果在当前线程监听文件状态会导致一下接收为0
	                new Thread(){
	                	@Override
	                	public void run(){
	                		long startTime = System.currentTimeMillis();
	                		while(!inTransfer.isDone()){
	                        	if (inTransfer.getStatus().equals(Status.error)){
	                				System.out.println(sdf.format(new Date())+"error!!!"+inTransfer.getError());
	                			}else{
	                				double progress = inTransfer.getProgress();
	                				progress*=100;
	                				System.out.println(sdf.format(new Date())+"status="+inTransfer.getStatus());
//	                				System.out.println(sdf.format(new Date())+"progress="+nf.format(progress)+"%");
	                			}
	                			try {
	                				Thread.sleep(1000);
	                			} catch (InterruptedException e) {
	                				e.printStackTrace();
	                			}
	                        }
	                		System.out.println("used "+((System.currentTimeMillis()-startTime)/1000)+" seconds  ");
	                	}
	                }.start();
	            } catch (XMPPException e) {
	                JOptionPane.showMessageDialog(null, "文件失败", "错误", JOptionPane.ERROR_MESSAGE);
	                e.printStackTrace();
	            }
	        }

			
	    });
	    System.out.println(conn.getUser()+"--"+conn.getServiceName()+"开始监听文件传输");
	}
	public static void main(String[] args){
		FileListen listen=new FileListen();
		listen.startRecvFileListen(listen.con);
	}

}
