//package com.sp.affair.data.analysis.function.ANALYSIS;
//
//import net.sf.json.JSONObject;
//
//import org.jivesoftware.openfire.message.messageAnalysis;
//
//import com.sp.affair.data.analysis.function.addMember.addMember;
//import com.sp.affair.data.analysis.function.createGroup.createGroup;
//import com.sp.affair.data.analysis.function.delMember.delMember;
//import com.sp.affair.data.analysis.function.deleteGroup.deleteGroup;
//import com.sp.affair.data.analysis.function.factoryInter.factory;
//import com.sp.message.returnMessage.returnMessage;
//
//public class Function {
//	public messageAnalysis message;
//	public Function(messageAnalysis message){
//		this.message = message;
//	}
//	public void addORdelGroup(){
//		JSONObject json = this.message.getBody2Json();
//		int act= this.message.getACT();
//		returnMessage retmessage = null;
//		if(act == 1){
//			factory creategroup = new createGroup(json);
//			retmessage = creategroup.doAction();
//			creategroup.cout();
//		}
//		else if(act == 2){
//			factory deletegroup = new deleteGroup(json);
//			retmessage = deletegroup.doAction();
//			deletegroup.cout();
//		}
//		else{
//			retmessage = new returnMessage(5, "Act错误");
//		}
////		System.out.println(retmessage.getRET()+"\t"+retmessage.getMESSAGE());
//	}
//	
//	public void addORdelMember(){
//		JSONObject json = this.message.getBody2Json();
//		int act = this.message.getACT();
//		returnMessage retmessage = null;
//		if(act == 1){//添加
//			factory addmember = new addMember(json);
//			retmessage = addmember.doAction();
//			addmember.cout();
//		}
//		else if(act == 2){//踢出
//			factory delmember = new delMember(json);
//			retmessage = delmember.doAction();
//			delmember.cout();
//		}
//		else{//act 错误
//			retmessage = new returnMessage(5, "Act错误");
//		}
//		System.out.println(retmessage);
//		
//	}
//}
