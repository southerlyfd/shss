package com.jianghongbo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jianghongbo.common.JsonResult;
import com.jianghongbo.common.consts.StateCodeConstant;
import com.jianghongbo.common.exception.ShssException;
import com.jianghongbo.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：taoyl
 * @date ：Created in 2019-04-22 21:04
 * @description：WebSocket
 */
@Slf4j
@ServerEndpoint("/webSocket/{userId}")
@Component
public class WebSocketController {

	//静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;
	
	//与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;
	
	//使用map对象，便于根据userId来获取对应的WebSocket
	private static ConcurrentHashMap<String,WebSocketController> websocketList = new ConcurrentHashMap<>();
	
	//接收sid
	private String userId="";
	/**
	 * 连接建立成功调用的方法*/
	@OnOpen
	public void onOpen(Session session,@PathParam("userId") String userId) {
		this.session = session;
		websocketList.put(userId,this);
		log.info("websocketList->"+ JSON.toJSONString(websocketList));
		addOnlineCount();           //在线数加1
		log.info("有新窗口开始监听:"+userId+",当前在线人数为" + getOnlineCount());
		this.userId=userId;
		JsonResult result = new JsonResult();
		try {
			result.setState(true);
			result.setErrMsg("成功");
			result.setData("当前在线人数为" + getOnlineCount());
			result.setStateCode(StateCodeConstant.SUCCESS_CODE);
			sendMessage(JSON.toJSONString(result));
		} catch (IOException e) {
			log.debug("websocket异常：", e.getStackTrace());
			throw new ShssException(StateCodeConstant.ERROR_CODE, e.getMessage());
		}
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		if(websocketList.get(this.userId)!=null){
			websocketList.remove(this.userId);
			subOnlineCount();           //在线数减1
			log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
		}
	}

	/**
	 * 收到客户端消息后调用的方法
	 *
	 * @param message 客户端发送过来的消息*/
	@OnMessage
	public void onMessage(String message, Session session) {
		log.info("收到来自窗口" + userId + "的信息:" + message);
		JSONObject messageObject = JSONObject.parseObject(message);
		String contentText = messageObject.getString("contentText");
		String toUserId = messageObject.getString("toUserId");
		try {
			if(StringUtil.isBlank(toUserId)) {
				sendtoAll(contentText);
			} else {
				sendtoUser(contentText, toUserId);
			}
		} catch (IOException e) {
			log.debug("发送消息异常：", e.getStackTrace());
			throw new ShssException(StateCodeConstant.ERROR_CODE, e.getMessage());
		}
	}

	/**
	 *
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		log.error("发生错误", error.getStackTrace());
		throw new ShssException(StateCodeConstant.ERROR_CODE, error.getMessage());
	}
	/**
	 * 实现服务器主动推送
	 */
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}

	/**
	 * 发送信息给指定ID用户，如果用户不在线则返回不在线信息给自己
	 * @param message
	 * @param sendUserId
	 * @throws IOException
	 */
	public void sendtoUser(String message,String sendUserId) throws IOException {
		if (websocketList.get(sendUserId) != null) {
			if(!userId.equals(sendUserId))
				websocketList.get(sendUserId).sendMessage( "用户" + userId + "发来消息：" + " <br/> " + message);
			else
				websocketList.get(sendUserId).sendMessage(message);
		} else {
			//如果用户不在线则返回不在线信息给自己
			sendtoUser("当前用户不在线",userId);
		}
	}

	/**
	 * 发送信息给所有人
	 * @param message
	 * @throws IOException
	 */
	public void sendtoAll(String message) throws IOException {
		for (String key : websocketList.keySet()) {
			try {
				websocketList.get(key).sendMessage(message);
			} catch (IOException e) {
				log.debug("发送消息给所有人异常：", e.getStackTrace());
				throw new ShssException(StateCodeConstant.ERROR_CODE, e.getMessage());
			}
		}
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		WebSocketController.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		WebSocketController.onlineCount--;
	}
}
