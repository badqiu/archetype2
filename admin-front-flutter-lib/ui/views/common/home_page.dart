
import 'package:flutter/material.dart';
import 'package:environment_checker_hub_ui/app/all.dart';
import 'package:auto_route/auto_route.dart';
// home_page.dart


@RoutePage()
class HomePage extends StatelessWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('AGV货位遍历')),
      body: Row(
        children: [
          // 左侧菜单
          _buildLeftMenu(context),
          // 右侧内容区域
          const Expanded(
            child: Center(
              child: AutoRouter(),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildLeftMenu(BuildContext context) {
    return Container(
      width: 240,
      color: Colors.grey[100],
      child: ListView(
        children: [
          
          _MenuTile(
            title: '检测数据',
            onTap: () => context.navigateTo(OdsEnvCheckTableRoute()),
          ),
          _MenuTile(
            title: '检测原始日志',
            onTap: () => context.navigateTo(OdsEnvCheckTableRoute()),
          ),
          _MenuTile(
            title: 'AGV遍历',
            onTap: () => context.navigateTo(OdsEnvCheckTableRoute()),
          ),
          _MenuTile(
            title: '数据看板',
            onTap: () => context.navigateTo(BottomBarHomeRoute()),
          ),
          _MenuTile(
            title: '测试菜单',
            onTap: () => context.navigateTo(BottomBarHomeRoute()),
          ),
        ],
      ),
    );
  }
}

class _MenuTile extends StatelessWidget {
  final String title;
  final VoidCallback onTap;

  const _MenuTile({required this.title, required this.onTap});

  @override
  Widget build(BuildContext context) {
    return ListTile(
      title: Text(title),
      leading: const Icon(Icons.chevron_right),
      onTap: onTap,
    );
  }
}