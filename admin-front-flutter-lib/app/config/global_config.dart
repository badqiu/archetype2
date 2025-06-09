import 'dart:collection';
import 'package:flutter/foundation.dart' show kIsWeb;
import 'dart:html' if (dart.library.io) 'package:misc_dart/util/mock_window.dart';
import 'package:flutter/material.dart';
import 'package:grpc/grpc_web.dart';


class GlobalConfig {
  

  static String appName = "异常溯源工具-UI";

  static String appEnv = "dev";


  static String locale = "zh";

  static int APP_API_PORT = 8803;

  static const Color successColor = Color.fromRGBO(40, 167, 69, 1);

  static final GlobalKey<NavigatorState> globalNavigatorKey = GlobalKey<NavigatorState>();

  static List<Map<String, dynamic>> helpList = [
    {
      "title": "名词介绍:RCS",
      "key": "rcs",
      "keywords": "rcs",
      "content": "RCS是机器人集群调度系统,\n可以通过界面监控，控制，小车运行及执行小车相关任务操作"
    }  
  ];


  static overrideFieldsByEnv(String _appEnv) {
    print("GlobalConfig.overrideFieldsByEnv() appEnv:$_appEnv");

    if (_appEnv == "prod") {
      appName = Prod.appName;
      appEnv = Prod.appEnv;
    }else if (_appEnv == "dev") {
      // default
    } else {
      throw Exception("unknow appEnv:$_appEnv");
    }
  }


  static String get baseUrl {
    if (kIsWeb) {
      // 对于Web应用，我们可以使用当前页面的URL信息。
      return webBaseUrl;
    } else {
      // 对于移动应用，我们假设使用固定的域名或通过其他方式确定。
      return 'https://api.yourdomain.com';
    }
  }

  static String get baseHostname {
    String? hostname = getWebWindow().location.hostname;
    return hostname??"";
  }

  static String get basePort {
    String port = getWebWindow().location.port;
    return port;
  }

  static String get baseProtocol {
    // 获取当前页面的协议（http/https）
    String protocol = getWebWindow().location.protocol;
    return protocol;
  }

  static String get remoteControllerWebsocketUrl {
    return "ws://${baseHostname}:9000";
  }

  static String get remoteControllerHttpUrl {
    if (isDevEnv) {
      return "http://${baseHostname}:9001";
    } else {
      return "http://${baseHostname}:9000";
    }
  }

  static String get remoteControllerUrl {
    return webBaseUrl;
  }

  static String get exceptionTraceAgvServerUrl {
    if (isDevEnv) {
      return "${baseProtocol}//${baseHostname}:8022";
    }else {
      return "${webBaseUrl}/api/exception_trace_agv_server";
    }
  }

  static String get dumperServerUrl {
    return "${baseProtocol}//${baseHostname}:8802";
  }

  static String get webBaseUrl {
    var result = "${baseProtocol}//${baseHostname}:8080";
    return result;
  }

  static String get rpcBaseUrl {
    var result = "${baseProtocol}//${baseHostname}:8589";
    return result;
  }

  static bool get isDevEnv {
    return appEnv == "dev";
  }

  static bool get isProdEnv {
    return appEnv == "prod";
  }

  static bool isWebPlatform() {
    return kIsWeb;
  }

  static dynamic getWebWindow() {
    return window;
  }

  static GrpcWebClientChannel getGrpcChannel() {
      return GrpcWebClientChannel.xhr(
        Uri.parse(rpcBaseUrl), // 服务端地址
      );
  }

  static GlobalConfig init() {
    print("GlobalConfig.init() start");
    if (getWebWindow().location.hostname == "localhost" || getWebWindow().location.hostname == "127.0.0.1") {
      overrideFieldsByEnv("dev");
    }else {
      overrideFieldsByEnv("prod");
    }
    return GlobalConfig();
  }

  GlobalConfig() {
    print("GlobalConfig constructor");
  }
  

}



class Prod {
  static String appName = "prod work helper";
  static String appEnv = "prod";
}
