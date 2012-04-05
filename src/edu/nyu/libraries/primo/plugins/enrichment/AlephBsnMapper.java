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
	private final static SectionTag mapFromSectionTag = 
		new SectionTag("control", "sourcerecordid");

	/**
	 * @throws Exception
	 */
	public AlephBsnMapper(String mappingTableName, String mapToColumnName, 
			String bsnColumnName, DataWarehouse dataWarehouse, 
			List<SectionTag> enrichmentSectionTags) {
		super(mappingTableName, mapToColumnName, bsnColumnName, 
			mapFromSectionTag, dataWarehouse, enrichmentSectionTags);
	}
}