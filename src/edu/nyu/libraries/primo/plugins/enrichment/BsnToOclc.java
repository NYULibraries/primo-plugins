/**
 * 
 */
package edu.nyu.libraries.primo.plugins.enrichment;

/**
 * @author Scot Dalton
 * 
 */
public class BsnToOclc extends AlephBsnMapper {
	private final static String PROPERTIES_FILE_NAME = 
		"META-INF/bsn_to_oclc.properties";
	
	/**
	 * Public constructor.
	 * 
	 * @throws Exception
	 */
	public BsnToOclc() throws Exception {
		super(
			sqlQuery(PROPERTIES_FILE_NAME), 
			enrichmentSectionTags(
				new SectionTag("addata", "oclcid"), 
				new SectionTag("search", "oclc")));
	}
}