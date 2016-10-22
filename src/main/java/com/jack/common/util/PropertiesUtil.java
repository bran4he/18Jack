package com.jack.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

public class PropertiesUtil {

	private static Logger logger = Logger.getLogger(PropertiesUtil.class);

	// public static void main(String[] args) {
	// logger.info(PropertiesUtil.getValue("test", "/init.properties"));
	// }

	public static Map<String, String> read(String fileName) {
		Properties properties = new Properties();

		Map<String, String> headerMap = new HashMap<String, String>();

		// InputStream ins =
		// PropTest.class.getResourceAsStream("/demo.properties");
		InputStream ins = PropertiesUtil.class.getResourceAsStream(fileName);

		try {
			properties.load(ins);
		} catch (IOException e) {
			logger.info("load properties file stream error");
			e.printStackTrace();
		}
		Set<Object> keySet = properties.keySet();
		logger.info("===================loading properties");
		for (Object object : keySet) {
			logger.info(object.toString() + ":" + properties.getProperty(object.toString()));
			headerMap.put(object.toString(), properties.getProperty(object.toString()));
		}
		logger.info("===================loaded");
		return headerMap;
	}

	public static String getValue(String key, String fileName) {
		Properties props = new Properties();
		try {
			InputStream ins = PropertiesUtil.class.getResourceAsStream(fileName);
			props.load(ins);
			String value = props.getProperty(key);
			logger.info(key + " :key's value is：" + value);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getValue(String key) {
		Properties props = new Properties();
		try {
			InputStream ins = PropertiesUtil.class.getResourceAsStream("/init.properties");
			props.load(ins);
			String value = props.getProperty(key);
			logger.info(key + ":key's value is：" + value);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void write(String filePath, String key, String value) throws FileNotFoundException {
		Properties prop = new Properties();
		File file = new File(PropertiesUtil.class.getClassLoader().getResource("").getPath() + filePath);
		try {
			logger.info("file path：" + file.getCanonicalPath());
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// InputStream ins = new FileInputStream(file);
		InputStream ins = PropertiesUtil.class.getResourceAsStream("/" + filePath);
		try {
			prop.load(ins);

			OutputStream out = new FileOutputStream(file);
			prop.setProperty(key, value);
			prop.store(out, "update");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
