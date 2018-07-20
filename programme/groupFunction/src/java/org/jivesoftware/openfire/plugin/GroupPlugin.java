package org.jivesoftware.openfire.plugin;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;



import org.jivesoftware.openfire.PresenceManager;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.openfire.event.SessionEventDispatcher;
import org.jivesoftware.openfire.event.SessionEventListener;
import org.jivesoftware.openfire.interceptor.*;
import org.jivesoftware.openfire.plugin.bean.GroupBean;
import org.jivesoftware.openfire.plugin.bean.ReqBean;
import org.jivesoftware.openfire.plugin.bean.UserBean;
import org.jivesoftware.openfire.plugin.control.GpsControl;
import org.jivesoftware.openfire.plugin.control.GrpControl;
import org.jivesoftware.openfire.plugin.control.MsgControl;
import org.jivesoftware.openfire.plugin.control.PresenceControl;
import org.jivesoftware.openfire.plugin.db.DBHelper;
import org.jivesoftware.openfire.plugin.push.PushServer;
import org.jivesoftware.openfire.plugin.util.GsonUtil;
import org.jivesoftware.openfire.session.Session;
import org.jivesoftware.openfire.user.UserManager;
import org.jivesoftware.openfire.user.UserNotFoundException;
import org.jivesoftware.util.PropertyEventDispatcher;
import org.jivesoftware.util.PropertyEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;
import org.xmpp.packet.Presence;

import com.google.gson.JsonSyntaxException;
import com.sp.data.data.cacheDATA;

public class GroupPlugin implements PacketInterceptor, Plugin,
		PropertyEventListener {

	private XMPPServer server;
	private UserManager userManager;
	private InterceptorManager interceptorManager;
	private static final Logger log = LoggerFactory.getLogger(GroupPlugin.class);
	private MotDSessionEventListener listener = new MotDSessionEventListener();
	private PresenceManager nPresenceManager;
	private String serverDomain;
	
	// Cache
	private List<GroupBean> grpList;
	
	// control
	private GrpControl grpControl;//创建、解散群组（客户端、服务器端）
	private MsgControl msgControl;//发送消息
	private GpsControl gpsControl;//推送GPS信息
	private PresenceControl presenceControl;//出席消息
	
	// pushServer
	private PushServer pushServer;
	
	//初始化缓存
	private cacheDATA cachedata;
	
	public GroupPlugin() {
		interceptorManager = InterceptorManager.getInstance();
	}

	@Override
	public void initializePlugin(PluginManager manager, File pluginDirectory) {
		log.info("加载群组插件");
		
		server = XMPPServer.getInstance();
		interceptorManager.addInterceptor(this);
		PropertyEventDispatcher.addListener(this);
		nPresenceManager = server.getPresenceManager();
		userManager = UserManager.getInstance();
		this.serverDomain = XMPPServer.getInstance().getServerInfo().getXMPPDomain();
		SessionEventDispatcher.addListener(listener);//监听用户回话
		
		// 初始化推送DAO
		this.pushServer = new PushServer(this, this.serverDomain,server);
				
				
		// 初始化控制层
		this.grpControl = new GrpControl(this.pushServer);
		this.msgControl = new MsgControl(this.pushServer);
		this.gpsControl = new GpsControl(this.pushServer);
		this.presenceControl = new PresenceControl(this.pushServer);
		
		cachedata = cacheDATA.getInstance();
		grpList = cachedata.groupandmember.list;
		
	}

	
	
	// 用户登录时推送消息
	private class MotDSessionEventListener implements SessionEventListener {
		public void sessionCreated(Session session) {
			// ignore
			System.out.println("session监听器："+session.getAddress());
		}

		public void sessionDestroyed(Session session) {
			// ignore
		}

		public void resourceBound(Session session) {
			try {
				// 登录回调方法
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void anonymousSessionCreated(Session session) {
			// ignore
		}

		public void anonymousSessionDestroyed(Session session) {
			// ignore
		}
	}

	public void interceptPacket(Packet packet, Session session,
			boolean incoming, boolean processed) throws PacketRejectedException {
		// main
		if (!incoming || processed) {
			return;
		}
		 
	     
		if (packet instanceof Message) {
			Message message = (Message)packet;
			
			if(null != message && null != message.getBody() ){//message需要有内容才进行处理
				
				//session
				
//				 Message msg = new Message();
//			     msg.setType(Message.Type.normal);
//			     msg.setFrom(this.serverDomain);
//			     msg.setBody("Hello from server");
//			     msg.setBody(message.getBody());
//			     session.process(msg);
				
				//getRoutingTable
				
//				 if(message.getBody().equals("test")){
//					    Message message1 = new Message();  
//					    message1.setFrom(message.getTo());  
//					    message1.setTo(message.getFrom());  
//					    message1.setBody("receive test");  
//					  
//					   
//					  
//					    server.getRoutingTable().routePacket(message.getFrom(), message1, true);  
//				 }
			     
			     
				JID from = message.getFrom();
				JID to = message.getTo();
				String subjectAction = message.getSubject();
				if(null != subjectAction){
					System.out.println("---- package ----");
					String logTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
					System.out.println(logTime+"\tclient-->server:"+message.toXML());
					String express = message.getBody();//Codec.decode(msg.getBody());
					
					ReqBean reqBean = null;
					
					try {
						reqBean = GsonUtil.gson.fromJson(express, ReqBean.class);
						reqBean.setMsgLinkId(message.getID());
						reqBean.setFrom(message.getFrom().toString());
		//				System.out.println(reqBean.getU_list().getClass().getName());
					} catch (JsonSyntaxException e1) {
						System.out.println("parse error");
						e1.printStackTrace();
					}
					try {
						if ("grp_client_kick".equals(subjectAction)) {	//	添加/踢出群员
							System.out.println("grp_client_kick");
							grpControl.editGrpInfo(reqBean,session, grpList);
						}
						
						else if ("grp_client_create_del".equals(subjectAction)) {	//	创建/解散群组                                     ******
							System.out.println("grp_client_create_del");
							
							grpControl.createDelGrp(reqBean, session, grpList);
						}
						
						else if ("grp_client_pre".equals(subjectAction)) {	//	客户端出席消息
							System.out.println("grp_client_pre");
							presenceControl.updatePresenceCache(reqBean,session, grpList);
						}
						
						else if ("grp_client_msg".equals(subjectAction)) {	//	客户端发送消息
							System.out.println("grp_client_msg");
							msgControl.pushMsg(reqBean, session, grpList);
						}
						
						else if ("grp_client_gps".equals(subjectAction)) {	//	客户端GPS信息
							System.out.println("grp_client_gps");
							gpsControl.getGps(reqBean,session, grpList);
						}
					} 
					
					catch (Exception e) {
						System.out.println("do service error");
						e.printStackTrace();
					}
				}
			
			}
		}
	}

	
	
	// 判断用户是否在线
	public boolean isAlive(String jid){
		try {
			System.out.println("jid:"+jid);
			
			//JID toJid = new JID(jid+"@"+this.serverDomain+"/family");
			Presence presence = nPresenceManager.getPresence(userManager.getUser(jid));
			
			if (null!=presence) {//presence 不为空 即在线
				return true;
			}else {
				return false;
			}
		} catch (UserNotFoundException e) {
			//e.printStackTrace();
			return false;
		}
	}
	
	
	public void destroyPlugin() {
		log.info("销毁群组插件");
		server = null;
		interceptorManager.removeInterceptor(this);
		PropertyEventDispatcher.removeListener(this);
		SessionEventDispatcher.removeListener(listener);
	}

	@Override
	public void propertyDeleted(String property, Map<String, Object> params) {

	}

	@Override
	public void propertySet(String property, Map<String, Object> params) {

	}

	@Override
	public void xmlPropertyDeleted(String property, Map<String, Object> params) {

	}

	@Override
	public void xmlPropertySet(String property, Map<String, Object> params) {

	}
	
}