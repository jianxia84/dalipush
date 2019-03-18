package com.brzhang.dalipush;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.sdk.android.push.AndroidPopupActivity;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class PopupPushActivity extends AndroidPopupActivity {
    static final String TAG = "PopupPushActivity";
    Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    /**
     * 实现通知打开回调方法，获取通知相关信息
     * @param title     标题
     * @param summary   内容
     * @param extMap    额外参数
     */
    @Override
    protected void onSysNoticeOpened(String title, String summary, Map<String, String> extMap) {
        Log.d(TAG,"Oitle: " + title + ", content: " + summary + ", extMap: " + extMap);
        Map<String,Object> parm= new HashMap<>();
        parm.put("type","SysNoticeOpened");
        parm.put("title",title);
        parm.put("summary",summary);
        parm.put("extraMap",extMap);
        DalipushPlugin.getInstance().getEventSink().success(gson.toJson(parm));
    }
}