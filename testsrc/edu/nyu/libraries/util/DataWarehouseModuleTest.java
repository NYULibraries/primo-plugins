/**
 * 
 */
package edu.nyu.libraries.util;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
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
	public void setup() throws FileNotFoundException, IOException {
		injector = Guice.createInjector(
			new DataWarehouseModule("./META-INF/datawarehouse.properties"));
	}
	
	@Test
	public void testGetInstance() throws SQLException  {
		DataWarehouse dataWarehouse = 
			injector.getInstance(DataWarehouse.class);
		assertTrue(dataWarehouse instanceof DataWarehouse);
	}
	
	@Test
	public void testSingleton() {
		DataWarehouse dataWarehouse1 = 
			injector.getInstance(DataWarehouse.class);
		DataWarehouse dataWarehouse2 = 
			injector.getInstance(DataWarehouse.class);
		assertSame(dataWarehouse1, dataWarehouse2);
	}

}
