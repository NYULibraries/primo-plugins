/**
 * 
 */
package edu.nyu.library.datawarehouse;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.nyu.library.datawarehouse.DataWarehouse;
import edu.nyu.library.datawarehouse.DataWarehouseModule;

/**
 * @author Scot Dalton
 *
 */
public class DataWarehouseModuleTest {
	
	private final static String propertiesFilename = 
		"./src/test/resources/META-INF/datawarehouse.properties";
	private Injector injector;
	
	@Before
	public void setup() throws FileNotFoundException, IOException {
		File propertiesFile = new File(propertiesFilename);
		injector = 
			Guice.createInjector(new DataWarehouseModule(propertiesFile));
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