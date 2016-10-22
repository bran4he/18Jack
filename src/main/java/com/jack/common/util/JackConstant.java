package com.jack.common.util;

import java.util.Properties;

import org.apache.log4j.Logger;

public class JackConstant {

	private static Logger logger = Logger.getLogger(JackConstant.class);
	
	public static final String SAVE_PATH =PropertiesUtil.getValue("save_folder");
	public static final String URL_BASE =PropertiesUtil.getValue("base_url");
	
	public static final Long JACK_ANALYSIS = 60L * 1000 * Long.parseLong(PropertiesUtil.getValue("jackAnalysis"));
	public static final Long FILE_WORK = 60L * 1000 * Long.parseLong(PropertiesUtil.getValue("fileWalk"));
	public static final Long FILE_MERGE = 60L * 1000 * Long.parseLong(PropertiesUtil.getValue("fileMerge"));
	
	static {
		if("Y".equals(PropertiesUtil.getValue("user_proxy"))){
			Properties prop = System.getProperties();
			String socksProxyHost = PropertiesUtil.getValue("socksProxyHost");
			String socksProxyPort = PropertiesUtil.getValue("socksProxyPort");
			logger.info("load system proxy data");
			prop.put("socksProxyHost", socksProxyHost);
			prop.put("socksProxyPort", socksProxyPort);
		}
	}
}
