package com.jianghongbo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jianghongbo.common.JsonResult;
import com.jianghongbo.common.consts.StateCodeConstant;
import com.jianghongbo.service.api.DemoServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	//新：使用map对象，便于根据userId来获取对应的WebSocket
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
		//webSocketSet.add(this);     //加入set中
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
			log.error("websocket IO异常");
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
		log.info("收到来自窗口"+userId+"的信息:"+message);
		if(StringUtils.isNotBlank(message)){
			JSONArray list=JSONArray.parseArray(message);
			for (int i = 0; i < list.size(); i++) {
				try {
					//解析发送的报文
					JSONObject object = list.getJSONObject(i);
					String toUserId=object.getString("toUserId");
					String contentText=object.getString("contentText");
					object.put("fromUserId",this.userId);
					//传送给对应用户的websocket
					if(StringUtils.isNotBlank(toUserId)&&StringUtils.isNotBlank(contentText)){
						WebSocketController socketx=websocketList.get(toUserId);
						//需要进行转换，userId
						if(socketx!=null){
							JsonResult result = new JsonResult();
							result.setState(true);
							result.setErrMsg("失败");
							result.setData(object);
							result.setStateCode(StateCodeConstant.ERROR_CODE);
							socketx.sendMessage(JSON.toJSONString(result));
							//此处可以放置相关业务代码，例如存储到数据库
						}
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 *
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		log.error("发生错误");
		error.printStackTrace();
	}
	/**
	 * 实现服务器主动推送
	 */
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}


	/**
	 * 群发自定义消息
	 * */
    /*public static void sendInfo(String message,@PathParam("userId") String userId) throws IOException {
        log.info("推送消息到窗口"+userId+"，推送内容:"+message);
        for (WebSocketController item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if(userId==null) {
                    item.sendMessage(message);
                }else if(item.userId.equals(userId)){
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }*/

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
