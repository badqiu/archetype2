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
	public void test_i18n_javax_validation_constraints_DecimalMax_message() {
		String value = I18nKeys.i18n_javax_validation_constraints_DecimalMax_message("100");
		System.out.println(value);
	}

}
