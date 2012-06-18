/**
 * 
 */
package edu.nyu.library.primo.plugins.enrichment;

import java.util.List;

import edu.nyu.library.datawarehouse.DataWarehouse;


/**
 * @author Scot Dalton
 *
 */
public class AlephBsnMapper extends SingleTableMapper {
	private final static SectionTag mapFromSectionTag = 
		new SectionTag("control", "sourcerecordid");

	/**
	 * 
	 * @param mappingTableName
	 * @param mapToColumnName
	 * @param bsnColumnName
	 * @param dataWarehouse
	 * @param enrichmentSectionTags
	 */
	public AlephBsnMapper(String mappingTableName, String mapToColumnName, 
			String bsnColumnName, DataWarehouse dataWarehouse, 
			List<SectionTag> enrichmentSectionTags) {
		super(mappingTableName, mapToColumnName, bsnColumnName, 
			mapFromSectionTag, dataWarehouse, enrichmentSectionTags);
	}
}