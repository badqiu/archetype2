package com.company.project.util;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.github.rapid.common.util.PropertiesUtil;
import com.github.rapid.common.util.ReflectUtil;

public class NacosConfigUtil {
	
	private static Logger logger = LoggerFactory.getLogger(NacosConfigUtil.class);
	
	private static int defaultTimeoutMs = 1000 * 10;
	
	public static ConfigService createConfigService(String serverAddr,String namespace) throws NacosException {
		Properties properties = new Properties();  
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);  
        
        if(StringUtils.isNotBlank(namespace)) {
        	properties.put(PropertyKeyConst.NAMESPACE, namespace);  
        }
        
        // 创建ConfigService实例  
        ConfigService configService = NacosFactory.createConfigService(properties);
		return configService;
	}  
	
	private static String getDataIdByClass(Class clazz) {
		return clazz.getName();
	}
	
    public static void autoRefreshConfigClass(Class clazz,  String group, ConfigService configService) throws NacosException {
    	autoRefreshConfigClass(clazz,getDataIdByClass(clazz),group,configService);
    }
    
    public static void autoRefreshConfigClass(Class clazz, String dataId, String group, ConfigService configService)
			throws NacosException {
    	
		String content = configService.getConfig(dataId, group, defaultTimeoutMs);  
		logger.info("autoRefreshConfigClass() dateId:{} group:{}  configContent:{} ",dataId,group,content);  
        
        modifyAllStaticVariablesByConfigProperties(clazz, content);
        
        configService.addListener(dataId, group, new Listener() {  
            public void receiveConfigInfo(String configInfo) {  
                logger.info("receiveConfigInfo() dateId:{} group:{} get nacos config:{} ",dataId,group,configInfo);  
                modifyAllStaticVariablesByConfigProperties(clazz, configInfo);
            }
            public Executor getExecutor() {  
                return null;  
            }  
        });
	}
    
	private static void modifyAllStaticVariablesByConfigProperties(Class clazz, String configInfo) {
		if(StringUtils.isBlank(configInfo)) return;
		
		Properties props = PropertiesUtil.loadProperties(configInfo);
        ReflectUtil.modifyAllStaticVariables(clazz, (Map)props);
	}
	
}
