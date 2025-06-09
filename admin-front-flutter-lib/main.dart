import 'package:environment_checker_hub_ui/app/config/global_config.dart';
import 'package:flutter/material.dart';
import 'package:environment_checker_hub_ui/app/all.dart';
import 'package:misc_dart/all.dart';
import 'package:flutter/foundation.dart';
import 'dart:async';

void main() {  
  print("main() start");
  
  GlobalConfig.init();

  if(GlobalConfig.isDevEnv){
    Logger.setLevel(LogLevel.debug);
  }

  print("GlobalConfig app_env=" + GlobalConfig.appEnv);


  runZonedGuarded(() {
    runApp(const MainApp());
  }, (error, stackTrace) {
    // 处理所有未捕获的异常
    handleGlobalError(GlobalConfig.globalNavigatorKey,error,stackTrace);
  });

  // 处理Flutter框架异常
  FlutterError.onError = (details) {
    handleGlobalError(GlobalConfig.globalNavigatorKey,details.exception,details.stack);
  };

  
}




