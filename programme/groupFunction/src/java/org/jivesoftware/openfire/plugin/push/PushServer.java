package org.jivesoftware.openfire.plugin.push;

import java.text.SimpleDateFormat;

import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.plugin.GroupPlugin;
import org.jivesoftware.openfire.plugin.bean.GroupBean;
import org.jivesoftware.openfire.plugin.bean.RecvBean;
import org.jivesoftware.openfire.plugin.bean.ReqBean;
import org.jivesoftware.openfire.plugin.bean.RespBean;
import org.jivesoftware.openfire.plugin.util.GsonUtil;
import org.jivesoftware.openfire.session.Session;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;

public class PushServer {
	private GroupPlugin plugin;
	private String serverDomain;
	private XMPPServer server;
	
	public PushServer(GroupPlugin plugin,String serverDomain,XMPPServer server){
		this.plugin = plugin;
		this.serverDomain = serverDomain;
		this.server = server;
	}
	



	/**
	 * 客户端消息回执
	 * @param subjectAction
	 * @param reqBean
	 * @param respBean
	 * @param groupBean
	 * @param session
	 */
	public void recvClientMsg(String subjectAction,ReqBean reqBean,RecvBean recvBean,Session session){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Message message = new Message();
		message.setFrom(this.serverDomain);
//		System.out.println(this.serverDomain);
//		message.setID(System.currentTimeMillis()+"");
		message.setID(reqBean.getMsgLinkId());
		message.setType(Message.Type.normal);
		//RecvBean为处理反馈信息code/msg/grp_id
	
		if ("grp_client_kick".equals(subjectAction)) {	//	添加/踢出群员
			message.setSubject("grp_server_kick_rec");
		}else if ("grp_client_create_del".equals(subjectAction)) {	//	创建/解散群组
			message.setSubject("grp_server_create_del_rec");
			
		}else if ("grp_client_pre".equals(subjectAction)) {	//	客户端出席消息
			message.setSubject("grp_server_pre_rec");
		}else if ("grp_client_msg".equals(subjectAction)) {	//	客户端发送消息
			message.setSubject("grp_server_msg_rec");
		}else if ("grp_client_gps".equals(subjectAction)) {	//	客户端GPS信息
			message.setSubject("grp_server_gps_rec");
		}
//		message.setBody(GsonUtil.gson.toJson("hello client"));
		message.setBody(GsonUtil.gson.toJson(recvBean));
		System.out.println("server-->client:"+message.toXML());
		
//		server.getRoutingTable().routePacket(new JID(reqBean.getFrom()), message , true);
		session.process(message);
		
		
	}


	/**
	 * 推送到app端公用方法
	 * @param plugin
	 * @param to
	 * @param subject
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public boolean push(final String to,final String subject,final String body) {
		// 先查询客户端是否在线
		boolean isAlive = this.plugin.isAlive(to);
		if(isAlive){
			// 推送到客户端
//			plugin.pushCommand(to,subject,Codec.codec(body));
			pushDetail(to,subject,body);
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 推送到app端
	 * @param to
	 * @param subject
	 * @param body
	 * @throws Exception
	 */
	public void pushDetail(final String to,final String subject,final String body)  {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Message message = new Message();
		message.setFrom(this.serverDomain);
		message.setTo(to);
		message.setID(System.currentTimeMillis()+"");
		message.setType(Message.Type.normal);
		JID jid = null;
//		jid = new JID("15818724679@"+this.serverDomain+"/Spark 2.6.3");
		jid = new JID(to+"@"+this.serverDomain);
		message.setSubject(subject);
		message.setBody(body);
		
		System.out.println("server-->client:"+message.toXML());
		
		server.getRoutingTable().routePacket(jid, message, true);
	}
	
}
