/**
 * 
 */
package edu.nyu.libraries.util;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;

import edu.nyu.libraries.util.DataWarehousePropertiesModule.ConnectionURL;
import edu.nyu.libraries.util.DataWarehousePropertiesModule.Password;
import edu.nyu.libraries.util.DataWarehousePropertiesModule.Username;

/**
 * @author Scot Dalton
 *
 */
public class DataWarehousePropertiesModuleTest {
	
	private final String PROPERTIES_FILE = 
		"./config/datawarehouse_test.properties";
	private Injector injector;
	
	@Before
	public void setup() {
		injector = 
			Guice.createInjector(new DataWarehousePropertiesModule(PROPERTIES_FILE));
	}
	
	/**
	 * This is a pretty hacky test since we're reading from the config
	 * file for the test. 
	 */
	@Test
	public void testGetConnectionURL() {
		String connectionURL = 
			injector.getInstance(Key.get(String.class, ConnectionURL.class));
		assertEquals("jdbc:sqlserver://localhost\\instance:1433;databaseName=database;selectMethod=cursor", connectionURL);
	}
	
	/**
	 * This is a pretty hacky test since we're reading from the config
	 * file for the test. 
	 */
	@Test
	public void testGetUsername() {
		String username = 
			injector.getInstance(Key.get(String.class, Username.class));
		assertEquals("user", username);
	}
	
	/**
	 * This is a pretty hacky test since we're reading from the config
	 * file for the test. 
	 */
	@Test
	public void testGetPassword() {
		String password = 
			injector.getInstance(Key.get(String.class, Password.class));
		assertEquals("******", password);
	}
}
