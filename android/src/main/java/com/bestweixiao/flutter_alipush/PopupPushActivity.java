package com.bestweixiao.flutter_alipush;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.sdk.android.push.AndroidPopupActivity;

import java.util.Map;

public class PopupPushActivity extends AndroidPopupActivity {

    static final String TAG = "PopupPushActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onSysNoticeOpened(String title, String summary, Map<String, String> extMap) {

        Log.d(TAG, "OnMiPushSysNoticeOpened, title: " + title + ", content: " + summary + ", extMap: " + extMap);
    }
}
