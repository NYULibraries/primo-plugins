/**
 * 
 */
package edu.nyu.libraries.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;

import edu.nyu.libraries.util.DataWarehousePropertiesModule.ConnectionURL;
import edu.nyu.libraries.util.DataWarehousePropertiesModule.Password;
import edu.nyu.libraries.util.DataWarehousePropertiesModule.Username;

/**
 * @author Scot Dalton
 *
 */
public class DataWarehouseModule extends AbstractModule {
	private @ConnectionURL String connectionURL;
	private @Username String username;
	private @Password String password;
	
	public DataWarehouseModule() {
		super();
		Injector propertiesInjector = 
			Guice.createInjector(new DataWarehousePropertiesModule());
		this.connectionURL = 
			propertiesInjector.getInstance(Key.get(String.class, ConnectionURL.class));
		this.username = 
			propertiesInjector.getInstance(Key.get(String.class, Username.class));
		this.password = 
			propertiesInjector.getInstance(Key.get(String.class, Password.class));
	}

	@Inject
	public DataWarehouseModule(@ConnectionURL String connectionURL, 
			@Username String username, @Password String password) {
		super();
		this.connectionURL = connectionURL;
		this.username = username;
		this.password = password;
	}
	
	@Override
	protected void configure() {
		Injector injector = 
			Guice.createInjector(new DataWarehousePropertiesModule());
		Driver driver = injector.getInstance(Driver.class);
		try {
			DriverManager.registerDriver(driver);
			Connection connection = 
				DriverManager.getConnection(connectionURL, username, password);
			bind(Connection.class).toInstance(connection);
		} catch (SQLException e) {
			addError(e);
		}
	}
}