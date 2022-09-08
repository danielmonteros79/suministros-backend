package com.sa.su.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropUtil {
	private static Properties prop = null;
	private static Logger log = Logger.getLogger(PropUtil.class);
	private static ServletContext context;

	public PropUtil(ServletContext context) {
		PropUtil.context = context;
	}
	
	public static Properties getProp() {
		if (null == prop) {
			prop = new Properties();
			try {
				prop.load(new FileInputStream(getSysConfPathResource("suministros.properties")));
			} catch (IOException e) {
				log.error(e);
			}
		}
		return prop;
	}
	
	public static String getSysConfPathResource(String fileName) {
		File f = new File(context.getRealPath(fileName));
		if (fileName.startsWith("/"))
			fileName = fileName.substring(1);

		String pathfinal = f.getParentFile().getParentFile().getAbsolutePath().concat("/syscfg/".concat(fileName));

		return pathfinal;
	}
}
