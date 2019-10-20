import 'dart:async';

import 'package:flutter/services.dart';

typedef Future<dynamic> EventHandler(Map<String, dynamic> event);


class Alipush {
  factory Alipush() => _getInstance();

  static Alipush get instance => _getInstance();

  static Alipush _instance;

  static Alipush _getInstance() {
    if (_instance == null) {
      _instance = new Alipush._internal();
    }
    return _instance;
  }

  Alipush._internal() {}

  static const MethodChannel _channel = const MethodChannel('flutter_alipush');


  EventHandler _onNotification;
  EventHandler _onMessage;
  EventHandler _onNotificationOpened;
  EventHandler _onNotificationClickedWithNoAction;
  EventHandler _onNotificationReceivedInApp;
  EventHandler _onNotificationRemoved;

  Stream<dynamic> _listener;

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  Future<String> getDeviceId() async {
    final String result = await _channel.invokeMethod('getDeviceId');

    return result;
  }



  ///
  ///
  /// 添加事件监听
  ///
  void addEventHandler({
    EventHandler onNotification,
    EventHandler onMessage,
    EventHandler onNotificationOpened,
    EventHandler onNotificationClickedWithNoAction,
    EventHandler onNotificationReceivedInApp,
    EventHandler onNotificationRemoved,
  }){
    _onNotification = onNotification;
    _onMessage = onMessage;
    _onNotificationOpened = onNotificationOpened;
    _onNotificationClickedWithNoAction = onNotificationClickedWithNoAction;
    _onNotificationReceivedInApp = onNotificationReceivedInApp;
    _onNotificationRemoved = onNotificationRemoved;
    _channel.setMethodCallHandler(_handleMethod);
  }

  Future<Null> _handleMethod(MethodCall call) async {
    switch (call.method) {
      case "onNotification":
        return _onNotification(call.arguments.cast<String, dynamic>());
      case "onMessage":
        return _onMessage(call.arguments.cast<String, dynamic>());
      case "onNotificationOpened":
        return _onNotificationOpened(call.arguments.cast<String, dynamic>());
      case "onNotificationClickedWithNoAction":
        return _onNotificationClickedWithNoAction(call.arguments.cast<String, dynamic>());
      case "onNotificationReceivedInApp":
        return _onNotificationReceivedInApp(call.arguments.cast<String, dynamic>());
      case "onNotificationRemoved":
        return _onNotificationRemoved(call.arguments.cast<String, dynamic>());
      default:
        throw new UnsupportedError("Unrecognized Event");
    }
  }

  ///
  /// 绑定账号
  /// @param account 账号
  ///
  Future<String> bindAccount(String account) async {
    return await _channel.invokeMethod("bindAccount", account);
  }

  ///
  /// 解绑账号
  ///
  Future<String> unbindAccount() async {
    return await _channel.invokeMethod("unbindAccount");
  }

  ///
  ///  绑定标签
  ///  @param target 目标类型，1：本设备； 2：本设备绑定账号； 3：别名
  ///  @param tags 标签（数组输入）
  ///  @param alias 别名（仅当target = 3时生效）
  ///
  Future<String> bindTag(int target, List<String> tags, String alias) async {
    Map arguments = new Map();
    arguments['target'] = target;
    arguments['tags'] = tags;
    arguments['alias'] = alias;
    return await _channel.invokeMethod("bindTag", arguments);
  }

  ///
  ///  解绑标签
  ///  @param target 目标类型，1：本设备； 2：本设备绑定账号； 3：别名
  ///  @param tags 标签（数组输入）
  ///  @param alias 别名（仅当target = 3时生效）
  ///
  Future<String> unbindTag(int target, List<String> tags, String alias) async {
    Map arguments = new Map();
    arguments['target'] = target;
    arguments['tags'] = tags;
    arguments['alias'] = alias;
    return await _channel.invokeMethod("unbindTag", arguments);
  }

}
