/**
 * 
 */
package edu.nyu.libraries.util;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;


/**
 * @author Scot Dalton
 *
 */
public class DataWarehouseTest {
	
	@Test
	public void testIntantiate() {
		Injector injector = 
			Guice.createInjector(new DataWarehouseModule());
		DataWarehouse dataWarehouse = 
			new DataWarehouse(injector.getInstance(Connection.class));
		assertTrue(dataWarehouse instanceof DataWarehouse);
	}
}