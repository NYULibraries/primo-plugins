/**
 * 
 */
package edu.nyu.libraries.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

/**
 * @author Scot Dalton
 *
 */
public class DataWarehouseModule extends AbstractModule {
	private final static String PROPERTIES_RESOURCE = 
		"META-INF/datawarehouse.properties";
	private Driver driver;
	private String connectionURL;
	private String username;
	private String password;
	private InputStream inputStream;

	public DataWarehouseModule() {
		super();
		inputStream = 
			ClassLoader.getSystemClassLoader().
				getResourceAsStream(PROPERTIES_RESOURCE);
	}
	
	public DataWarehouseModule(String propertiesFileName) {
		super();
		try {
			inputStream = new FileInputStream(propertiesFileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void configure() {
	}
	
	@Provides @Singleton
	DataWarehouse provideDataWarehouse() {
		DataWarehouse dataWarehouse = null;
		try {
			setFields();
			DriverManager.registerDriver(driver);
			Connection connection = 
				DriverManager.getConnection(connectionURL, username, password);
			dataWarehouse = new DataWarehouse(connection);
		} catch (Exception e) {
			addError(e);
		}
		return dataWarehouse;
	}
	
	private void setFields() throws IOException {
		Properties properties = new Properties();
		properties.load(inputStream);
		driver = new SQLServerDriver();
		connectionURL = properties.getProperty("connectionURL");
		username = properties.getProperty("username");
		password = properties.getProperty("password");
	}
}