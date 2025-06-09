import 'package:flutter/material.dart';
import 'package:auto_route/auto_route.dart';
import 'package:bruno/bruno.dart';
import 'car_list.dart';

class BottomBarWithBody {
  BrnBottomTabBarItem bottomTabBarItem;
  Widget body;

  BottomBarWithBody({required this.bottomTabBarItem, required this.body});
}

// 小车主页
@RoutePage()
class BottomBarHomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<BottomBarHomePage> {
  int _currentIndex = 0;
  Widget? _currentBody;

  List<BottomBarWithBody> items = [
    BottomBarWithBody(
      bottomTabBarItem: BrnBottomTabBarItem(icon: Icon(Icons.report_problem_outlined), title: Text('异常原因分析')),
      body: CarListPage()
    ),

    BottomBarWithBody(
      bottomTabBarItem: BrnBottomTabBarItem(icon: Icon(Icons.car_repair), title: Text('小车列表')),
      body: CarListPage()
    ),

    // BottomBarWithBody(
    //   bottomTabBarItem: BrnBottomTabBarItem(icon: Icon(Icons.list), title: Text('其它')),
    //   body: OtherListPage()
    // ),
  ];

  @override
  void initState() {
    super.initState();
    //print("_HomePageState.initState()");

    _onItemSelected(_currentIndex);
  }

  void _onItemSelected(int index) {
    _currentBody = items[index].body;

    setState(() {
      _currentIndex = index;
    });
  }


  @override
  Widget build(BuildContext context) {

    List<BrnBottomTabBarItem> tempItems = items.map((item) => item.bottomTabBarItem).toList();

    var botomBar = BrnBottomTabBar(
      fixedColor: Colors.blue,
      currentIndex: _currentIndex,
      onTap:_onItemSelected,
      badgeColor: Colors.red,
      items: tempItems,
    );

  
    return Scaffold(
      body: _currentBody,
      bottomNavigationBar: botomBar,
    );
  
  }
}