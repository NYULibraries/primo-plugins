/**
 * 
 */
package edu.nyu.libraries.primo.plugins.enrichment;

import java.util.List;

import edu.nyu.libraries.util.DataWarehouse;


/**
 * @author Scot Dalton
 *
 */
public class AlephBsnMapper extends SingleTableMapper {
	private final static SectionTag mapperSectionTag = 
		new SectionTag("control", "sourcerecordid");

	/**
	 * @throws Exception
	 */
	public AlephBsnMapper(String tableName, String mappingColumnName, 
			String bsnColumnName, DataWarehouse dataWarehouse, 
			List<SectionTag> enrichmentSectionTags) {
		super(tableName, mappingColumnName, bsnColumnName, 
			mapperSectionTag, dataWarehouse, enrichmentSectionTags);
	}
}