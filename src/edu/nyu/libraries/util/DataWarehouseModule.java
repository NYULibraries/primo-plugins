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
import com.google.inject.Injector;
import com.google.inject.Key;

import edu.nyu.libraries.util.DataWarehouseConnectionModule.ConnectionURL;
import edu.nyu.libraries.util.DataWarehouseConnectionModule.Password;
import edu.nyu.libraries.util.DataWarehouseConnectionModule.Username;


/**
 * @author Scot Dalton
 *
 */
public class DataWarehouseModule extends AbstractModule {

	@Override
	protected void configure() {
		Injector injector = 
			Guice.createInjector(new DataWarehouseConnectionModule());
		Driver driver = injector.getInstance(Driver.class);
		String connectionURL = 
			injector.getInstance(Key.get(String.class, ConnectionURL.class));
		String username = 
			injector.getInstance(Key.get(String.class, Username.class));
		String password = 
			injector.getInstance(Key.get(String.class, Password.class));
		try {
			DriverManager.registerDriver(driver);
			Connection connection = 
				getConnection(connectionURL, username, password);
			bind(Connection.class).toInstance(connection);
		} catch (SQLException e) {
			addError(e);
		}
	}

	private Connection getConnection(String connectionUrl, 
			String userName, String password) throws SQLException {
		return DriverManager.getConnection(connectionUrl, userName, password);
    }
}