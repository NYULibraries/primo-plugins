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
public class BsnToOclc extends AlephBsnMapper {
	private final static String TABLE_NAME = "HARVARD_PROJECT_OCLC_KEYS";
	private final static String OCLC_COLUMN_NAME = "OCLC_MASTER";
	private final static String BSN_COLUMN_NAME = "ALEPH_BSN";
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
	public BsnToOclc() {
		this(injector.getInstance(DataWarehouse.class));
	}

	/**
	 * Protected constructor. Used for testing. 
	 * 
	 * @throws Exception
	 */
	protected BsnToOclc(DataWarehouse dataWarehouse) {
		super(TABLE_NAME, OCLC_COLUMN_NAME, BSN_COLUMN_NAME, 
			dataWarehouse, ENRICHMENT_SECTION_TAGS);
	}
}