package org.jivesoftware.openfire.plugin;

import java.io.File;

import net.sf.json.JSONObject;

import org.jivesoftware.database.DbConnectionManager;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.openfire.interceptor.InterceptorManager;
import org.jivesoftware.openfire.interceptor.PacketInterceptor;
import org.jivesoftware.openfire.interceptor.PacketRejectedException;
import org.jivesoftware.openfire.message.messageAnalysis;
import org.jivesoftware.openfire.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.IQ;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;
import org.xmpp.packet.Packet;
import org.xmpp.packet.Presence;

import com.sp.affair.data.analysis.analysis;



import com.sp.data.data.DATA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class chatPlugins  implements Plugin,PacketInterceptor{
	private XMPPServer server; 
	private InterceptorManager interceptorManager;
	
	private static final Logger Log = LoggerFactory.getLogger(chatPlugins.class);
	
	@Override
	public void initializePlugin(PluginManager manager, File pluginDirectory) {
		// TODO Auto-generated method stub
		Log.info("chatPlugins");
		server = XMPPServer.getInstance();
		System.out.println("chatPlugins init...");
		
		interceptorManager = InterceptorManager.getInstance();
		interceptorManager.addInterceptor(this);
	}

	@Override
	public void destroyPlugin() {
		// TODO Auto-generated method stub
		System.out.println("chatPlugins destroy...");
		interceptorManager.removeInterceptor(this);
	}

	@Override
	public void interceptPacket(Packet packet, Session session,
			boolean incoming, boolean processed) throws PacketRejectedException {
		// TODO Auto-generated method stub
		
	
		
		
		if(incoming && !processed){
			
			if(packet instanceof Presence){//PRESENCE
//				System.out.println("Presence: "+packet);
			}
			
			
			if(packet instanceof IQ){//IQ
//				System.out.println("IQ: "+packet);
			}
			
			
			if(packet instanceof Message){//MESSAGE
				
				Message message = (Message)packet;
				message = message.createCopy();
				
				if(null != message && null != message.getBody() ){//message需要有内容才进行处理
//					System.out.println("try_chat1:"+message.getBody());
					/*
					 * 用户发送的message有效
					 */
					analysis analy = new analysis(message);
					analy.choose();
				}
			}
		}
	}
					

}

