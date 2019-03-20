package com.websocket.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.websocket.chat.biz.ChatBiz;
import com.websocket.chat.biz.ChatBizImpl;
import com.websocket.chat.dto.ChatDto;
import com.websocket.file.biz.FileBiz;
import com.websocket.file.dto.FileDto;

public class ChatWebSocketHandler extends TextWebSocketHandler {

	private Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);
	
	@Autowired
	private ChatBiz biz;
	
	@Autowired
	private FileBiz fileBiz;
	
	private Map<String,WebSocketSession> users;
	
	/*
	 * 클라이언트가 연결되면, 클라이언트의 관련된 WebSocketSession을 users 맵에 저장한다.
	 * 이 users 맵은 채팅 메시지를 연결된 전체 클라이언트에 전달할 때 사용
	 */
	
	public ChatWebSocketHandler() {
		users = new ConcurrentHashMap<String,WebSocketSession>();
    }
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info(session.getId()+"(IP)"+session.getRemoteAddress().getHostName() + " 연결됨");
		users.put(session.getId(), session);
		
		// CHAT 테이블 전체값 List에 가져오기
		List<ChatDto> list = biz.selectList();
		String sendmessage = "";
		// 가져온 값 종류별로 전달.
		for (ChatDto dto : list) {
			if (dto.getChattype().equals("msg")){
				sendmessage = "{\"type\" :\"msg\","
							+ "\"nickname\" :\"sadf\","
							+ "\"message\" :\""+dto.getChatcontent()+"\","
							+ "\"randomcolor\" :\""+dto.getChatcolor()+"\"}";
			} else if (dto.getChattype().equals("video")){
				sendmessage = "{\"type\" :\"video\","
							+ "\"nickname\" :\"sadf\","
							+ "\"url\" :\""+dto.getVideourl()+"\","
							+ "\"randomcolor\" :\""+dto.getChatcolor()+"\"}";
			} else if (dto.getChattype().equals("filedata")){
				FileDto selectFileDto = fileBiz.FileSelectOne(dto.getFileno());
				sendmessage = "{\"type\" :\"filedata\","
							+ "\"nickname\" :\"sadf\","
							+ "\"filename\" :\""+ selectFileDto.getFilename()+"\","
							+ "\"newFileName\" :\""+ selectFileDto.getFilesavename()+"\","
							+ "\"randomcolor\" :\""+dto.getChatcolor()+"\"}";
			}
			session.sendMessage(new TextMessage(sendmessage));
		}
		session.sendMessage(new TextMessage("{\"type\":\"inout\", \"message\":\"이전 글 불러오기 완료\"}"));
	}
	
	/*
	 * 클라이언트가 전송한 메시지를 users 맵에 보관한 전체 WebSocketSession에 다시 전달한다.
	 * 클라이언트는 메시지를 수신하면 채팅 영역에 보여주도록 구현.
	 * 특정 클라이언트가 채팅 메시지를 서버에 보내면 전체 클라이언트는 다시 그 메시지를 받아서 화면에 보여주게 된다.
	 */

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		logger.info(session.getId()+"(IP)"+session.getRemoteAddress().getHostName() + "로부터 메시지 수신 : " + message.getPayload());
		
		// 웹소켓을 통해 전달된 데이터값 jsonobject로 전환
		JSONParser jsonparser = new JSONParser();
		JSONObject jsonObj = null;
		jsonObj = (JSONObject)jsonparser.parse(message.getPayload());

		// 값 없을 시 null값 추가
		if((String)jsonObj.get("url") == null) {
			jsonObj.put("url", "");
		}
		if ((String)jsonObj.get("message") == null) {
			jsonObj.put("message", "");
		}		
		
		// DB CHAT 테이블에 저장(insert)
		if (!jsonObj.get("type").equals("inout")) {
			int fileno = 0;
			if (jsonObj.get("type").equals("filedata")) {
				fileno = Integer.parseInt((String)jsonObj.get("fileno"));
			}
			ChatDto dto = new ChatDto(0, 1, fileno , (String)jsonObj.get("type"), (String)jsonObj.get("message"), (String)jsonObj.get("url"), (String)jsonObj.get("randomcolor"));
			int insert_res = biz.insert(dto);
		}
		
		for(WebSocketSession s : users.values()) {
			s.sendMessage(message);
			logger.info(s.getId() + "에 메시지 발송 : " + message.getPayload());
		}
	}	

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		logger.info(session.getId() + " 익셉션 발생 : " + exception.getMessage());
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		logger.info(session.getId() + " 연결 종료됨");
		users.remove(session.getId());
	}
	
}
