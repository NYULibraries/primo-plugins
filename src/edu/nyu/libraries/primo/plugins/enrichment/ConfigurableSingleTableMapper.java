/**
 * 
 */
package edu.nyu.libraries.primo.plugins.enrichment;


import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;

import com.exlibris.primo.api.plugins.enrichment.IEnrichmentDocUtils;
import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.nyu.libraries.util.DataWarehouse;
import edu.nyu.libraries.util.DataWarehouseModule;

/**
 * @author Scot Dalton
 *
 */
public class ConfigurableSingleTableMapper extends
		DataWarehouseEnrichmentPlugin {

	private static Injector injector = 
		Guice.createInjector(new DataWarehouseModule());
	private SingleTableMapper singleTableMapper;
	
	public ConfigurableSingleTableMapper() {
		this(injector.getInstance(DataWarehouse.class));
	}

	/**
	 * @param dataWarehouse
	 * @param enrichmentSectionTags
	 */
	public ConfigurableSingleTableMapper(DataWarehouse dataWarehouse) {
		super(injector.getInstance(DataWarehouse.class), null);
	}

	/* (non-Javadoc)
	 * @see com.exlibris.primo.api.plugins.enrichment.EnrichmentPlugin#enrich(org.w3c.dom.Document, com.exlibris.primo.api.plugins.enrichment.IEnrichmentDocUtils)
	 */
	@Override
	public Document enrich(Document doc, IEnrichmentDocUtils docUtils)
			throws Exception {
		Map<String, Object> paramsMap = getParamsMap();
		String mappingTableName = (String) paramsMap.get("mappingTable");
		String mapToColumn = (String) paramsMap.get("mapToColumn");
		String mapFromColumn = (String) paramsMap.get("mapFromColumn");
		String mapFromSection = (String) paramsMap.get("mapFromSection");
		String mapFromTag = (String) paramsMap.get("mapFromTag");
		SectionTag mapFromSectionTag = 
			new SectionTag(mapFromSection, mapFromTag);
		int numberOfEnrichmentTags = 
			Integer.valueOf((String) paramsMap.get("numberOfEnrichmentTags"));
		List<SectionTag> enrichmentSectionTag = Lists.newArrayList();
		for(int i=1; i<=numberOfEnrichmentTags; i++){
			String section = (String) paramsMap.get("enrichmentSection"+i);
			String tag = (String) paramsMap.get("enrichmentTag"+i);
			enrichmentSectionTag.add(new SectionTag(section, tag));
		}
		singleTableMapper = 
			new SingleTableMapper(mappingTableName, mapToColumn, mapFromColumn, mapFromSectionTag, getDataWarehouse(), enrichmentSectionTag);
		return singleTableMapper.enrich(doc, docUtils);
	}
}