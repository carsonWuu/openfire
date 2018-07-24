## 项目一、try_chat/弃用
## 项目二、groupFunction:聊天插件 
> 该聊天插件使用openfire作为桥梁，建立服务器与客户端的连接，使用xml格式作为数据传输。该项目只使用Message进行数据传输，subject作为功能识别（下面的是一个功能点在这里进行判断），所有的信息写在body中，Message的头部用到的信息较少。服务器端不使用smack。
```
<message id="Kowrj-5" to="add1@192.168.1.101" from="add2@192.168.1.101/Smack" type="chat">
  <subject>grp_client_create_del</subject>
  <body>{"act":1,"grp_id":"group_1","u_list":[{"u_id":"user1","type":1},{"u_id":"user2","type":0},{"u_id":"333","type":0}],"master":"user1"}</body>
  <thread>s4F2W0</thread>
</message>
```
>> grp_client_create_del 为增加删除群组功能点，act判断增加或是删除，grp_id是群组号（删除时候才有），u_list是添加入群组的用户信息（u_id为用户id，type判断是否群主1为群主） 

### groupFunction 功能：
1. 增加群组
2. 删除群组

> 每个用户
3. 增加群成员
> 群主：
4. 踢出群成员

> 每个用户：
5. 创建群组
> 群主：
6. 解散群组

> 群组新成员：
7. 进入群组
群组老成员：
8. 离开群组

> 用户向服务器推送信息，服务器向接收方推送信息。加密base64：

9. 广播消息：单聊
> 用户向服务器推送信息，服务器向所有接收方推送信息。加密base64：
10. 广播消息：群聊

> 用户向服务器推送GPS信息，服务器向接收方推送GPS信息。

11. GPS信息推送


