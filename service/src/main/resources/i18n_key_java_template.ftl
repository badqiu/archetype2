package generated;

// auto generate by Build.java, cannot modify this file;

public class I18nKeys {
<#list i18nEnMap?keys as key>

	<#assign newKey = key?replace('.', '_')?replace('-', '_')>
	// en: ${i18nEnMap[key]}
	// zh_CN: ${i18nZhCNMap[key]}
	public static String i18n_${newKey} = "${key}";
</#list>
}