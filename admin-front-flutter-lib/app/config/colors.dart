import 'package:flutter/material.dart';


class ColorConstants {
  //base colors
  static const Color colorPrimary = Color(0xFF0D6EFD);   // Bootstrap 5 主蓝色
  static const Color colorSecondary = Color(0xFF6C757D); // 中性灰
  static const Color colorSuccess = Color(0xFF198754);   // 成功绿
  static const Color colorError = Color(0xFFDC3545);     // 错误红
  static const Color colorWarn = Color(0xFFFFC107);      // 警告黄
  static const Color colorInfo = Color(0xFF0DCAF0);      // 信息蓝
  static const Color colorDisable = Color(0xFFADB5BD);   // 禁用灰（比Secondary浅）
  static const Color colorLight = Color(0xFFF8F9FA);     // 浅背景色
  static const Color colorDark = Color(0xFF212529);      // 深文字色
  static const Color colorOffline =  Color.fromARGB(255, 236, 232, 232);
  
  // ========== 新增容器颜色 ==========
  // 主色调容器（浅背景）
  // ========== 更新后的容器颜色（25%透明度） ==========
  static const Color colorPrimaryContainer = Color(0x260D6EFD);   // 15%透明度
  static const Color colorSecondaryContainer = Color(0x266C757D); 
  static const Color colorSuccessContainer = Color(0x26198754);  
  static const Color colorErrorContainer = Color(0x26DC3545);    
  static const Color colorWarnContainer = Color(0x26FFC107);     
  static const Color colorInfoContainer = Color(0x260DCAF0);     
  static const Color colorDisableContainer = Color(0x26ADB5BD);  
  static const Color colorLightContainer = Color(0xFFF1F3F5);     // 更柔和的浅灰（替代原E9ECEF）
  static const Color colorDarkContainer = Color(0x26212529);       // 15%深灰
  static const Color colorOfflineContainer = Color.fromARGB(255, 236, 232, 232);   // 保持纯浅色


  // 扩展色（Bootstrap的Hover/Active状态色）
  static const Color colorPrimaryDark = Color(0xFF0B5ED7);  // 主色深色版
  static const Color colorSuccessLight = Color(0xFFD1E7DD); // 成功浅背景
  static const Color colorErrorLight = Color(0xFFF8D7DA);   // 错误浅背景
}


// /// Light [ColorScheme] made with FlexColorScheme v8.1.1.
// /// Requires Flutter 3.22.0 or later.
// const ColorScheme lightColorScheme = ColorScheme(
//   brightness: Brightness.light,
//   primary: Color(0xFF6750A4),
//   onPrimary: Color(0xFFFFFFFF),
//   primaryContainer: Color(0xFFEADDFF),
//   onPrimaryContainer: Color(0xFF000000),
//   primaryFixed: Color(0xFFDDD8EC),
//   primaryFixedDim: Color(0xFFBCB2D9),
//   onPrimaryFixed: Color(0xFF2B2245),
//   onPrimaryFixedVariant: Color(0xFF332851),
//   secondary: Color(0xFF625B71),
//   onSecondary: Color(0xFFFFFFFF),
//   secondaryContainer: Color(0xFFE8DEF8),
//   onSecondaryContainer: Color(0xFF000000),
//   secondaryFixed: Color(0xFFDCDBE1),
//   secondaryFixedDim: Color(0xFFBDBAC4),
//   onSecondaryFixed: Color(0xFF242229),
//   onSecondaryFixedVariant: Color(0xFF2D2933),
//   tertiary: Color(0xFF7D5260),
//   onTertiary: Color(0xFFFFFFFF),
//   tertiaryContainer: Color(0xFFFFD8E4),
//   onTertiaryContainer: Color(0xFF000000),
//   tertiaryFixed: Color(0xFFE5D8DC),
//   tertiaryFixedDim: Color(0xFFC9B4BC),
//   onTertiaryFixed: Color(0xFF2E1F24),
//   onTertiaryFixedVariant: Color(0xFF39262C),
//   error: Color(0xFFBA1A1A),
//   onError: Color(0xFFFFFFFF),
//   errorContainer: Color(0xFFFFDAD6),
//   onErrorContainer: Color(0xFF000000),
//   surface: Color(0xFFFCFCFC),
//   onSurface: Color(0xFF111111),
//   surfaceDim: Color(0xFFE0E0E0),
//   surfaceBright: Color(0xFFFDFDFD),
//   surfaceContainerLowest: Color(0xFFFFFFFF),
//   surfaceContainerLow: Color(0xFFF8F8F8),
//   surfaceContainer: Color(0xFFF3F3F3),
//   surfaceContainerHigh: Color(0xFFEDEDED),
//   surfaceContainerHighest: Color(0xFFE7E7E7),
//   onSurfaceVariant: Color(0xFF393939),
//   outline: Color(0xFF919191),
//   outlineVariant: Color(0xFFD1D1D1),
//   shadow: Color(0xFF000000),
//   scrim: Color(0xFF000000),
//   inverseSurface: Color(0xFF2A2A2A),
//   onInverseSurface: Color(0xFFF1F1F1),
//   inversePrimary: Color(0xFFF0E9FF),
//   surfaceTint: Color(0xFF6750A4),
// );

// /// Dark [ColorScheme] made with FlexColorScheme v8.1.1.
// /// Requires Flutter 3.22.0 or later.
// const ColorScheme darkColorScheme = ColorScheme(
//   brightness: Brightness.dark,
//   primary: Color(0xFFD0BCFF),
//   onPrimary: Color(0xFF000000),
//   primaryContainer: Color(0xFF4F378B),
//   onPrimaryContainer: Color(0xFFFFFFFF),
//   primaryFixed: Color(0xFFDDD8EC),
//   primaryFixedDim: Color(0xFFBCB2D9),
//   onPrimaryFixed: Color(0xFF2B2245),
//   onPrimaryFixedVariant: Color(0xFF332851),
//   secondary: Color(0xFFCCC2DC),
//   onSecondary: Color(0xFF000000),
//   secondaryContainer: Color(0xFF4A4458),
//   onSecondaryContainer: Color(0xFFFFFFFF),
//   secondaryFixed: Color(0xFFDCDBE1),
//   secondaryFixedDim: Color(0xFFBDBAC4),
//   onSecondaryFixed: Color(0xFF242229),
//   onSecondaryFixedVariant: Color(0xFF2D2933),
//   tertiary: Color(0xFFEFB8C8),
//   onTertiary: Color(0xFF000000),
//   tertiaryContainer: Color(0xFF633B48),
//   onTertiaryContainer: Color(0xFFFFFFFF),
//   tertiaryFixed: Color(0xFFE5D8DC),
//   tertiaryFixedDim: Color(0xFFC9B4BC),
//   onTertiaryFixed: Color(0xFF2E1F24),
//   onTertiaryFixedVariant: Color(0xFF39262C),
//   error: Color(0xFFFFB4AB),
//   onError: Color(0xFF000000),
//   errorContainer: Color(0xFF93000A),
//   onErrorContainer: Color(0xFFFFFFFF),
//   surface: Color(0xFF080808),
//   onSurface: Color(0xFFF1F1F1),
//   surfaceDim: Color(0xFF060606),
//   surfaceBright: Color(0xFF2C2C2C),
//   surfaceContainerLowest: Color(0xFF010101),
//   surfaceContainerLow: Color(0xFF0E0E0E),
//   surfaceContainer: Color(0xFF151515),
//   surfaceContainerHigh: Color(0xFF1D1D1D),
//   surfaceContainerHighest: Color(0xFF282828),
//   onSurfaceVariant: Color(0xFFCACACA),
//   outline: Color(0xFF777777),
//   outlineVariant: Color(0xFF414141),
//   shadow: Color(0xFF000000),
//   scrim: Color(0xFF000000),
//   inverseSurface: Color(0xFFE8E8E8),
//   onInverseSurface: Color(0xFF2A2A2A),
//   inversePrimary: Color(0xFF5D556B),
//   surfaceTint: Color(0xFFD0BCFF),
// );
