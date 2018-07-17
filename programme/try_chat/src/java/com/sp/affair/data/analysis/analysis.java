package com.sp.affair.data.analysis;

import com.sp.affair.data.Json.Json;
import com.sp.affair.data.analysis.function.ANALYSIS.Function;
import com.sp.affair.data.analysis.function.createGroup.createGroup;
import com.sp.affair.data.analysis.function.deleteGroup.deleteGroup;
import com.sp.affair.data.analysis.function.factoryInter.factory;
import com.sp.data.groupAndmember.member;
import com.sp.message.returnMessage.returnMessage;

import java.util.*;

import org.jivesoftware.openfire.message.messageAnalysis;
import org.xmpp.packet.Message;

import net.sf.json.JSONObject;
/*
 * 解析客户端传上来的数据
 */
public class analysis {
	public messageAnalysis message;
	public Function function;
	public analysis(Message message){
		this.message = new messageAnalysis(message);
		function  = new Function(this.message);
	}
	public void choose(){
		String subject = this.message.getSubject();
		if(null != subject){
			
			switch(subject){
			
			case "grp_client_kick": 
				function.addORdelMember();
				break;//client:添加/踢出群员
				
			case "grp_server_kick": 
//				doAction(subject,message);
				break;//server:添加/踢出群员
			
			
			case "grp_client_create_del":
				function.addORdelGroup();
				break;//client:创建/解散群组
			
			case "grp_server_create_del_rec": 
//				doAction(subject,message);
				break;//server:创建/解散群组
				
			case "grp_client_pre":
//				doAction(subject,message);
				break;//client:进入/离开群组
			
			case "grp_client_msge":
//				doAction(subject,message);
				break;//client:群聊/单聊
				
			case "grp_server_msg": 
//				doAction(subject,message);
				break;//广播：server:创建/解散群组	
				
			case "grp_client_gps":
//				doAction(subject,message);
				break;//GPS信息推送：client:GPS推送
				
			case "grp_server_gps":
//				doAction(subject,message);
				break;//GPS信息推送：server:GPS推送	
			
			default:break;
				
			}
			
		}
	}
	
}
