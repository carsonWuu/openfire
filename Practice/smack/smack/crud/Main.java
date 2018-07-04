package com.test.smack.crud;

public class Main {
	public static void main(String []args){
		userCrud user1 = new userCrud("admin","123456");
		userCrud user2 = new userCrud("wcs","123456");
		user1.login();
		user2.login();
		
		user1.getFriends();
		user2.getFriends();
		
		user2.addFriends("add1@192.168.1.101");
//		user1.setUser("add1", "123456").create();
		userCrud user3 = new userCrud("add1","123456");
		user3.login();
		user3.addFriends("admin@192.168.1.101");
		
//		user3.addFriends("admin");
		user3.getFriends();
		
//		user3.updatePASSWORD("123456");
		//user3.updatePASSWORD("add1");
		//user1.delete();
		
		//user1.setUser("add2", "123465").create();
		
	}
}
