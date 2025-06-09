

import '../../generated/protobuf/google/protobuf/timestamp.pb.dart';
import 'package:fixnum/fixnum.dart';



Timestamp? toTimestamp(DateTime? dateTime) {
  if (dateTime == null) return null;

  return Timestamp.fromDateTime(dateTime);
}

double? toDouble(String? str) {
  if (str == null) return null;
  if (str.isEmpty) return null;

  return double.tryParse(str);
}

Int64? toInt64(String? str) {
  if (str == null) return null;
  if (str.isEmpty) return null;

  return Int64.tryParseInt(str);
}

int? toInt(String? str) {
  if (str == null) return null;
  if (str.isEmpty) return null;

  return int.tryParse(str);
}
