package org.developerworld.commons.db.dynamicdatasource.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.developerworld.commons.db.datasource.builder.DataSourceBuilder;
import org.developerworld.commons.db.dynamicdatasource.DataSourcesFactory;

/**
 * 基于文件库系统中的properties文件进行配置的数据源工厂
 * 
 * @author Roy Huang
 * @version 20140605
 * @deprecated see org.developerworld.commons.dbsource
 */
public class FileSystemPropertiesDataSourcesFactory implements
		DataSourcesFactory {

	private String configFileDirectoryPath;
	private DataSourceBuilder dataSourceBuilder;

	public void setConfigFileDirectoryPath(String configFileDirectoryPath) {
		this.configFileDirectoryPath = configFileDirectoryPath;
	}

	public void setDataSourceBuilder(DataSourceBuilder dataSourceBuilder) {
		this.dataSourceBuilder = dataSourceBuilder;
	}

	public Map<String, DataSource> buildDataSources() {
		Map<String, DataSource> rst = null;
		try {
			File configFileDirectory = new File(configFileDirectoryPath);
			// 获取所有配置文件
			List<File> configFiles = getDirectoryAllFiles(configFileDirectory);
			// 根据配置文件集合，获取配置信息集合
			List<Map<String, String>> configMaps = getConfigMaps(configFiles);
			// 根据配置信息，构建数据源集合
			rst = buildDataSourceMap(configMaps);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return rst;
	}

	/**
	 * 根据配置信息，构造配置源
	 * 
	 * @param configMaps
	 * @return
	 */
	private Map<String, DataSource> buildDataSourceMap(
			List<Map<String, String>> configMaps) {
		Map<String, DataSource> rst = new HashMap<String, DataSource>();
		for (Map<String, String> configMap : configMaps) {
			String key = configMap.get(DATA_SOURCE_KEY);
			if (StringUtils.isNotBlank(key)) {
				DataSource dataSource = dataSourceBuilder
						.buildDataSource(configMap);
				if (dataSource != null)
					rst.put(key, dataSource);
			}
		}
		return rst;
	}

	/**
	 * 获取配置信息集合
	 * 
	 * @param configFiles
	 * @return
	 * @throws IOException
	 */
	private List<Map<String, String>> getConfigMaps(List<File> configFiles)
			throws IOException {
		List<Map<String, String>> rst = new ArrayList<Map<String, String>>();
		for (File configFile : configFiles) {
			Properties propertie = new Properties();
			InputStream is = new FileInputStream(configFile);
			try {
				// 加载配置文件内容
				propertie.load(is);
				Map<String, String> configMap = new HashMap<String, String>();
				Iterator<Entry<Object, Object>> iterator = propertie.entrySet()
						.iterator();
				// 转换为配置map
				while (iterator.hasNext()) {
					Entry<Object, Object> entry = iterator.next();
					configMap.put((String) entry.getKey(),
							(String) entry.getValue());
				}
				rst.add(configMap);
			} finally {
				is.close();
			}
		}
		return rst;
	}

	/**
	 * 获取目录下的所有文件
	 * 
	 * @param directory
	 * @return
	 */
	private List<File> getDirectoryAllFiles(File directory) {
		List<File> rst = new ArrayList<File>();
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isFile())
				rst.add(file);
			else if (file.isDirectory())
				rst.addAll(getDirectoryAllFiles(file));
		}
		return rst;
	}

}
