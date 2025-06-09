import 'package:flutter/material.dart';
import 'package:environment_checker_hub_ui/app/config/all.dart';

class Car {
  final int agv_id;
  final String ip;
  final bool is_online;
  final String fn_model_str;
  bool? exceptionStatus = null;

  Car({
    required this.agv_id,
    required this.ip,
    required this.is_online,
    // required this.exceptionStatus,
    required this.fn_model_str,
  });

  String get onlineStatusName {
    return is_online ? '在线' : '离线';
  }

  Color? get onlineStatusColor {
    Color? online = null;
    Color offline = ColorConstants.colorOfflineContainer;
    return is_online ? online  : offline ;
  }

  String get exceptionStatusName {
    return exceptionStatus == true ? '异常' : '正常';
  }

  String get exception_assistant_url {
    return 'http://${ip}:8080/exception_assistant/';
  }

  String get remote_controler_url {
    return 'http://${ip}:8080/';
  }

  String get geye_url {
    return 'http://${ip}:8080/geye/';
  }

  String get sensorview_url {
    return 'http://${ip}:8080/sensorview/sensorview.html?locale=zh_cn';
  }

  bool searchByText(String text) {
    String searchQuery = text.toLowerCase();
    return agv_id.toString().toLowerCase().contains(searchQuery) 
           || fn_model_str.toLowerCase().contains(searchQuery)
           || ip.toLowerCase().contains(searchQuery)
           ;
  }

  // static Car fromAgvItem(AgvItem agvItem) {
  //   return Car(
  //     agv_id: agvItem.agvId,
  //     ip: agvItem.ip,
  //     is_online: agvItem.isOnline,
  //     fn_model_str: agvItem.fnName,
  //   );
  // }
}



void sortCarListByOnlineStatusAndAgvId(List<Car> carList) {
  carList.sort((a, b) {
    if (a.is_online == b.is_online) {
      return a.agv_id.compareTo(b.agv_id);
    }
    return a.is_online? -1 : 1;
  });
}