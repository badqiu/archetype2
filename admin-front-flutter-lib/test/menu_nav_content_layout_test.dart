import 'package:flutter/material.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: '仓储管理系统',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        appBarTheme: const AppBarTheme(
          backgroundColor: Colors.white,
          foregroundColor: Colors.black,
          elevation: 1,
        ),
      ),
      home: const MainLayout(),
    );
  }
}

class MainLayout extends StatefulWidget {
  const MainLayout({super.key});

  @override
  _MainLayoutState createState() => _MainLayoutState();
}

class _MainLayoutState extends State<MainLayout> {
  int _selectedIndex = 0;
  final List<String> _menuItems = [
    '系统配置',
    '货位遍历信息',
    '货位遍历日志'
  ];

  final List<Widget> _contentPages = [
    const SystemConfigPage(),
    const LocationTraversalPage(),
    const OperationLogPage(),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('仓储管理系统 V1.0'),
        actions: [
          IconButton(
            icon: const Icon(Icons.notifications),
            onPressed: () {},
          ),
          const SizedBox(width: 16),
        ],
      ),
      body: Row(
        children: [
          // 左侧菜单
          Container(
            width: 240,
            decoration: const BoxDecoration(
              color: Color(0xFFF5F5F5),
              border: Border(right: BorderSide(color: Colors.grey, width: 0.5)),
            ),
            child: ListView(
              children: _menuItems
                  .asMap()
                  .entries
                  .map((entry) => _buildMenuTile(entry.key, entry.value))
                  .toList(),
            ),
          ),

          // 右侧内容区域
          Expanded(
            child: _contentPages[_selectedIndex],
          ),
        ],
      ),
    );
  }

  Widget _buildMenuTile(int index, String title) {
    return ListTile(
      selected: _selectedIndex == index,
      selectedTileColor: Colors.blue.withOpacity(0.1),
      leading: _getMenuIcon(index),
      title: Text(title,
          style: TextStyle(
            color: _selectedIndex == index ? Colors.blue : Colors.black87,
            fontWeight: _selectedIndex == index 
                ? FontWeight.w500 
                : FontWeight.normal,
          )),
      onTap: () => setState(() => _selectedIndex = index),
    );
  }

  Icon _getMenuIcon(int index) {
    const icons = [
      Icons.settings,
      Icons.map,
      Icons.assignment,
    ];
    return Icon(
      icons[index],
      color: _selectedIndex == index ? Colors.blue : Colors.grey[700],
    );
  }
}

// 示例页面组件
class SystemConfigPage extends StatelessWidget {
  const SystemConfigPage({super.key});

  @override
  Widget build(BuildContext context) {
    return const Padding(
      padding: EdgeInsets.all(20.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text('系统参数配置', style: TextStyle(fontSize: 20)),
          SizedBox(height: 20),
          // 这里添加具体的配置组件
        ],
      ),
    );
  }
}

class LocationTraversalPage extends StatelessWidget {
  const LocationTraversalPage({super.key});

  @override
  Widget build(BuildContext context) {
    return const Padding(
      padding: EdgeInsets.all(20.0),
      child: Column(
        children: [
          Text('当前货位状态', style: TextStyle(fontSize: 20)),
          SizedBox(height: 20),
          // 这里添加数据表格
        ],
      ),
    );
  }
}

class OperationLogPage extends StatelessWidget {
  const OperationLogPage({super.key});

  @override
  Widget build(BuildContext context) {
    return const Padding(
      padding: EdgeInsets.all(20.0),
      child: Column(
        children: [
          Text('最近操作记录', style: TextStyle(fontSize: 20)),
          SizedBox(height: 20),
          // 这里添加日志列表
        ],
      ),
    );
  }
}