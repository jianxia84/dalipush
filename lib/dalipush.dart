import 'dart:async';

import 'package:flutter/services.dart';

class Dalipush {
  factory Dalipush() {
    if (_instance == null) {
      final MethodChannel methodChannel = const MethodChannel('dalipush');
      final EventChannel eventChannel = const EventChannel('dalipush_event');
      _instance = new Dalipush.private(methodChannel, eventChannel);
    }
    return _instance;
  }

  Dalipush.private(this._methodChannel, this._eventChannel);

  final MethodChannel _methodChannel;

  final EventChannel _eventChannel;

  static Dalipush _instance;

  Stream<dynamic> _listener;

  Future<String> get platformVersion async {
    final String version =
        await _methodChannel.invokeMethod('getPlatformVersion');
    return version;
  }

  Future<String> get getDeviceid async {
    final String version =
    await _methodChannel.invokeMethod('getDeviceid');
    return version;
  }

  Future<String> bindAccount (String account) async{
    final String version =
    await _methodChannel.invokeMethod('bindAccount',{"account":account});
    return version;
  }

  Future<String> unbindAccount () async{
    final String version =
    await _methodChannel.invokeMethod('unbindAccount');
    return version;
  }

  Future<String> bindTag (int target,List<String> tags ,String alias) async{
    final String version =
    await _methodChannel.invokeMethod('bindTag',{"target":target,"tags":tags,"alias":alias});
    return version;
  }

  Future<String>  unbindTag (int target,List<String> tags ,String alias) async{
    final String version =
    await _methodChannel.invokeMethod('unbindTag',{"target":target,"tags":tags,"alias":alias});
    return version;
  }

  Future<String> listTags(int target) async {
    final String version =
    await _methodChannel.invokeMethod('listTags',{"target":target});
    return version;
  }

  Future<String> addAlias(String alias) async {
    final String version =
    await _methodChannel.invokeMethod('addAlias',{"alias":alias});
    return version;
  }

  Future<String> removeAlias(String alias) async {
    final String version =
    await _methodChannel.invokeMethod('removeAlias',{"alias":alias});
    return version;
  }

  Future<String> listAliases () async {
    final String version =
    await _methodChannel.invokeMethod('listAliases');
    return version;
  }

  Stream<dynamic> get onMessage {
    if (_listener == null) {
      _listener = _eventChannel
          .receiveBroadcastStream()
          .map((dynamic event) => _parseMsg(event));
    }
    return _listener;
  }

  dynamic _parseMsg(event) {
    return event;
  }
}
