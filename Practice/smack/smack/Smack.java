package com.test.smack;

import java.util.Scanner;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

/**
 * 一个简单的XMPPConnection示例
 */
public class Smack {
    public static XMPPConnection con;
    public static Chat newChat;
    public static ChatManager chatmanager;

    public static void main(String[] args) throws InterruptedException {
        try {
            // 配置域和端口号，域可以换成IP地址
            ConnectionConfiguration config = new ConnectionConfiguration(
                    "192.168.1.101", 5222);
            // 新建一个XMPPConnection对象
            con = new XMPPConnection(config);

            // 连接服务器
            con.connect();
            // 用户登录
            con.login("wcs", "123456");
            // 是否已经通过身份验证
            System.out.println("Authenticated = " + con.isAuthenticated());

            addListener();
            // 获取一个ChatManager对象
            chatmanager = con.getChatManager();
            newChat = chatmanager.createChat("admin@192.168.1.101",
                    new MessageListener() {
                        public void processMessage(Chat chat, Message message) {
                            if(message.getBody() != null){
                            	System.out.println("I'm sending: "+ message.getBody());
                            }
                                    
                        }
                    });
            Scanner input = new Scanner(System.in);
            while (true) {
              String message = input.nextLine(); 
              newChat.sendMessage(message);
            }
        } catch (XMPPException e) {
            e.printStackTrace();
        } finally {
            // 让线程休眠 然后再关闭连接
            Thread.sleep(20000);
            con.disconnect();
        }
    }

    private static void addListener() {//监听接受者发送的信息
    	
        // 包的过滤器
        PacketFilter filterMessage = new PacketTypeFilter(Message.class);
        // 创建包的监听器
        PacketListener myListener = new PacketListener() {
            public void processPacket(Packet packet) {
                // 以XML格式输出接收到的消息
                System.out.println(packet.toXML());
                System.out.println("From: " + packet.getFrom());
               if(((Message) packet).getBody() !=null) System.out.println("Body: " + ((Message) packet).getBody());

                try {
                    // 尝试发送消息给服务器
                    newChat.sendMessage("hi again");
                } catch (XMPPException e) {
                    e.printStackTrace();
                }
            }
        };
        // 给连接注册一个包的监听器
        con.addPacketListener(myListener, filterMessage);
    }
}