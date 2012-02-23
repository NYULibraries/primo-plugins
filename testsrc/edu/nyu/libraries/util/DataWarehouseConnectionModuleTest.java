/**
 * 
 */
package edu.nyu.libraries.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Driver;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import edu.nyu.libraries.util.DataWarehouseConnectionModule.ConnectionURL;
import edu.nyu.libraries.util.DataWarehouseConnectionModule.Password;
import edu.nyu.libraries.util.DataWarehouseConnectionModule.Username;



/**
 * @author Scot Dalton
 *
 */
public class DataWarehouseConnectionModuleTest {
	
	private final String PROPERTIES_FILE = 
		"./config/datawarehouse.properties";
	private Injector injector;
	private Properties properties;
	
	@Before
	public void setup() throws FileNotFoundException, IOException {
		injector = 
			Guice.createInjector(new DataWarehouseConnectionModule());
		properties = new Properties();
		properties.load(new FileReader(PROPERTIES_FILE));
	}
	
	@Test
	public void testGetDriver() {
		Driver driver = injector.getInstance(Driver.class);
		assertTrue(driver instanceof Driver);
		assertTrue(driver instanceof SQLServerDriver);
	}
	
	@Test
	public void testGetConnectionURL() {
		String connectionURL = injector.getInstance(
			Key.get(String.class, ConnectionURL.class));
		assertTrue(connectionURL instanceof String);
		assertEquals(properties.getProperty("connectionURL"), connectionURL);
	}
	
	@Test
	public void testGetUsername() {
		String username = injector.getInstance(Key.get(String.class, Username.class));
		assertTrue(username instanceof String);
		assertEquals(properties.getProperty("username"), username);
	}
	
	@Test
	public void testGetPassword() {
		String password = injector.getInstance(Key.get(String.class, Password.class));
		assertTrue(password instanceof String);
		assertEquals(properties.getProperty("password"), password);
	}
}
