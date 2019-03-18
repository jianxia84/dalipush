package com.brzhang.dalipush;

import android.app.Notification;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.google.gson.Gson;

import java.util.Map;

import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * DalipushPlugin
 */
public class DalipushPlugin implements MethodCallHandler, EventChannel.StreamHandler {

    private static DalipushPlugin dalipushPlugin;

    private EventChannel.EventSink eventSink;

    CloudPushService pushService = PushServiceFactory.getCloudPushService();

    public static DalipushPlugin getInstance() {
        return dalipushPlugin;
    }

    public EventChannel.EventSink getEventSink() {
        return this.eventSink;
    }

    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        Log.e("DalipushPlugin", "registerWith() called with: registrar = [" + registrar + "]");
        final MethodChannel methodChannel = new MethodChannel(registrar.messenger(), "dalipush");
        final EventChannel eventChannel = new EventChannel(registrar.messenger(), "dalipush_event");
        Log.e("DalipushPlugin", "eventChannel called with: registrar = [" + eventChannel + "]");
        dalipushPlugin = new DalipushPlugin();
        methodChannel.setMethodCallHandler(dalipushPlugin);
        eventChannel.setStreamHandler(dalipushPlugin);

    }

    @Override
    public void onMethodCall(MethodCall call, final Result result) {
        if (call.method.equals("getPlatformVersion")) {
            result.success("Android " + android.os.Build.VERSION.RELEASE + "packageName" + BuildConfig.APPLICATION_ID);
        } else if (call.method.equals("getDeviceid")) {
            result.success(pushService.getDeviceId());
        } else if (call.method.equals("bindAccount")) {
            String account = call.argument("account");
            pushService.bindAccount(account, new CommonCallback() {
                @Override
                public void onSuccess(String s) {
                    result.success(s);
                }

                @Override
                public void onFailed(String s, String s1) {
                    result.error(s,s1,null);
                }
            });

        } else if (call.method.equals("unbindAccount")) {
            pushService.unbindAccount(new CommonCallback() {
                @Override
                public void onSuccess(String s) {
                    result.success(s);
                }

                @Override
                public void onFailed(String s, String s1) {
                    result.error(s,s1,null);
                }
            });
        } else if (call.method.equals("bindTag")) {
            int target = call.argument("target");
            String[] tags = call.argument("tags");
            String alias = call.argument("alias");
            pushService.bindTag( target, tags, alias,
                new CommonCallback() {
                    @Override
                    public void onSuccess(String s) {
                        result.success(s);
                    }

                    @Override
                    public void onFailed(String s, String s1) {
                        result.error(s,s1,null);
                    }
                });

        } else if (call.method.equals("unbindTag")) {
            int target = call.argument("target");
            String[] tags = call.argument("tags");
            String alias = call.argument("alias");
            pushService.unbindTag( target, tags, alias,
                    new CommonCallback() {
                        @Override
                        public void onSuccess(String s) {
                            result.success(s);
                        }

                        @Override
                        public void onFailed(String s, String s1) {
                            result.error(s,s1,null);
                        }
                    });
        } else if (call.method.equals("listTags")) {
            int target = call.argument("target");
            pushService.listTags( target,
                    new CommonCallback() {
                        @Override
                        public void onSuccess(String s) {
                            result.success(s);
                        }

                        @Override
                        public void onFailed(String s, String s1) {
                            result.error(s,s1,null);
                        }
                    });
        } else if (call.method.equals("addAlias")) {
            String alias = call.argument("alias");
            pushService.addAlias( alias,
                    new CommonCallback() {
                        @Override
                        public void onSuccess(String s) {
                            result.success(s);
                        }

                        @Override
                        public void onFailed(String s, String s1) {
                            result.error(s,s1,null);
                        }
                    });
        } else if (call.method.equals("removeAlias")) {
            String alias = call.argument("alias");
            pushService.removeAlias( alias,
                    new CommonCallback() {
                        @Override
                        public void onSuccess(String s) {
                            result.success(s);
                        }

                        @Override
                        public void onFailed(String s, String s1) {
                            result.error(s,s1,null);
                        }
                    });
        } else if (call.method.equals("listAliases")) {
            pushService.listAliases(
                    new CommonCallback() {
                        @Override
                        public void onSuccess(String s) {
                            result.success(s);
                        }

                        @Override
                        public void onFailed(String s, String s1) {
                            result.error(s,s1,null);
                        }
                    });
        } else {
            result.notImplemented();
        }
    }

    @Override
    public void onListen(Object o, EventChannel.EventSink eventSink) {
        Log.e("DalipushPlugin", "onListen() called with: o = [" + o + "], eventSink = [" + eventSink + "]");
        this.eventSink = eventSink;
    }

    @Override
    public void onCancel(Object o) {

    }


}
