import 'package:environment_checker_hub_ui/generated/all.dart';
import 'package:environment_checker_hub_ui/app/all.dart';

final helloWorldProtoServiceClient = HelloWorldProtoServiceClient(GlobalConfig.getGrpcChannel());
final odsEnvCheckRpcServiceClient = OdsEnvCheckRpcServiceClient(GlobalConfig.getGrpcChannel());


