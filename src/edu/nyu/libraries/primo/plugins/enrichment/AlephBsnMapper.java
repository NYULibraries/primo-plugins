/**
 * 
 */
package edu.nyu.libraries.primo.plugins.enrichment;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.w3c.dom.Document;

import com.exlibris.primo.api.plugins.enrichment.IEnrichmentDocUtils;
import com.google.common.collect.Lists;


/**
 * @author Scot Dalton
 *
 */
public abstract class AlephBsnMapper extends DataWarehouseEnrichmentPlugin {
	private final String sqlQuery;

	protected final static String sqlQuery(String propertiesFileName) 
			throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileReader(propertiesFileName));
		String table = properties.getProperty("table");
		if (	table == null) 
			throw new NullPointerException(
				"No property 'table' defined in properties file.");
		String column = properties.getProperty("column");
		if (	column == null) 
			throw new NullPointerException(
				"No property 'column' defined in properties file.");
		String bsnColumn = properties.getProperty("bsnColumn");
		if (	bsnColumn == null) 
			throw new NullPointerException(
				"No property 'bsnColumn' defined in properties file.");
		return "SELECT " + column + " FROM " + table + " WHERE " + 
			bsnColumn + " = ";
}

protected final static List<SectionTag> enrichmentSectionTags(SectionTag... sectionTags) {
return Lists.newArrayList(sectionTags);
}

	/**
	 * @throws Exception
	 */
	public AlephBsnMapper(String sqlQuery, 
			List<SectionTag> enrichmentSectionTags) throws Exception {
		super(enrichmentSectionTags);
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
	
	@Override
	public ResultSet getResultSet(String bsn) throws SQLException {
		return super.getResultSet(sqlQuery + bsn);
	}
}