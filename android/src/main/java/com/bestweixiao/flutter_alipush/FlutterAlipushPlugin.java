package com.bestweixiao.flutter_alipush;

import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;

import java.util.List;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FlutterAlipushPlugin */
public class FlutterAlipushPlugin implements MethodCallHandler {

  public static final String TAG = "ALIPUSH_PLUGIN";


  public static FlutterAlipushPlugin instance;

  private  final MethodChannel channel;

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_alipush");
    channel.setMethodCallHandler(new FlutterAlipushPlugin(channel));
  }

  private FlutterAlipushPlugin(MethodChannel channel){
    this.channel = channel;
    instance = this;
  }

  public static MethodChannel getMethodChannel(){
    return instance.channel;
  }

  public static FlutterAlipushPlugin getInstance(){
    return instance;
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if(call.method.equals("getDeviceId")){
      getDeviceId(call, result);
    } else if(call.method.equals("bindAccount")){
      bindAccount(call, result);
    } else if(call.method.equals("unbindAccount")){
      unbindAccount(call, result);
    } else if(call.method.equals("bindTag")){
      bindTag(call, result);
    } else if(call.method.equals("unbindTag")){
      unbindTag(call, result);
    }else {
      result.notImplemented();
    }
  }



  public void getDeviceId (MethodCall call, Result result) {
    Log.i(TAG, "invoke getDeviceId");
    final CloudPushService pushService = PushServiceFactory.getCloudPushService();
    String deviceId = pushService.getDeviceId();
    result.success(deviceId);
  }

  public void bindAccount(MethodCall call, final Result result){
    Log.i(TAG, "invoke bindAccount");
    final CloudPushService pushService = PushServiceFactory.getCloudPushService();
    String account = call.arguments();
    pushService.bindAccount(account, new CommonCallback() {
      @Override
      public void onSuccess(String s) {
        Log.i(TAG, "bindAccount onSuccess:s=" + s);
        result.success(s);
      }

      @Override
      public void onFailed(String s, String s1) {
        Log.i(TAG, "bindAccount onFailed:s=" + s);
        result.error(s, s1, null);
      }
    });
  }

  public void unbindAccount(MethodCall call, final Result result){
    Log.i(TAG, "invoke unbindAccount");
    final CloudPushService pushService = PushServiceFactory.getCloudPushService();
    pushService.unbindAccount( new CommonCallback() {
      @Override
      public void onSuccess(String s) {
        Log.i(TAG, "unbindAccount onSuccess:s=" + s);
        result.success(s);
      }

      @Override
      public void onFailed(String s, String s1) {
        Log.i(TAG, "unbindAccount onFailed: s= " + s);
        result.error(s, s1, null);
      }
    });
  }

  /**
   * 绑定标签
   * @param call
   * @param result
   */
  public void bindTag(MethodCall call, final Result result){
    Log.i(TAG, "invoke bindTag");
    final CloudPushService pushService = PushServiceFactory.getCloudPushService();
    Integer  target = call.argument("target");
    List<String> tags = call.argument("tags");
    String alias = call.argument("alias");

    String[] tagsArray = new String[tags.size()];
    tagsArray = tags.toArray(tagsArray);

    pushService.bindTag(target, tagsArray, alias, new CommonCallback() {
      @Override
      public void onSuccess(String s) {
        Log.i(TAG, "bindTag onSuccess:s=" + s);
        result.success(s);
      }

      @Override
      public void onFailed(String s, String s1) {
        Log.i(TAG, "bindTag onFailed: s= " + s);
        result.error(s, s1, null);
      }
    });
  }

  /**
   * 解绑标签
   * @param call
   * @param result
   */
  public void unbindTag(MethodCall call, final Result result){
    Log.i(TAG, "invoke unbindTag");
    final CloudPushService pushService = PushServiceFactory.getCloudPushService();
    Integer  target = call.argument("target");
    List<String> tags = call.argument("tags");
    String alias = call.argument("alias");

    String[] tagsArray = new String[tags.size()];
    tagsArray = tags.toArray(tagsArray);

    pushService.unbindTag(target, tagsArray, alias, new CommonCallback() {
      @Override
      public void onSuccess(String s) {
        Log.i(TAG, "unbindTag onSuccess:s=" + s);
        result.success(s);
      }

      @Override
      public void onFailed(String s, String s1) {
        Log.i(TAG, "unbindTag onFailed: s= " + s);
        result.error(s, s1, null);
      }
    });
  }
}
