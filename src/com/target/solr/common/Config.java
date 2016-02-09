package com.target.solr.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * 系统配置读取
 * <p>Title: Config</p>
 * <p>Description: </p>
 */
public class Config {

	private static String connectionUrl;
	private static String connectionUsername;
	private static String connectionPassword;
	private static String indexPath;
	private static int searchMax;
	private static int pageSize;
	
	private static Properties properties;
	static{
		properties = new Properties();
		InputStream in = Config.class.getResourceAsStream("/config.properties");
		try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (properties.containsKey("connection.url")) {
			connectionUrl = properties.getProperty("connection.url");
		}
		if (properties.containsKey("connection.username")) {
			connectionUsername = properties.getProperty("connection.username");
		}                                                                                  
		if (properties.containsKey("connection.password")) {
			connectionPassword = properties.getProperty("connection.password");
		}
		if (properties.containsKey("index.path")) {
			indexPath = properties.getProperty("index.path");
		}
		if (properties.containsKey("search.max")) {
			String max = properties.getProperty("search.max");
			searchMax = Integer.parseInt(max);
		}
		if (properties.containsKey("search.pagesize")) {
			String size = properties.getProperty("search.pagesize");
			pageSize = Integer.parseInt(size);
		}
	}
	public static String getConnectionUrl() {
		return connectionUrl;
	}
	public static String getConnectionUsername() {
		return connectionUsername;
	}
	public static String getConnectionPassword() {
		return connectionPassword;
	}
	public static String getIndexPath() {
		return indexPath;
	}
	public static int getSearchMax() {
		return searchMax;
	}
	public static int getPageSize() {
		return pageSize;
	}
	
}
