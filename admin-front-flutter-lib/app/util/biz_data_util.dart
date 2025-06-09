class LocationDto {
  int? x;
  int? y;
  
  LocationDto(this.x, this.y);

  static LocationDto NULL = LocationDto(null, null);
  
  static LocationDto tryParse(String str) {
    if (str.isEmpty) return NULL;
    
    final parts = str.split(',');
    if (parts.length == 2) {
      final x = int.tryParse(parts[0]);
      final y = int.tryParse(parts[1]);
      return LocationDto(x,y);
    }else if(parts.length == 1){
      final x = int.tryParse(parts[0]);
      return LocationDto(x,null);
    }else {
      return NULL;
    }
  }

}