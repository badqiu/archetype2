import 'package:flutter/material.dart';
import 'package:environment_checker_hub_ui/app/all.dart';


class MainApp extends StatefulWidget {
  const MainApp({Key? key}) : super(key: key);

  @override
  MainAppState createState() => MainAppState();
}

class MainAppState extends State<MainApp> {
  Locale? locale_;
  final _appRouter = globalAppRouter;
  
  @override
  Widget build(BuildContext context) {
    
    return MaterialApp.router(
      routerConfig: _appRouter.config(),
    );

  }
}
