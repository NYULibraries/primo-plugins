/**
 * 
 */
package edu.nyu.libraries.primo.plugins.enrichment;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;

import edu.nyu.libraries.util.DataWarehouse;
import edu.nyu.libraries.util.DataWarehouseModule;

/**
 * @author Scot Dalton
 * 
 */
public class BsnToOclcMapper extends AlephBsnMapper {
	private final static String MAPPING_TABLE_NAME = 
		"HARVARD_PROJECT_OCLC_KEYS";
	private final static String MAP_TO_COLUMN_NAME = 
		"OCLC_MASTER";
	private final static String BSN_COLUMN_NAME = 
		"ALEPH_BSN";
	private final static List<SectionTag> ENRICHMENT_SECTION_TAGS =
		Lists.newArrayList(new SectionTag("addata", "oclcid"), 
			new SectionTag("search", "oclc"));
	private static Injector injector = 
		Guice.createInjector(new DataWarehouseModule());

	/**
	 * Public constructor.
	 * 
	 * @throws Exception
	 */
	public BsnToOclcMapper() {
		this(injector.getInstance(DataWarehouse.class));
	}

	/**
	 * Protected constructor. Used for testing. 
	 * 
	 * @throws Exception
	 */
	protected BsnToOclcMapper(DataWarehouse dataWarehouse) {
		super(MAPPING_TABLE_NAME, MAP_TO_COLUMN_NAME, BSN_COLUMN_NAME, 
			dataWarehouse, ENRICHMENT_SECTION_TAGS);
	}
}