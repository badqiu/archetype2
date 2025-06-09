import 'package:flutter/material.dart';
import 'package:grpc/grpc_web.dart';
import 'package:environment_checker_hub_ui/generated/protobuf/galaxis/helloworld.pbgrpc.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(title: Text('gRPC Demo')),
        body: Center(
          child: TextButton(
            onPressed: _callGrpc,
            child: Text('Send Request'),
          ),
        ),
      ),
    );
  }

  Future<void> _callGrpc() async {
    final channel = GrpcWebClientChannel.xhr(
      Uri.parse('http://localhost:8589'), // 服务端地址
    );

    final helloWorldProtoServiceClient = HelloWorldProtoServiceClient(channel, options: CallOptions(timeout: Duration(seconds: 20)));
    
    try {

      final response = await helloWorldProtoServiceClient.sayHello(
        HelloRequest(name : 'Flutter User')
      );

      print('Server response: ${response.message}');
    } catch (e) {
      print('Error: $e');
    }
    await channel.shutdown();
  }
}