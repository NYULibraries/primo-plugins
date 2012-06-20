/**
 * 
 */
package edu.nyu.library.datawarehouse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class DataWarehouseTest {
	private final String SQL_SELECT_STUB = 
		"SELECT OCLC_MASTER FROM HARVARD_PROJECT_OCLC_KEYS WHERE ALEPH_BSN = ";
	private final String ALEPH_BSN = "000000001";
	private final String SQL_SELECT = SQL_SELECT_STUB + ALEPH_BSN;
	private final String SQL_PREPARED_STATEMENT = SQL_SELECT_STUB + "?";
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
	public void testIntantiate() 
			throws SQLException, FileNotFoundException, IOException {
		DataWarehouse dataWarehouse = 
			injector.getInstance(DataWarehouse.class);
		assertTrue(dataWarehouse instanceof DataWarehouse);
	}
	
	@Test
	public void testExecuteQuery_select() 
			throws FileNotFoundException, IOException, SQLException {
		DataWarehouse dataWarehouse = 
			injector.getInstance(DataWarehouse.class);
		ResultSet results = dataWarehouse.executeQuery(SQL_SELECT);
		assertTrue(results.next());
		assertEquals("154703639", results.getString(1));
		assertFalse(results.next());
	}
	
	@Test
	public void testGetActivatedPreparedStatement_select()
			throws FileNotFoundException, IOException, SQLException {
		DataWarehouse dataWarehouse = 
			injector.getInstance(DataWarehouse.class);
		dataWarehouse.activatePreparedStatement(SQL_PREPARED_STATEMENT);
		PreparedStatement activatedPreparedStatement = 
			dataWarehouse.getActivatedPreparedStatement();
		assertFalse(activatedPreparedStatement.isClosed());
		assertEquals("OCLC_MASTER", 
			activatedPreparedStatement.getMetaData().getColumnName(1));
		assertEquals(java.sql.Types.VARCHAR, 
			activatedPreparedStatement.getMetaData().getColumnType(1));
	}
	
	@Test
	public void testGetResultsFromActivatedPreparedStatement_select() 
			throws FileNotFoundException, IOException, SQLException {
		DataWarehouse dataWarehouse = 
			injector.getInstance(DataWarehouse.class);
		dataWarehouse.activatePreparedStatement(SQL_PREPARED_STATEMENT);
		dataWarehouse.setStringForActivatedPreparedStatement(1, ALEPH_BSN);
		ResultSet results = 
			dataWarehouse.executeQueryForActivatedPreparedStatement();
		assertTrue(results.next());
		assertEquals("154703639", results.getString(1));
		assertFalse(results.next());
	}
}