/**
 * 
 */
package edu.nyu.libraries.primo.plugins.enrichment;

/**
 * @author Scot Dalton
 * 
 */
public class BsnToOclc extends AlephBsnMapper {
	private final static String TABLE_NAME = "HARVARD_PROJECT_OCLC_KEYS";
	private final static String OCLC_COLUMN_NAME = "OCLC_MASTER";
	private final static String BSN_COLUMN_NAME = "ALEPH_BSN"; 

	/**
	 * Public constructor.
	 * 
	 * @throws Exception
	 */
	public BsnToOclc() throws Exception {
		super(TABLE_NAME, OCLC_COLUMN_NAME, BSN_COLUMN_NAME, 
			enrichmentSectionTags(
				new SectionTag("addata", "oclcid"), 
				new SectionTag("search", "oclc")));
	}
}