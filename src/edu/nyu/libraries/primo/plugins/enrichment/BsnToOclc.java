/**
 * 
 */
package edu.nyu.libraries.primo.plugins.enrichment;

/**
 * @author Scot Dalton
 * 
 */
public class BsnToOclc extends AlephBsnMapper {
	private final static String table = "HARVARD_PROJECT_OCLC_KEYS";
	private final static String column = "OCLC_MASTER";
	private final static String bsnColumn = "ALEPH_BSN"; 

	/**
	 * Public constructor.
	 * 
	 * @throws Exception
	 */
	public BsnToOclc() throws Exception {
		super(
			sqlQuery(table, column, bsnColumn), 
			enrichmentSectionTags(
				new SectionTag("addata", "oclcid"), 
				new SectionTag("search", "oclc")));
	}
}