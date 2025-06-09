enum ParamType{
  int,
  double,
  string,
}

class Param{
  final String code;
  final String name;
  final ParamType type;
  final String placeholder;

  const Param({
    required this.code,
    required this.name,
    this.type = ParamType.int,
    this.placeholder = "",
  });
  
}

