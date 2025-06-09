

import 'package:flutter/widgets.dart';

class TableColumn<T> {
  String label;
  Widget Function(T item) dataCellBuilder;
  bool sortable;
  String? columnName;

  TableColumn({
    required this.label,
    required this.dataCellBuilder,
    this.sortable = false,
    this.columnName,
  });
}