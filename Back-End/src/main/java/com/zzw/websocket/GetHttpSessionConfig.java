package com.zzw.websocket;


import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**在websocket链接中获取http链接的httpSession对象的配置*/
public class GetHttpSessionConfig extends ServerEndpointConfig.Configurator {


    //重写这个方法
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        //这个方法里面有两个参数，参数一和参数二
        //参数一就是EndPointConfig对象，参数二就是 http请求的request对象

        //通过参数一获取到httpSession对象。
        HttpSession httpSession = (HttpSession) request.getHttpSession();

        //把获取到的httpSession对象，存储到sec中就行了
        sec.getUserProperties().put(HttpSession.class.getName(),httpSession);

    }
}
