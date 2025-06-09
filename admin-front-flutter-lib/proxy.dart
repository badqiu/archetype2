import 'dart:io';
import 'package:shelf/shelf_io.dart' as shelf_io;
import 'package:shelf_proxy/shelf_proxy.dart';
import 'dart:convert';
import 'dart:async';
import 'dart:io';
import 'dart:typed_data';

final proxyHost = "192.168.1.28";


void main() async {
  
  start_http_proxy_server('http://${proxyHost}:8802/',8802); //dumper-server
  start_http_proxy_server('http://${proxyHost}:8080/',8080); //nginx web
  //websocket proxy
  await startWebSocketProxy(9000, 'ws://${proxyHost}:9000/'); //remote controller
  start_http_proxy_server('http://${proxyHost}:9000/',9001);
  // await startTcpProxyServer(9000, proxyHost, 9000);


  await startTcpProxyServer(8800,proxyHost,8800); //exception tracer
}

Future<void> startTcpProxyServer(int proxyPort, String targetHost, int targetPort) async {
  try {
    // 监听本地端口
    final server = await ServerSocket.bind(InternetAddress.loopbackIPv4, proxyPort);
    print('startTcpProxyServer() listening on localhost:$proxyPort');
  
  
    await for (final clientSocket in server) {
      handleTcpProxy(clientSocket, targetHost, targetPort);
    }
  } catch (e) {
    print('Failed to start proxy server: $e');
  }
}


void configServer(HttpServer server) {
  // 这里设置请求策略，允许所有
  server.defaultResponseHeaders.add('Access-Control-Allow-Origin', '*');
  server.defaultResponseHeaders.add('Access-Control-Allow-Credentials', true);
  server.defaultResponseHeaders.add('Access-Control-Allow-Methods', '*');
  server.defaultResponseHeaders.add('Access-Control-Allow-Headers', '*');
  server.defaultResponseHeaders.add('Access-Control-Max-Age', '3600');
}

void start_http_proxy_server(target,port) async{
  var handler = proxyHandler(target);
  var server = await shelf_io.serve(handler, 'localhost', port);
  configServer(server);

  print('Serving at http://${server.address.host}:${server.port} to $target');
}
// start by: dart proxy.dart


Future<void> startWebSocketProxy(int port, String target) async {
  print('Starting WebSocket proxy on port $port to target $target');

  try {
    // 监听本地端口以接收 WebSocket 连接
    await HttpServer.bind(InternetAddress.loopbackIPv4, port).then((server) {
      server.listen((HttpRequest request) async {
        if (WebSocketTransformer.isUpgradeRequest(request)) {
          var socket = await WebSocketTransformer.upgrade(request);
          handleWebSocketConnection(socket, target);
        } else {
          request.response.close();
        }
      });
    });
  } catch (e) {
    print('Failed to start WebSocket proxy: $e');
  }
}

void handleWebSocketConnection(WebSocket socket, String target) {
  // 建立到目标 WebSocket 服务器的连接
  WebSocket.connect(target).then((WebSocket targetSocket) {
    // 处理从客户端到目标服务器的消息
    socket.listen((message) {
      targetSocket.add(message);
    }, onDone: () => targetSocket.close());

    // 处理从目标服务器到客户端的消息
    targetSocket.listen((message) {
      socket.add(message);
    }, onDone: () => socket.close());
  }).catchError((error) {
    var errorMessage = 'Failed to connect to target WebSocket: $error';
    socket.add(errorMessage);
    socket.close();
  });
}


Future<void> handleTcpProxy(Socket clientSocket, String targetHost, int targetPort) async {
  try {
    // 连接到目标服务器
    final targetSocket = await Socket.connect(targetHost, targetPort);
    print('Connected to target server at $targetHost:$targetPort');


    // 从客户端到目标服务器的数据转发
    clientSocket.listen(
      (Uint8List data) {
        print('Forwarding data from client to target: $data');
        targetSocket.add(data);
      },
      onDone: () {
        targetSocket.close();
      },
      onError: (error) {
        print('Error forwarding data from client to target: $error');
        targetSocket.close();
      },
    );


    // 从目标服务器到客户端的数据转发
    targetSocket.listen(
      (Uint8List data) {
        print('Forwarding data from target to client: $data');
        clientSocket.add(data);
      },
      onDone: () {
        clientSocket.close();
      },
      onError: (error) {
        print('Error forwarding data from target to client: $error');
        clientSocket.close();
      },
    );
  } catch (e) {
    print('Failed to connect to target server: $e');
    clientSocket.close();
  }
}





