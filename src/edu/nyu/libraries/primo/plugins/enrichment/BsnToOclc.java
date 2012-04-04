/**
 * 
 */
package edu.nyu.libraries.primo.plugins.enrichment;

import java.sql.ResultSet;
import java.util.List;

import org.w3c.dom.Document;

import com.exlibris.primo.api.plugins.enrichment.IEnrichmentDocUtils;
import com.google.common.collect.Lists;

/**
 * @author Scot Dalton
 * 
 */
public class BsnToOclc extends DataWarehouseEnrichmentPlugin {
	private final String SQL_SELECT_STUB = 
		"SELECT OCLC_MASTER FROM HARVARD_PROJECT_OCLC_KEYS WHERE ALEPH_BSN = ";

	/**
	 * Public constructor.
	 * 
	 * @throws Exception
	 */
	public BsnToOclc() throws Exception {
		super();
	}

	/**
	 * Adds OCLC numbers in the search/oclc and addata/oclcid elements of 
	 * the input Document based on the mappings provided by the NYU 
	 * Libraries' Aleph DataWarehouse.
	 */
	@Override
	public Document enrich(Document doc, IEnrichmentDocUtils docUtil)
			throws Exception {
		String[] bsns = docUtil.getValuesBySectionAndTag(doc, "control",
				"sourcerecordid");
		List<String> oclcs = Lists.newArrayList();
		for (String bsn: bsns) {
			ResultSet resultSet = 
				getResultSet(SQL_SELECT_STUB + bsn);
			while(resultSet.next()) {
				oclcs.add(resultSet.getString("OCLC_MASTER"));
			}
		}
		docUtil.addTags(doc, "search", "oclc", oclcs.toArray(new String[0]));
		docUtil.addTags(doc, "addata", "oclcid", oclcs.toArray(new String[0]));
		return doc;
	}
}