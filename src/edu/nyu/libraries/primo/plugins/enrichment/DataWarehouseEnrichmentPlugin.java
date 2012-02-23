/**
 * 
 */
package edu.nyu.libraries.primo.plugins.enrichment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.nyu.libraries.util.DataWarehouse;
import edu.nyu.libraries.util.DataWarehouseModule;


/**
 * @author Scot Dalton
 * 
 * Abstract class for enriching Primo via the NYU Libraries Data Warehouse.
 * Subclasses should implement the abstract method getSqlStatement() with 
 * their specific query for simple queries or throw an 
 * UnsupportedOperationException and use the method 
 * getResultSet(String sql) for more complex needs
 */
public abstract class DataWarehouseEnrichmentPlugin extends NyuEnrichmentPlugin {
	private DataWarehouse dataWarehouse;
	
	/**
	 * Public constructor.
	 * @throws Exception
	 */
	public DataWarehouseEnrichmentPlugin() throws Exception {
		Injector injector = 
			Guice.createInjector(new DataWarehouseModule());
		Connection connection = 
			injector.getInstance(Connection.class);
		dataWarehouse = 
			new DataWarehouse(connection);
	}
	
	/**
	 * Abstract method for more straightforward enrichment plugins.
	 * Implementers should return a valid SQL statement.
	 * 
	 * @return SQL string
	 */
	protected abstract String getSqlStatement();
	
	/**
	 * 
	 * @return ResultSet from the NYU Libraries Data Warehouse
	 * @throws SQLException
	 */
	protected ResultSet getResultSet() throws SQLException {
		return getResultSet(getSqlStatement());
	}
	
	/**
	 * Subclasses can use this method for more complex querying needs.
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	protected ResultSet getResultSet(String sql) throws SQLException {
		dataWarehouse.prepareStatement(sql);
		return dataWarehouse.executePreparedStatement();
	}
}