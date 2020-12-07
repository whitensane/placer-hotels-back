package kz.placer.hotels.notify;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;

class WebSocketHandler extends AbstractWebSocketHandler{

	@Override
	protected void handleTextMessage (WebSocketSession session, TextMessage message) throws IOException{
		String msg = message.getPayload();
		// Send back unique message depending on the id received from the client
		session.sendMessage(new TextMessage(msg));
	}
}