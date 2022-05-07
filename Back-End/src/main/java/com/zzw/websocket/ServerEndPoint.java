package com.zzw.websocket;


import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/ws/api/equipment")/** 1、EndPoint资源的路径*/
@Component  /** 5、把这个类交给SpringBoot管理，不过springBoot并不会在ioc容器中生成这个类的对象，所以要加一个配置类*/
public class ServerEndPoint {

    //用来存储，每一个客户端对应的,ServerEndPoint对象实例，该容器仅此一份所以用static或
    public static Map<String,ServerEndPoint> onLinePoints = new ConcurrentHashMap<>();  //使用这个并发编程安全的子实现类
    //声明一个Session对象用来和客户端进行通信
    private Session session;
    //声明自己的唯一id捏
    private String LinkName;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getLinkName() {
        return LinkName;
    }

    public void setLinkName(String linkName) {
        LinkName = linkName;
    }

    //声明一个http连接产生的httpSession对象，因为我们可能会在这个对象中存储一些东西，所以在这里也要定义一个，方便数据的交互
//    private HttpSession httpSession;

    /** 2、链接建立时被调用*/
    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig){

        this.session = session;  //将局部的session赋值给成员对象

//        HttpSession httpSession = (HttpSession) endpointConfig.getUserProperties().get(HttpSession.class.getName());
//        this.httpSession = httpSession;
//
//        String linkName = (String) httpSession.getAttribute("linkName");

//        onLinePoints.put("LinkTest01",this);
        System.out.println("连接成功");

    }

    /** 3、接收到客户端发送的数据时被调用*/
    @OnMessage
    public void onMessage(String message,Session session){
        System.out.println(message);

        //使用正则表达式判断传过来的是不是链接名，如果是的话存储这个链接对象
        if(message.matches("^\\w+-Linking$")){
            String linkingName = message;
            this.LinkName = linkingName;
            onLinePoints.put(linkingName,this);
        }

    }

    /** 4、链接关闭时被调用*/
    @OnClose
    public void onClose(Session session){
        //本链接被关闭后，就调用这个删除集合中的该对象捏
        onLinePoints.remove(this.LinkName);
    }

//    @OnError
//    public void onError(Session session){
//        System.out.println("websocket error!");
//    }

}
