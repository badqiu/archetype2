import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'package:auto_route/auto_route.dart';
import 'package:environment_checker_hub_ui/app/all.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: '用户管理',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        cardTheme: CardTheme(
          elevation: 2,
          margin: EdgeInsets.symmetric(vertical: 4, horizontal: 8),
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(8),
          ),
        ),
      ),
      home: DemoUserCrudPage(),
    );
  }
}

class User {
  final int id;
  final String username;
  final int age;
  final int height;
  final double weight;
  final double money;
  final int sex;
  final DateTime birthDate;
  final String props;

  User({
    required this.id,
    required this.username,
    required this.age,
    required this.height,
    required this.weight,
    required this.money,
    required this.sex,
    required this.birthDate,
    required this.props,
  });

  factory User.fromJson(Map<String, dynamic> json) => User(
        id: json['id'],
        username: json['username'],
        age: json['age'],
        height: json['height'],
        weight: json['weight'].toDouble(),
        money: json['money'].toDouble(),
        sex: json['sex'],
        birthDate: DateTime.parse(json['birth_date']),
        props: json['props'],
      );

  Map<String, dynamic> toJson() => {
        'id': id,
        'username': username,
        'age': age,
        'height': height,
        'weight': weight,
        'money': money,
        'sex': sex,
        'birth_date': birthDate.toIso8601String(),
        'props': props,
      };
}

@RoutePage()
class DemoUserCrudPage extends StatefulWidget {
  @override
  _DemoUserCrudPageState createState() => _DemoUserCrudPageState();
}

class _DemoUserCrudPageState extends State<DemoUserCrudPage> {
  final ScrollController _scrollController = ScrollController();
  List<User> _users = [];
  int _currentPage = 1;
  final int _pageSize = 10;
  bool _isLoading = false;
  bool _hasMore = true;

  @override
  void initState() {
    super.initState();
    _loadData();
    _scrollController.addListener(_scrollListener);
  }

  Future<void> _loadData() async {
    if (_isLoading) return;
    
    setState(() => _isLoading = true);
    
    try {
      final response = await http.post(
        Uri.parse('https://your-api/users/query'),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({'page': _currentPage, 'pageSize': _pageSize}),
      );

      if (response.statusCode == 200) {
        final List data = json.decode(response.body);
        setState(() {
          _users.addAll(data.map((e) => User.fromJson(e)).toList());
          _hasMore = data.length >= _pageSize;
        });
      }
    } catch (e) {
      _showError('数据加载失败: ${e.toString()}');
    } finally {
      setState(() => _isLoading = false);
    }
  }

  void _scrollListener() {
    if (_scrollController.position.pixels ==
            _scrollController.position.maxScrollExtent &&
        !_isLoading &&
        _hasMore) {
      _currentPage++;
      _loadData();
    }
  }

  void _showError(String message) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text(message),
        backgroundColor: Colors.red,
      ),
    );
  }

  Widget _buildListItem(User user) {
    return Card(
      child: ListTile(
        title: Text(user.username),
        subtitle: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('年龄: ${user.age}  身高: ${user.height}cm'),
            Text('体重: ${user.weight}kg  余额: ¥${user.money.toStringAsFixed(2)}'),
          ],
        ),
        trailing: PopupMenuButton(
          itemBuilder: (context) => [
            PopupMenuItem(child: Text('编辑'), onTap: () => _showEditDialog(user)),
            PopupMenuItem(child: Text('删除'), onTap: () => _deleteUser(user.id)),
          ],
        ),
      ),
    );
  }

  Widget _buildEmptyState() {
    return Center(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Icon(Icons.people_alt_outlined, size: 64, color: Colors.grey),
          SizedBox(height: 16),
          Text('暂无用户数据', style: TextStyle(color: Colors.grey)),
          SizedBox(height: 8),
          ElevatedButton.icon(
            icon: Icon(Icons.add),
            label: Text('添加用户'),
            onPressed: _showAddDialog,
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('用户管理'),
        actions: [
          IconButton(
            icon: Icon(Icons.add),
            onPressed: _showAddDialog,
          ),
        ],
      ),
      body: Stack(
        children: [
          _users.isEmpty && !_isLoading
              ? _buildEmptyState()
              : ListView.builder(
                  controller: _scrollController,
                  itemCount: _users.length + (_hasMore ? 1 : 0),
                  itemBuilder: (context, index) {
                    if (index < _users.length) {
                      return _buildListItem(_users[index]);
                    }
                    return _hasMore
                        ? Center(child: Padding(
                            padding: EdgeInsets.all(16),
                            child: CircularProgressIndicator(),
                          ))
                        : SizedBox();
                  },
                ),
          if (_isLoading)
            LinearProgressIndicator(
              minHeight: 2,
              backgroundColor: Colors.transparent,
            ),
        ],
      ),
    );
  }

  // 添加/编辑用户对话框
  void _showAddDialog() => _showUserForm();
  void _showEditDialog(User user) => _showUserForm(user: user);

  void _showUserForm({User? user}) {
    print("_showUserForm()");
    // throw AppException("自定义异常,用于测试使用,throw AppException");
    

    final _formKey = GlobalKey<FormState>();
    final _username = TextEditingController(text: user?.username ?? '');
    // 其他字段控制器初始化...

    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text(user == null ? '添加用户' : '编辑用户'),
        content: Form(
          key: _formKey,
          child: SingleChildScrollView(
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                TextFormField(
                  controller: _username,
                  decoration: InputDecoration(labelText: '用户名'),
                  validator: (v) => v!.isEmpty ? '必填字段' : null,
                ),
                // 其他表单字段...
                SizedBox(height: 16),
                ElevatedButton(
                  onPressed: () async {
                    if (_formKey.currentState!.validate()) {
                      final newUser = User(
                        id: user?.id ?? 0,
                        username: _username.text,
                        age: 0, // 从其他字段获取实际值
                        height: 0,
                        weight: 0,
                        money: 0,
                        sex: 0,
                        birthDate: DateTime.now(),
                        props: '',
                      );
                      
                      try {
                        if (user == null) {
                          await http.post(
                            Uri.parse('https://your-api/users/create'),
                            body: json.encode(newUser.toJson()),
                            headers: {'Content-Type': 'application/json'},
                          );
                        } else {
                          await http.post(
                            Uri.parse('https://your-api/users/update'),
                            body: json.encode(newUser.toJson()),
                            headers: {'Content-Type': 'application/json'},
                          );
                        }
                        _currentPage = 1;
                        _users.clear();
                        _loadData();
                        Navigator.pop(context);
                      } catch (e) {
                        _showError('保存失败: ${e.toString()}');
                      }
                    }
                  },
                  child: Text('保存'),
                )
              ],
            ),
          ),
        ),
      ),
    );
  }

  Future<void> _deleteUser(int id) async {
    try {
      await http.post(
        Uri.parse('https://your-api/users/remove'),
        body: json.encode({'id': id}),
        headers: {'Content-Type': 'application/json'},
      );
      setState(() => _users.removeWhere((u) => u.id == id));
    } catch (e) {
      _showError('删除失败: ${e.toString()}');
    }
  }

  @override
  void dispose() {
    _scrollController.dispose();
    super.dispose();
  }
}