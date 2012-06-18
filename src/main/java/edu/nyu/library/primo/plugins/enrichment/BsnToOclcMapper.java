/**
 * 
 */
package edu.nyu.library.primo.plugins.enrichment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.inject.Guice;

import edu.nyu.library.datawarehouse.DataWarehouse;
import edu.nyu.library.datawarehouse.DataWarehouseModule;

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

	/**
	 * Public constructor.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws Exception
	 */
	public BsnToOclcMapper() throws FileNotFoundException, IOException {
		this(Guice.createInjector(new DataWarehouseModule(
			"./src/main/resources/META-INF/datawarehouse.properties")).
				getInstance(DataWarehouse.class));
	}

	/**
	 * Protected constructor. Used for testing. 
	 * @throws Exception
	 */
	protected BsnToOclcMapper(DataWarehouse dataWarehouse) {
		super(MAPPING_TABLE_NAME, MAP_TO_COLUMN_NAME, BSN_COLUMN_NAME, 
			dataWarehouse, ENRICHMENT_SECTION_TAGS);
	}
}