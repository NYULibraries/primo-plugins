/**
 * 
 */
package edu.nyu.libraries.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;


/**
 * @author Scot Dalton
 *
 */
public class DataWarehouseTest {
	
	@Test
	public void testIntantiate() throws SQLException {
		Injector injector = 
			Guice.createInjector(new DataWarehouseModule());
		new DataWarehouse(injector.getInstance(Connection.class));
	}
}