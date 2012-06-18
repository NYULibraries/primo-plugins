/**
 */
package edu.nyu.library.datawarehouse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

/**
 * @author Scot Dalton
 * DataWarehouseModule leverages Guice to provide a singleton instance of 
 * a DataWarehouse.  DataWarehouseModule is instantiated with a Properties 
 * object that includes the datawarehouse connection information.
 *
 */
public class DataWarehouseModule extends AbstractModule {
	private Properties properties;

	/**
	 * Public constructor takes a properties file with the datawarehouse
	 * connection information.
	 * @param properties
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public DataWarehouseModule(File propertiesFile) 
			throws FileNotFoundException, IOException {
		super();
		properties = new Properties();
		properties.load(new FileReader(propertiesFile));
	}
	
	@Override
	protected void configure() {
	}
	
	/**
	 * Returns a singleton instance of a DataWarehouse based on the
	 * connection information passed in when the DataWarehouseModule
	 * was instantiated. 
	 * @return
	 */
	@Provides @Singleton
	DataWarehouse provideDataWarehouse() {
		DataWarehouse dataWarehouse = null;
		try {
			Driver driver = 
				(Driver) Class.forName(
					properties.getProperty("driverClass")).newInstance();
			String connectionURL = properties.getProperty("connectionURL");
			String username = properties.getProperty("username");
			String password = properties.getProperty("password");
			DriverManager.registerDriver(driver);
			Connection connection = 
				DriverManager.getConnection(connectionURL, username, password);
			dataWarehouse = new DataWarehouse(connection);
		} catch (Exception e) {
			addError(e);
		}
		return dataWarehouse;
	}
}