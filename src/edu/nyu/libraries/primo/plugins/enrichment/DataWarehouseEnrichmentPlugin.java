/**
 * 
 */
package edu.nyu.libraries.primo.plugins.enrichment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.google.inject.Inject;

import edu.nyu.libraries.util.DataWarehouse;


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
	@Inject
	public DataWarehouseEnrichmentPlugin(DataWarehouse dataWarehouse) {
		this(dataWarehouse, null);
	}
	
	/**
	 * Public constructor.
	 * @throws Exception
	 */
	@Inject
	public DataWarehouseEnrichmentPlugin(DataWarehouse dataWarehouse,
			List<SectionTag> enrichmentSectionTags) {
		super(enrichmentSectionTags);
		this.dataWarehouse = dataWarehouse;
	}
	
	/**
	 * Subclasses can override this method for more complex querying needs.
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	protected ResultSet getResultSet(String sql) throws SQLException {
		return dataWarehouse.executeQuery(sql);
	}
	
	protected DataWarehouse getDataWarehouse() {
		return dataWarehouse;
	}
}