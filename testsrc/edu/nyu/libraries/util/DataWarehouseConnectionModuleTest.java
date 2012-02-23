/**
 * 
 */
package edu.nyu.libraries.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;
import java.sql.Driver;

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
	
	private Injector injector;
	
	@Before
	public void setup() {
		injector = Guice.createInjector(new DataWarehouseConnectionModule());
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
		assertEquals("jdbc:sqlserver://"+
			"cadmus.bobst.nyu.edu\\CADMUS_SQLSERVER:1433"+
			";databaseName=*****"+
			";selectMethod=cursor", connectionURL);
	}
	
	@Test
	public void testGetUsername() {
		String username = injector.getInstance(Key.get(String.class, Username.class));
		assertTrue(username instanceof String);
		assertEquals("*****", username);
	}
	
	@Test
	public void testGetPassword() {
		String password = injector.getInstance(Key.get(String.class, Password.class));
		assertTrue(password instanceof String);
		assertEquals("*****", password);
	}
}
