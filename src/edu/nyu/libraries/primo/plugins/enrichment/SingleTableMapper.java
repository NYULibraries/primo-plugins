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
 * SingleTableMapper represents a column to column enrichment based on
 * a give element in the Primo document and a mapped column in the 
 * DataWarehouse.
 */
public class SingleTableMapper extends DataWarehouseEnrichmentPlugin {
	private SectionTag mapperSectionTag;
	private final String sqlQuery;

	/**
	 * @param dataWarehouse
	 * @param enrichmentSectionTags
	 */
	public SingleTableMapper(String tableName, String mappingColumnName, 
			String bsnColumnName, SectionTag mapperSectionTag, 
			DataWarehouse dataWarehouse, List<SectionTag> enrichmentSectionTags) {
		this(sqlQuery(tableName, mappingColumnName, bsnColumnName), mapperSectionTag,
				dataWarehouse, enrichmentSectionTags);
	}

	/**
	 * 
	 * @param sqlQuery
	 * @param dataWarehouse
	 * @param enrichmentSectionTags
	 */
	public SingleTableMapper(String sqlQuery, SectionTag mapperSectionTag, 
			DataWarehouse dataWarehouse, List<SectionTag> enrichmentSectionTags) {
		super(dataWarehouse, enrichmentSectionTags);
		this.sqlQuery = sqlQuery;
		this.mapperSectionTag = mapperSectionTag;
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
		String[] mappers = docUtil.
			getValuesBySectionAndTag(doc, mapperSectionTag.section, mapperSectionTag.tag);
		List<String> mappings = Lists.newArrayList();
		for (String mapper: mappers) {
			ResultSet resultSet = 
				getResultSet(mapper);
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
