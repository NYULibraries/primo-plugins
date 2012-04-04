/**
 * 
 */
package edu.nyu.libraries.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
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
	private final static String PROPERTIES_FILE_NAME = 
		"./META-INF/datawarehouse.properties";
	private Properties properties;
	private Driver driver;
	private String connectionURL;
	private String username;
	private String password;

	public DataWarehouseModule() throws FileNotFoundException, IOException {
		this(PROPERTIES_FILE_NAME);
	}

	public DataWarehouseModule(String propertiesFileName) 
		throws FileNotFoundException, IOException {
			super();
			properties = new Properties();
			properties.load(new FileReader(propertiesFileName));
			driver = new SQLServerDriver();
			connectionURL = properties.getProperty("connectionURL");
			username = properties.getProperty("username");
			password = properties.getProperty("password");
	}
	
	@Override
	protected void configure() {
	}
	
	@Provides @Singleton
	DataWarehouse provideDataWarehouse() {
		DataWarehouse dataWarehouse = null;
		try {
			DriverManager.registerDriver(driver);
			Connection connection = 
				DriverManager.getConnection(connectionURL, username, password);
			dataWarehouse = new DataWarehouse(connection);
		} catch (SQLException e) {
			addError(e);
		}
		return dataWarehouse;
	}
}