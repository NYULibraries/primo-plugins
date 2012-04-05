/**
 * 
 */
package edu.nyu.libraries.primo.plugins.enrichment;


import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;

import com.exlibris.primo.api.common.IMappingTablesFetcher;
import com.exlibris.primo.api.common.IPrimoLogger;
import com.exlibris.primo.api.plugins.enrichment.IEnrichmentDocUtils;
import com.google.common.collect.Lists;

import edu.nyu.libraries.util.DataWarehouse;

/**
 * @author Scot Dalton
 *
 */
public class ConfigurableSingleTableMapper extends NyuEnrichmentPlugin {
	private SingleTableMapper singleTableMapper;
	private String mappingTableName;
	private String mapToColumn;
	private String mapFromColumn;
	private String mapFromSection;
	private String mapFromTag;
	private SectionTag mapFromSectionTag;
	private int numberOfEnrichmentTags;
	private List<SectionTag> enrichmentSectionTag;
	
	@Override
	public void init(IPrimoLogger primoLogger, 
			IMappingTablesFetcher tablesFetcher, 
			Map<String, Object> paramsMap) throws Exception {
		super.init(primoLogger, tablesFetcher, paramsMap);
		setMappingConfig(paramsMap);
		singleTableMapper = new SingleTableMapper(mappingTableName, 
			mapToColumn, mapFromColumn, mapFromSectionTag, enrichmentSectionTag);
	}
	
	/**
	 * Protected init. Used for testing.
	 * @param primoLogger
	 * @param tablesFetcher
	 * @param paramsMap
	 * @param dataWarehouse
	 * @throws Exception
	 */
	protected void init(IPrimoLogger primoLogger, 
			IMappingTablesFetcher tablesFetcher, 
			Map<String, Object> paramsMap, DataWarehouse dataWarehouse) throws Exception {
		super.init(primoLogger, tablesFetcher, paramsMap);
		setMappingConfig(paramsMap);
		singleTableMapper = new SingleTableMapper(mappingTableName, 
			mapToColumn, mapFromColumn, mapFromSectionTag, dataWarehouse, enrichmentSectionTag);
	}
	
	@Override
	public Document enrich(Document doc, IEnrichmentDocUtils docUtils)
			throws Exception {
		return singleTableMapper.enrich(doc, docUtils);
	}
	
	private void setMappingConfig(Map<String, Object> paramsMap) {
		mappingTableName = (String) paramsMap.get("mappingTable");
		mapToColumn = (String) paramsMap.get("mapToColumn");
		mapFromColumn = (String) paramsMap.get("mapFromColumn");
		mapFromSection = (String) paramsMap.get("mapFromSection");
		mapFromTag = (String) paramsMap.get("mapFromTag");
		mapFromSectionTag = 
			new SectionTag(mapFromSection, mapFromTag);
		numberOfEnrichmentTags = 
			Integer.valueOf((String) paramsMap.get("numberOfEnrichmentTags"));
		enrichmentSectionTag = Lists.newArrayList();
		for(int i=1; i<=numberOfEnrichmentTags; i++){
			String section = (String) paramsMap.get("enrichmentSection"+i);
			String tag = (String) paramsMap.get("enrichmentTag"+i);
			enrichmentSectionTag.add(new SectionTag(section, tag));
		}
	}
}