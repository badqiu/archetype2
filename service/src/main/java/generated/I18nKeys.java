package generated;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

import com.company.project.springconfig.I18nConfig;
import com.company.project.util.I18nNamedMessageResolver;

//
// auto generated by Build.java, Please do not modify it directly.
// 
// if has error, you can delete this file.
//
@Component
public class I18nKeys implements MessageSourceAware{

	public static I18nNamedMessageResolver messageSource;
	
	static {
		init();
	}
	
	public static void init() {
		MessageSource ms = new I18nConfig().messageSource();
		new I18nKeys().setMessageSource(ms);
	}
	
	@Override
	public void setMessageSource(MessageSource value) {
		messageSource = new I18nNamedMessageResolver(value);
	}



	// en: my name is {name}, age:{age}
	// zh_CN: 我的名字是 {name}, 年龄:{age}
	public static String i18n_demo_hello = "demo.hello";

	// en: hi:{msg}
	// zh_CN: 你好:{msg}
	public static String i18n_demo_echo = "demo.echo";


	// en: my name is {name}, age:{age}
	// zh_CN: 我的名字是 {name}, 年龄:{age}
	public static String i18n_demo_hello(String name,String age){
		String[] params = new String[]{"name",name,"age",age};
		return messageSource.getNamedMessage(i18n_demo_hello,params);
	}

	// en: hi:{msg}
	// zh_CN: 你好:{msg}
	public static String i18n_demo_echo(String msg){
		String[] params = new String[]{"msg",msg};
		return messageSource.getNamedMessage(i18n_demo_echo,params);
	}
}