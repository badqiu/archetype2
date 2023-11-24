package com.company.project.web.binder;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.github.rapid.common.util.DateConvertUtil;

public class SmartCustomDateEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
    	if(StringUtils.isBlank(text)) {
    		setValue(null);
    		return;
    	}
    	
		String dateFormat = getDateFormat(text);
		if(dateFormat == null) {
			setValue(new Date(Long.decode(text)));
		}else {
			Date date = DateConvertUtil.parse(text, dateFormat);
			setValue(date);
		}
		
    }
    
    public static String getDateFormat(String text) {
    	if(text == null) return null;
    	
    	if(text.contains("-")) {
			if(text.contains(":")) {
				//HH:mm
				return  "yyyy-MM-dd HH:mm";
			}else {
				return  "yyyy-MM-dd";
			}
		}
    	
    	return null;
    }

    /**
     * @see java.beans.PropertyEditorSupport#getAsText()
     	* 后台给前端返回响应信息,也要处理Date类型,将Date类型转为String.
     */
    @Override
    public String getAsText() {
        Date value = (Date) getValue();
        if(value == null) return null;
        return String.valueOf(value.getTime());
    }

}