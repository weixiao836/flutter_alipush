import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_alipush/flutter_alipush.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp>{
  String _platformVersion = 'Unknown';
  String deviceId = null;


  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await Alipush.platformVersion;
      deviceId = await Alipush().getDeviceId();
      print(deviceId);
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }



    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    Alipush().addEventHandler(
        onNotification:(Map<String, dynamic> message) async {
          print("flutter onNotification: $message");
        },
        onMessage:(Map<String, dynamic> message) async {
          print("flutter onMessage: $message");
        },
        onNotificationOpened:(Map<String, dynamic> message) async {
          print("flutter onNotificationOpened: $message");
        },
    );


    setState(() {
      _platformVersion = platformVersion ;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            children: <Widget>[
              Text('deviceId $deviceId\n'),
              RaisedButton(
                child: new Text('bindAccount'),
                onPressed: () => {
                  Alipush().bindAccount('123456')
                  .then((s) => {print('bindaccount======>'+s)})
                  .catchError((error) => {print(error)})
                },
              ),
              RaisedButton(
                child:Text('unbindAccount'),
                onPressed:() => {
                  Alipush().unbindAccount()
                      .then((s) => {print('bindaccount======>'+s)})
                      .catchError((error) => {print(error)})
                },
              )
            ],
          )
        ),
      ),
    );
  }
}
