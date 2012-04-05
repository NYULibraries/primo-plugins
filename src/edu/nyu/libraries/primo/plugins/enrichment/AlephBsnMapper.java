/**
 * 
 */
package edu.nyu.libraries.primo.plugins.enrichment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.w3c.dom.Document;

import com.exlibris.primo.api.plugins.enrichment.IEnrichmentDocUtils;
import com.google.common.collect.Lists;

import edu.nyu.libraries.util.DataWarehouse;


/**
 * @author Scot Dalton
 *
 */
public abstract class AlephBsnMapper extends DataWarehouseEnrichmentPlugin {
	private final String sqlQuery;

	/**
	 * @throws Exception
	 */
	public AlephBsnMapper(String tableName, String mappingColumnName, 
			String bsnColumnName, DataWarehouse dataWarehouse, 
			List<SectionTag> enrichmentSectionTags) {
		this(sqlQuery(tableName, mappingColumnName, bsnColumnName), 
			dataWarehouse, enrichmentSectionTags);
	}
	
	/**
	 * @throws Exception
	 */
	public AlephBsnMapper(String sqlQuery, DataWarehouse dataWarehouse, 
			List<SectionTag> enrichmentSectionTags) {
		super(dataWarehouse, enrichmentSectionTags);
		this.sqlQuery = sqlQuery;
	}
	
	/**
	 * Adds mappings provided by the NYU Libraries' Aleph DataWarehouse 
	 * from Aleph BSNs to result of the first column in the plugins' 
	 * configured SQL query.  Mappings are added to the Document based on
	 * the plugins' List of SectionTags.
	 */
	@Override
	public Document enrich(Document doc, IEnrichmentDocUtils docUtil) 
			throws Exception {
		String[] bsns = 
			docUtil.getValuesBySectionAndTag(doc, "control", "sourcerecordid");
		List<String> mappings = Lists.newArrayList();
		for (String bsn: bsns) {
			ResultSet resultSet = 
				getResultSet(bsn);
			while(resultSet.next()) {
				mappings.add(resultSet.getString(1));
			}
		}
		return addEnrichmentTags(doc, docUtil, mappings);
	}
	
	/**
	 * Get Result set based on the initial SQL query and the given BSN.
	 */
	@Override
	public ResultSet getResultSet(String bsn) throws SQLException {
		return super.getResultSet(sqlQuery + bsn);
	}

	private final static String sqlQuery(String tableName, 
			String mappingColumnName,  String bsnColumnName) {
		if (	tableName == null) 
			throw new NullPointerException(
				"No property 'table' defined.");
		if (	mappingColumnName == null) 
			throw new NullPointerException(
				"No property 'column' defined.");
		if (	bsnColumnName == null) 
			throw new NullPointerException(
				"No property 'bsnColumn' defined.");
		return "SELECT " + mappingColumnName + " FROM " + tableName + 
			" WHERE " + bsnColumnName + " = ";
	}
}