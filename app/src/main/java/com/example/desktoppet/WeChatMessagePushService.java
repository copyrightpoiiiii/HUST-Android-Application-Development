package com.example.desktoppet;

import java.util.List;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.PendingIntent;
import android.view.accessibility.AccessibilityEvent;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.content.Context;

public class WeChatMessagePushService extends AccessibilityService {
    static final String TAG = "GetWechatMessage";
    static final String WECHAT_PACKAGENAME = "com.tencent.mm";
    static final String WECHAT_LAUNCHER = "com.tencent.mm.ui.LauncherUI";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        final int eventType = event.getEventType();
        if (eventType == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED)
            onNotifyStateChanged(event);
    }

    @Override
    public void onInterrupt() {
        Toast.makeText(this, "中断微信消息获取服务", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Toast.makeText(this, "连接微信消息获取服务", Toast.LENGTH_SHORT).show();
    }

    private void onNotifyStateChanged(AccessibilityEvent event) {
        CharSequence mypackage = event.getPackageName();
        String str_package = String.valueOf(mypackage);
        if (str_package.equals(WECHAT_PACKAGENAME)) {
            openMessageWindow(event);
        }
    }

    /**
     * 打开对话框
     */
    private void openMessageWindow(AccessibilityEvent event) {
        if (event.getParcelableData() == null || !(event.getParcelableData() instanceof Notification)) {
            return;
        }
        /*
         * 获取消息内容
         */
        List<CharSequence> texts = event.getText();
        String text = String.valueOf(texts.get(texts.size() - 1));
        if (text.length() > 15) {
            text = text.substring(0, 15);
            text += "...";
        }
        Notification notification = (Notification) event.getParcelableData();
        PendingIntent pendingIntent = notification.contentIntent;
        myWindowManager.removeMessageWindow(this);
        myWindowManager.createMessageWindow(this, pendingIntent, text);
    }
}
