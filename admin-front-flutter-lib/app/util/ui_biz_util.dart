
import 'package:flutter/material.dart';
import 'package:environment_checker_hub_ui/app/enums/all.dart';
import 'package:flutter_inappwebview/flutter_inappwebview.dart';

InkWell buildInkWellByCargoLoadUnloadType(CargoLoadUnloadType type, Function doOnTab) {
  return InkWell(
    onTap: () {
      doOnTab(type);
    },
    child: ListTile(
      title: Text(type.title),
      trailing: Icon(Icons.arrow_forward_ios),
    ),
  );
}




Widget buildUrlWebView(String url) {
  return InAppWebView(
    key: UniqueKey(),
    initialFile: url,
    onWebViewCreated: (InAppWebViewController controller) {},
    initialSettings: InAppWebViewSettings(
        javaScriptEnabled: true,
        javaScriptCanOpenWindowsAutomatically: true,
        useShouldOverrideUrlLoading: true,
        mediaPlaybackRequiresUserGesture: false,
        allowFileAccessFromFileURLs: true,
        allowUniversalAccessFromFileURLs: true,
        supportZoom: false, // 禁用缩放
        displayZoomControls: false, // 隐藏缩放控件
        builtInZoomControls: false, // 隐藏内置缩放控件
        
      ),
  );
}




