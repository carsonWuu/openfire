package com.test.smack.cuid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.RosterListener;
public class userCuid {
	XMPPConnection connection = new openConnection().getconnection();
	String username="";//用户名
	String psw="";//密码
	Map attr;//用户基本信息
	Roster roster;
	Presence presence = new Presence(Presence.Type.available);
	public userCuid(){
		
	}
	public userCuid setUser(String username,String psw){//
		this.username= username;
		this.psw=psw;
		
		return this;
	}
	public userCuid(String username,String psw){
		super();
		this.username=username;
		this.psw = psw;
		this.attr=new HashMap();
		this.attr.put("name","add");
		this.attr.put("email","carsonwuu@163.com");
		this.attr.put("dept","unknown");
//		System.out.println(connection.getAccountManager());
	}
	
	public boolean create(){//create user
		boolean ret=true;
		try{
			connection.getAccountManager().createAccount(this.username,this.psw,this.attr);
		}
		catch(Exception e){
			ret = false;
		}
		return ret;
	}
	public boolean delete(){//delete user himself
		boolean ret= true;
		try{
			connection.getAccountManager().deleteAccount();
		}catch(Exception e){
			ret = false;
		}
		return ret;
	}
	public boolean updatePASSWORD(String newPasw){//update password
		boolean ret= true;
		try{
			connection.getAccountManager().changePassword(newPasw);
		}catch(Exception e){
			ret = false;
		}
		return ret;
	}
	public boolean login(){
		boolean ret= true;
		try{
			connection.login(this.username,this.psw);
			roster= connection.getRoster();
			roster.addRosterListener(new RosterListener(){

				@Override
				public void entriesAdded(Collection<String> arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void entriesDeleted(Collection<String> arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void entriesUpdated(Collection<String> arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void presenceChanged(Presence arg0) {
					// TODO Auto-generated method stub
					//System.out.println("Presence changed: "+arg0);
					
				}
				
			});
			Presence presence= new Presence(Presence.Type.available);
			presence.setMode(Presence.Mode.available);
			connection.sendPacket(presence);
		}
		catch(Exception e){
			ret = false;
		}
		return ret;
	}
	public boolean addFriends(String user){//add a friends like:[admin@192.168.1.101]
		return addFriends(user,"");
	}
	public boolean addFriends(String user,String nickname){
		return addFriends(user,nickname,new String[]{"Friends"});
	}
	public boolean addFriends(String user,String nickname,String []s){
		boolean ret= true;
		roster.setSubscriptionMode(Roster.SubscriptionMode.accept_all);
		try{
			roster.createEntry(user, nickname, s);
		}
		catch(Exception e){
			ret = false;
		}
		return ret;
	}
	public HashMap getFriends(){//get all friends;
		return getFriends("");
	}
	public HashMap getFriends(String s){
		HashMap hashmap = new HashMap();
		roster = connection.getRoster();
		Collection collection =roster.getEntries();
		int i=1;
		for(Iterator it = collection.iterator();it.hasNext();i++){
			hashmap.put(i,it.next());
		}
		System.out.println(hashmap);
		return hashmap;
	}
	public boolean deleteFriend(String name){//delete one friend
		boolean ret = true;
		roster = connection.getRoster();
		try{
			roster.removeEntry(roster.getEntry(name));
		}
		catch(Exception e){
			ret = false;
		}
		return ret;
		
	}
//    public void getAllEntries(){//get all friends
//        roster=connection.getRoster();  
//        System.out.println("\""+this.username+"\"的好友：");  
//        Iterator it = roster.getEntries().iterator();  
//        
//        while(it.hasNext()){  
//            
//            System.out.println(it.next());  
//        }     
//          
//    }
	
}
