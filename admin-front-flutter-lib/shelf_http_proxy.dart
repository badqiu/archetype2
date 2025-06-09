
// proxy_server.dart
import 'package:shelf/shelf.dart';
import 'package:shelf/shelf_io.dart' as io;
import 'package:shelf_proxy/shelf_proxy.dart';
import 'package:shelf_router/shelf_router.dart';

Middleware _addCorsHeaders() {
  return (Handler handler) {
    return (Request request) async {
      final response = await handler(request);
      return response.change(
        headers: _corsHeaders,
      );
    };
  };
}

const _corsHeaders = {
  'Access-Control-Allow-Origin': '*',          // 允许所有域名（生产环境应指定具体域名）
  'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
  'Access-Control-Allow-Headers': 'Origin, Content-Type, Authorization',
  'Access-Control-Allow-Credentials': 'true',  // 允许携带 Cookie
};

void main() async {
  final proxy = proxyHandler('http://localhost:8800');

  final handler = const Pipeline()
      .addMiddleware(_addCorsHeaders())
      .addHandler(proxy);

  await io.serve(handler, 'localhost', 18800);
  print('Proxy server running on http://localhost:18800');
}