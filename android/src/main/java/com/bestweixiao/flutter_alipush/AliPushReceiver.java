package com.bestweixiao.flutter_alipush;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.EventChannel;

public class AliPushReceiver extends MessageReceiver {

    public static final String REC_TAG = "AliPushReceiver";

    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        // TODO 处理推送通知
        Log.e("MyMessageReceiver", "Receive notification, title: " + title + ", summary: " + summary + ", extraMap: " + extraMap);
        Map<String,Object> map = new HashMap<>();
        map.put("title",title);
        map.put("summary",summary);
        map.put("extraMap",extraMap);
        FlutterAlipushPlugin.getMethodChannel().invokeMethod("onNotification",map);
    }

    @Override
    public void onMessage(Context context, CPushMessage cPushMessage) {
        Log.e("MyMessageReceiver", "onMessage, messageId: " + cPushMessage.getMessageId() + ", title: " + cPushMessage.getTitle() + ", content:" + cPushMessage.getContent());
        Map<String,Object> map = new HashMap<>();
        map.put("title",cPushMessage.getTitle());
        map.put("content",cPushMessage.getContent());
        map.put("messageId",cPushMessage.getMessageId());
        map.put("traceInfo",cPushMessage.getTraceInfo());
        FlutterAlipushPlugin.getMethodChannel().invokeMethod("onMessage",map);
    }


    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        Log.e("MyMessageReceiver", "onNotificationOpened, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
        Map<String,Object> map = new HashMap<>();
        map.put("title",title);
        map.put("summary",summary);
        map.put("extraMap",extraMap);
        FlutterAlipushPlugin.getMethodChannel().invokeMethod("onNotificationOpened",map);
    }

    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        Log.e("MyMessageReceiver", "onNotificationClickedWithNoAction, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
        Map<String,Object> map = new HashMap<>();
        map.put("title",title);
        map.put("summary",summary);
        map.put("extraMap",extraMap);
        FlutterAlipushPlugin.getMethodChannel().invokeMethod("onNotificationClickedWithNoAction",map);
    }

    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        Log.e("MyMessageReceiver", "onNotificationReceivedInApp, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap + ", openType:" + openType + ", openActivity:" + openActivity + ", openUrl:" + openUrl);
        Map<String,Object> map = new HashMap<>();
        map.put("title",title);
        map.put("summary",summary);
        map.put("extraMap",extraMap);
        map.put("openType",openType);
        map.put("openActivity",openActivity);
        map.put("openUrl",openUrl);
        FlutterAlipushPlugin.getMethodChannel().invokeMethod("onNotificationReceivedInApp",map);

    }

    @Override
    protected void onNotificationRemoved(Context context, String messageId) {
        Log.e("MyMessageReceiver", "onNotificationRemoved");
        Map<String,Object> map = new HashMap<>();
        map.put("messageId",messageId);
        FlutterAlipushPlugin.getMethodChannel().invokeMethod("onNotificationRemoved",map);
    }

}
