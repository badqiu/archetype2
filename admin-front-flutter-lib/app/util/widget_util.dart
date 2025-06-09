import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'dart:async';
import 'package:auto_route/auto_route.dart';


import 'package:misc_dart/all.dart';

void autoPopNavigator(BuildContext context) {
  if (ModalRoute.of(context)?.isCurrent != true) {
    Navigator.of(context).pop(); // 关闭 Dialog
  } else {
    Navigator.of(context).maybePop(); // 关闭 Page
  }
}


Widget buildAddButton(String label,VoidCallback onPressed) {
  return Padding(
    padding: const EdgeInsets.only(right: 16),
    child: ElevatedButton.icon(
      icon: const Icon(Icons.add, size: 20),
      label: Text(label),
      style: ElevatedButton.styleFrom(
        foregroundColor: Colors.white,
        backgroundColor: Get.theme.primaryColor,
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(20),
        ),
      ),
      onPressed: onPressed,
    ),
  );
}

Widget buildIconInfoRow(IconData icon, String text) {
  return Padding(
    padding: const EdgeInsets.symmetric(vertical: 2),
    child: Row(
      children: [
        Icon(icon, size: 16, color: Colors.grey),
        const SizedBox(width: 8),
        Text(text, style: TextStyle(color: Colors.grey[600])),
      ],
    ),
  );
}


Future<bool?> showConfirmDialog2({
  required BuildContext context,
  required String title,
  required String content,
}) async {
  return showDialog<bool>(
    context: context,
    builder: (context) => AlertDialog(
      title: Text(title),
      content: Text(content),
      actions: [
        TextButton(
          onPressed: () => Navigator.pop(context, false),
          child: const Text('取消'),
        ),
        TextButton(
          onPressed: () => Navigator.pop(context, true),
          child: const Text('确认', style: TextStyle(color: Colors.red)),
        ),
      ],
    ),
  );
}


Future<void> showDeleteConfirmDialog(BuildContext context,VoidCallback onConfirmDelete) async {
  final confirmed = await showConfirmDialog2(
    context: context,
    title: '确认删除',
    content: '确定删除？此操作不可恢复！',
  );

  if (confirmed == true) {
    onConfirmDelete();
  }
}


MaterialApp buildTestApp({
  Key? key,
  required Widget homePage, // 强制要求传入主页
  String title = 'testSimpleApp', // 默认标题
  Map<String, WidgetBuilder> routes = const {}, // 可选路由配置
  bool debugShowCheckedModeBanner = false, // 默认隐藏调试标志
}) {
  return MaterialApp(
    key: key,
    title: title,
    theme: ThemeData(
      primarySwatch: Colors.blue,
      useMaterial3: true,
    ),
    home: homePage,
    routes: routes,
    debugShowCheckedModeBanner: debugShowCheckedModeBanner,
  );
}