import 'package:flutter/material.dart';
import 'package:auto_route/auto_route.dart';
import 'package:environment_checker_hub_ui/app/all.dart';

@RoutePage()
class OtherListPage extends StatefulWidget {
  @override
  _OtherListState createState() => _OtherListState();
}

class _OtherListState extends State<OtherListPage> {



  Widget get sizeBox => SizedBox(height: 16.0, width: 10);
  Size buttonMaxSize = Size(60, 40);
  
  
    @override
  void initState() {
    super.initState();
  }

  void listenMqttMessage() async {
    // List<int> agvIds = [1,2,3,4,5,6,7,8,9,10];
    List<int> agvIds = [];
  }

  
  @override
  Widget build(BuildContext context) {
    Logger.info("CarList.build()");

    return Scaffold(
      appBar: AppBar(
        title: Text('其它'),
      ),
      body: Padding(
        padding: const EdgeInsets.symmetric(vertical: 8.0, horizontal: 16.0),
        child: Card(
          child: Padding(
            padding: const EdgeInsets.all(16.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                



                InkWell(
                  onTap: () {
                    // context.router.push(DemoUserCrudRoute());
                  },
                  child: const ListTile(
                    title: Text("demo用户列表"),
                    trailing: Icon(Icons.arrow_forward_ios),
                  ),
                ),



                InkWell(
                  onTap: () {
                    listenMqttMessage();
                  },
                  child: const ListTile(
                    title: Text("测试 WatchAgvMessages"),
                    trailing: Icon(Icons.arrow_forward_ios),
                  ),
                ),






              ],
            ),
          ),
        ),
      ),
    );
  }
}