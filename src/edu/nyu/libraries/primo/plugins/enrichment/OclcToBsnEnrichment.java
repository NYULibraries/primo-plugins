/**
 * 
 */
package edu.nyu.libraries.primo.plugins.enrichment;

import java.util.Map;

import org.w3c.dom.Document;

/**
 * @author Scot Dalton
 *
 */
public class OclcToBsnEnrichment extends DataWarehouseEnrichmentPlugin {

	/**
	 * @throws Exception
	 */
	public OclcToBsnEnrichment() throws Exception {
		super();
	}

	/* (non-Javadoc)
	 * @see edu.nyu.libraries.primo.plugins.enrichment.DataWarehouseEnrichmentPlugin#getSqlStatement()
	 */
	@Override
	protected String getSqlStatement() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.exlibris.primo.api.enrichment.plugin.EnrichmentPlugIn#enrich(org.w3c.dom.Document, java.util.Map)
	 */
	@Override
	public Document enrich(Document arg0, Map arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
