import 'package:auto_route/auto_route.dart';
import 'package:environment_checker_hub_ui/app/config/global_config.dart';

import 'router.gr.dart';

export './router.gr.dart';

@AutoRouterConfig(replaceInRouteName: 'View|Screen|Page|Widget,Route')
class AppRouter extends RootStackRouter {

  AppRouter({required super.navigatorKey});

  //default route: SomeHelp.page => /some-help
  @override
  List<AutoRoute> get routes => [
        AutoRoute(
          path: '/',
          page: HomeRoute.page, // 主页布局（包含左侧菜单+右侧内容）
          children: [
            AutoRoute(page: BottomBarHomeRoute.page, initial: false),
            AutoRoute(page: OdsEnvCheckTableRoute.page, initial: false),
            // AutoRoute(page: HomeRoute.page, initial: true),
          ],
        ),
        RedirectRoute(path: '*', redirectTo: '/'),
      ];
}

final globalAppRouter = AppRouter(navigatorKey: GlobalConfig.globalNavigatorKey);
