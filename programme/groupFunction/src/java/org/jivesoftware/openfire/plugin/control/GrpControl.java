package org.jivesoftware.openfire.plugin.control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jivesoftware.openfire.plugin.bean.GroupBean;
import org.jivesoftware.openfire.plugin.bean.RecvBean;
import org.jivesoftware.openfire.plugin.bean.ReqBean;
import org.jivesoftware.openfire.plugin.bean.RespBean;
import org.jivesoftware.openfire.plugin.bean.UserBean;
import org.jivesoftware.openfire.plugin.db.DBHelper;
import org.jivesoftware.openfire.plugin.push.PushServer;
import org.jivesoftware.openfire.plugin.util.GsonUtil;
import org.jivesoftware.openfire.session.Session;

import com.sp.affair.data.analysis.function.addMember.addMember;
import com.sp.affair.data.analysis.function.createGroup.createGroup;
import com.sp.affair.data.analysis.function.delMember.delMember;
import com.sp.affair.data.analysis.function.deleteGroup.deleteGroup;
import com.sp.affair.data.analysis.function.factoryInter.Factory;
import com.sp.message.returnMessage.returnMessage;

public class GrpControl {
	private PushServer pushServer;
	
	public GrpControl(PushServer pushServer){
		this.pushServer = pushServer;
	}
	
	
	/**
	 * 编辑群/组
	 * @param reqBean
	 * @param session
	 * @param grpList
	 * @return
	 */
	public int editGrpInfo(ReqBean reqBean,Session session,List<GroupBean> grpList)throws Exception{
		int act = reqBean.getAct();
		
		RecvBean ret = null;
		Factory addORdel = null;
		
		
		if(act == 1){//添加群组
			addORdel = new addMember(reqBean);
			ret = addORdel.doAction();
			addORdel.cout();
		}
		else if(act == 2){//删除群组
			addORdel = new delMember(reqBean);
			ret = addORdel.doAction();
			addORdel.cout();
		}
		else{
			ret = new RecvBean(5, "Act错误");
		}
		
		pushServer.recvClientMsg("grp_client_kick", reqBean, ret, session);//处理反馈，通知操作人
		
		if(ret.getCode() ==0){
			/*创建、解散成功
			 * ①反馈信息给操作用户
			 * ②向被操作用户发送信息
			 */
		
			pushServer.pushToAnyone(addORdel.storeGroup, "grp_server_kick", "添加、踢人用户成功");//通知被操作人
			
		}
		
		
		return 5;
	}
	
	/**
	 * 创建/解散 群/组
	 * @param reqBean
	 * @param session
	 * @param grpList
	 * @return
	 * @throws Exception
	 */
	public int createDelGrp(ReqBean reqBean,Session session,List<GroupBean> grpList)throws Exception{
		int act = reqBean.getAct();
		
		RecvBean ret = null;
		Factory createORdel = null;
		
		
		if(act == 1){//添加群组
			createORdel = new createGroup(reqBean);
			ret = createORdel.doAction();
			createORdel.cout();
		}
		else if(act == 2){//删除群组
			createORdel = new deleteGroup(reqBean);
			ret = createORdel.doAction();
			createORdel.cout();
		}
		else{
			ret = new RecvBean(5, "Act错误");
		}
		
		pushServer.recvClientMsg("grp_client_create_del", reqBean, ret, session);//处理反馈，通知操作人
		
		if(ret.getCode() ==0){
			/*创建、解散成功
			 * ①反馈信息给操作用户
			 * ②向被操作用户发送信息
			 */
		
			pushServer.pushToAnyone(createORdel.storeGroup, "grp_server_create_del", "创建或解散操作成功");//通知被操作人
			
		}
		
		
		return 0;
	}
	
	
	/**
	 * 推送创建通知
	 * @param reqBean
	 * @param grpBean
	 */
	public void pushCreMsg(ReqBean reqBean,GroupBean grpBean){
		try {
			// 通知除自己之外的所有人
			RespBean respBean = new RespBean();
			respBean.setAct(reqBean.getAct());
			respBean.setGrp_id(grpBean.getGrpId());
			respBean.setU_id(reqBean.getU_id());
			respBean.setAlias(reqBean.getAlias());
			respBean.setMaster(reqBean.getMaster());
			respBean.setU_list(reqBean.getU_list());
			for (UserBean userBean : grpBean.getUserList()) {
				if (!reqBean.getMaster().equals(userBean.getU_id())) {
					pushServer.pushDetail(userBean.getU_id(), "grp_server_create_del", GsonUtil.gson.toJson(respBean));
				}
			}
			if (!reqBean.getMaster().equals(grpBean.getMasterId())) {
				pushServer.pushDetail(grpBean.getMasterId(), "grp_server_create_del", GsonUtil.gson.toJson(respBean));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("push error");
		}
	}
	
	/**
	 * 推送解散通知
	 * @param reqBean
	 * @param grpBean
	 */
	public void pushDelMsg(ReqBean reqBean,GroupBean grpBean){
		try {
			// 通知除自己之外的所有人
			RespBean respBean = new RespBean();
			respBean.setAct(reqBean.getAct());
			respBean.setGrp_id(grpBean.getGrpId());
			respBean.setAlias(reqBean.getAlias());
			respBean.setMaster(reqBean.getMaster());
			for (UserBean userBean : grpBean.getUserList()) {
				if (!reqBean.getMaster().equals(userBean.getU_id())) {
					pushServer.pushDetail(userBean.getU_id(), "grp_server_create_del", GsonUtil.gson.toJson(respBean));
				}
			}
			if (!reqBean.getMaster().equals(grpBean.getMasterId())) {
				pushServer.pushDetail(grpBean.getMasterId(), "grp_server_create_del", GsonUtil.gson.toJson(respBean));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("push error");
		}
	}
	
	
	/**
	 * 推送群通知到各个群员
	 * @param reqBean
	 * @param grpBean
	 */
	public void pushEditMsg(ReqBean reqBean,GroupBean grpBean){
		try {
			// 通知除自己之外的所有人
			RespBean respBean = new RespBean();
			respBean.setAct(reqBean.getAct());
			respBean.setGrp_id(grpBean.getGrpId());
			respBean.setU_id(reqBean.getU_id());
			respBean.setMaster(reqBean.getMaster());
			for (UserBean userBean : grpBean.getUserList()) {
				if (!reqBean.getMaster().equals(userBean.getU_id())) {
					pushServer.pushDetail(userBean.getU_id(), "grp_server_kick", GsonUtil.gson.toJson(respBean));
				}
			}
			if (!reqBean.getMaster().equals(grpBean.getMasterId())) {
				pushServer.pushDetail(grpBean.getMasterId(), "grp_server_kick", GsonUtil.gson.toJson(respBean));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("push error");
		}
	}
}
