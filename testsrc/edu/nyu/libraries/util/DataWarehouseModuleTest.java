/**
 * 
 */
package edu.nyu.libraries.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;


/**
 * @author Scot Dalton
 *
 */
public class DataWarehouseModuleTest {
	
	private Injector injector;
	
	@Before
	public void setup() {
		injector = Guice.createInjector(new DataWarehouseModule());
	}
	
	@Test
	public void testConnection() throws SQLException  {
		injector.getInstance(Connection.class);
	}

}
