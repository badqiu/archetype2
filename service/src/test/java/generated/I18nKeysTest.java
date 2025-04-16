package generated;

import org.junit.Test;
import org.springframework.context.MessageSource;

import com.company.project.springconfig.ValidatorConfig;

public class I18nKeysTest {

	public I18nKeysTest() {
		MessageSource ms = new ValidatorConfig().validationMessageSource();
		new I18nKeys().setMessageSource(ms);
	}
    
	@Test
	public void test_i18n_demo_hello() {
		String i18n_demo_hello = I18nKeys.i18n_demo_hello("tiger","100");
		System.out.println(i18n_demo_hello);
		
		String i18n_demo_echo = I18nKeys.i18n_demo_echo(null);
		System.out.println(i18n_demo_echo);
	}

}
