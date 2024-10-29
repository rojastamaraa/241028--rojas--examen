package com.websocket.app.service;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatHandler extends TextWebSocketHandler{
	private static final Logger LOGGER = LoggerFactory.getLogger(ChatHandler.class);
	
	private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<WebSocketSession>();
	private final CopyOnWriteArrayList<TextMessage> msjs = new CopyOnWriteArrayList<TextMessage>();
	
	
	@Override 
	public void afterConnectionEstablished(WebSocketSession session) throws Exception{
		sessions.add(session);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
		sessions.remove(session);
	}
	
	@Override 
	public void handleTextMessage (WebSocketSession session, TextMessage message) throws Exception {
		String msj = message.getPayload();
		
		if(msj.contains("WebSocket")) {
			session.sendMessage(new TextMessage("WebSocket es un prtocolo que permite la comunicaion bidireccional en tiempo real entre un cliente y el servidor. Puede correr en el puerto 8080"));
		}
		
		for(WebSocketSession webSocketSession : sessions) {
			webSocketSession.sendMessage(message);
		}
	}
}
