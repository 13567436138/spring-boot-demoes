package com.mark.demo.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/*
*hxp(hxpwangyi@126.com)
*2017年10月12日
*
*/
public class SystemWebSocketHandler implements WebSocketHandler{
	private Logger log = LoggerFactory.getLogger(SystemWebSocketHandler.class);  
    private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();
    
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		users.remove(session);  
        log.debug("afterConnectionClosed" + closeStatus.getReason());  
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("ConnectionEstablished");  
        log.debug("ConnectionEstablished");  
        users.add(session);  
          
        session.sendMessage(new TextMessage("connect"));  
        session.sendMessage(new TextMessage("new_msg"));
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		System.out.println("handleMessage" + message.toString());  
        log.debug("handleMessage" + message.toString());  
        //sendMessageToUsers();  
        session.sendMessage(new TextMessage(new Date() + "")); 
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if(session.isOpen()){  
            session.close();  
        }  
        users.remove(session);  
          
        log.debug("handleTransportError" + exception.getMessage()); 
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
	
	public void sendMessageToUsers(TextMessage message) {  
        for (WebSocketSession user : users) {  
            try {  
                if (user.isOpen()) {  
                    user.sendMessage(message);  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
}
