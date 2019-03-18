package com.brzhang.dalipush;

import android.app.Notification;
import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MyMessageReceiver extends MessageReceiver {
    // 消息接收部分的LOG_TAG
    public static final String REC_TAG = "receiver";
    Gson gson = new Gson();

    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        // TODO 处理推送通知
        Log.e("DalipushPlugin", "Receive notification, title: " + title + ", summary: " + summary + ", extraMap: " + extraMap);
        Log.e("DalipushPlugin", "eventSink: " + DalipushPlugin.getInstance().getEventSink());
        DalipushPlugin.getInstance().getEventSink().success(gson.toJson(new Notification(title, summary, extraMap), Notification.class));
    }

    @Override
    public void onMessage(Context context, CPushMessage cPushMessage) {
        Log.e("DalipushPlugin", "onMessage, messageId: " + cPushMessage.getMessageId() + ", title: " + cPushMessage.getTitle() + ", content:" + cPushMessage.getContent());
        Log.e("DalipushPlugin", "eventSink: " + DalipushPlugin.getInstance().getEventSink());
        Message message = new Message(cPushMessage);
        DalipushPlugin.getInstance().getEventSink().success(gson.toJson(message, Message.class));
    }

    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        Log.e("MyMessageReceiver", "onNotificationOpened, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
        Log.e("DalipushPlugin", "eventSink: " + DalipushPlugin.getInstance().getEventSink());
        Map<String,String> parm= new HashMap<>();
        parm.put("type","NotificationOpened");
        parm.put("title",title);
        parm.put("summary",summary);
        parm.put("extraMap",extraMap);
        DalipushPlugin.getInstance().getEventSink().success(gson.toJson(parm));
    }

    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        Log.e("MyMessageReceiver", "onNotificationClickedWithNoAction, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
        Log.e("DalipushPlugin", "eventSink: " + DalipushPlugin.getInstance().getEventSink());
        Map<String,String> parm= new HashMap<>();
        parm.put("type","NotificationOpened");
        parm.put("title",title);
        parm.put("summary",summary);
        parm.put("extraMap",extraMap);
        DalipushPlugin.getInstance().getEventSink().success(gson.toJson(parm));
    }

    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        Log.e("MyMessageReceiver", "onNotificationReceivedInApp, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap + ", openType:" + openType + ", openActivity:" + openActivity + ", openUrl:" + openUrl);
        Log.e("DalipushPlugin", "eventSink: " + DalipushPlugin.getInstance().getEventSink());
        Map<String,Object> parm= new HashMap<>();
        parm.put("type","NotificationReceivedInApp");
        parm.put("title",title);
        parm.put("summary",summary);
        parm.put("extraMap",extraMap);
        parm.put("extraMap",openType);
        parm.put("openActivity",openActivity.getClass().getName());
        parm.put("openUrl",openUrl);
        DalipushPlugin.getInstance().getEventSink().success(gson.toJson(parm));
    }

    @Override
    protected void onNotificationRemoved(Context context, String messageId) {
        Log.e("MyMessageReceiver", "onNotificationRemoved");
        Log.e("DalipushPlugin", "eventSink: " + DalipushPlugin.getInstance().getEventSink());
        Map<String,String> parm= new HashMap<>();
        parm.put("type","NotificationRemoved");
        parm.put("messageId",messageId);
        DalipushPlugin.getInstance().getEventSink().success(gson.toJson(parm));
    }

    private class Notification {
        String type;
        String title;
        String summary;
        Map<String, String> extraMap;

        public Notification(String title, String summary, Map<String, String> extraMap) {
            this.type = "notification";
            this.title = title;
            this.summary = summary;
            this.extraMap = extraMap;
        }
    }

    private class Message {
        String type;
        String title;
        String content;

        public Message(CPushMessage cPushMessage) {
            this.type = "message";
            this.title = cPushMessage.getTitle();
            this.content = cPushMessage.getContent();
        }
    }
}