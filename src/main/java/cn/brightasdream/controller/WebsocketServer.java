package cn.brightasdream.controller;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import cn.brightasdream.common.QueryProcessor;

@ServerEndpoint("/websocket/search")
@Component
public class WebsocketServer {

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebsocketServer.applicationContext = applicationContext;
    }


    private Session session;

    private QueryProcessor processor;
    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        
    }

    @OnClose
    public void onClose() throws IOException {
        
    }

    @OnMessage  
    public void onMessage(String message) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject object = (JSONObject) jsonParser.parse(message);
            String open = (String)object.get("firstOpen");
            if(open.equals("yes")){ // 如果是第一次连接，加载
                processor = new QueryProcessor((String)object.get("content"));
                this.session.getBasicRemote().sendText("{\"brightnoconfloaded\":\"true\"}");
            }else{ //其他时候发送消息，查询
            // QueryProcessor processor = applicationContext.getBean(QueryProcessor.class);
            // IdAndPersonSchema schema = applicationContext.getBean(IdAndPersonSchema.class);
            
                String json = mapper.writeValueAsString(processor.getMappedPerson((String)object.get("content")));
            //String json = mapper.writeValueAsString(schema.getIdPeronSchema());
                this.session.getBasicRemote().sendText(json);
        
            }
        } catch (ParseException e) {
            e.printStackTrace();
		}
    }  
  
    @OnError  
    public void onError(Session session, Throwable error) {  
        error.printStackTrace();  
    }  

}