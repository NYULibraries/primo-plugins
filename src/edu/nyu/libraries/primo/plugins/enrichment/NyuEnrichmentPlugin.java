/**
 * 
 */
package edu.nyu.libraries.primo.plugins.enrichment;


import java.util.Map;

import org.w3c.dom.Document;

import com.exlibris.primo.api.common.IMappingTablesFetcher;
import com.exlibris.primo.api.common.IPrimoLogger;
import com.exlibris.primo.api.plugins.enrichment.EnrichmentPlugin;

import edu.nyu.libraries.primo.plugins.NyuPlugin;

/**
 * @author Scot Dalton
 * 
 * Base class for enrichment plugins.  Extends NyuPlugin
 * and implements the enrichment plugin interface provided 
 * by ExLibris.
 *
 */
public abstract class NyuEnrichmentPlugin extends NyuPlugin implements EnrichmentPlugin {
	private IPrimoLogger primoLogger;
	private IMappingTablesFetcher tablesFetcher;
	private Map<String, Object> paramsMap;

	/**
	 * Public constructor for NYU Enrichments.
	 */
	public NyuEnrichmentPlugin() {
		super("NyuEnrichments");
	}
	
	/**
	 * Initializes the NyuEnrichmentPlugin.  Subclasses should override as
	 * necessary.
	 */
	@Override
	public void init(IPrimoLogger primoLogger, 
			IMappingTablesFetcher tablesFetcher, 
			Map<String, Object> paramsMap) throws Exception {
		this.primoLogger = primoLogger;
		System.out.println(this.getClass());
		this.primoLogger.setClass(this.getClass());
		this.tablesFetcher = tablesFetcher;
		this.paramsMap = paramsMap;
		registerLogger(primoLogger);
	}
	
	/**
	 * Logs the exception and returns false.
	 * 
	 * @see com.exlibris.primo.api.plugins.enrichment.EnrichmentPlugin#
	 * shouldSkipFailedRecord(org.w3c.dom.Document, java.lang.Exception)
	 */
	@Override
	public boolean shouldSkipFailedRecord(Document doc, Exception e) {
		logError("Enrichment failed for record " + 
			doc.getElementsByTagName("recordid").item(0).getTextContent() +
			"\n" + e.getMessage(), e);
		return false;
	}
	
	/**
	 * Returns an IMappingTablesFetcher.
	 * Returns the IMappingTablesFetcher initialized with this NyuEnrichmentPlugin.
	 * @return
	 */
	protected IMappingTablesFetcher getTablesFetcher() {
		return tablesFetcher;
	}

	/**
	 * Returns the parameter map associated with this NyuEnrichmentPlugin
	 * @return
	 */
	protected Map<String, Object> getParamsMap() {
		return paramsMap;
	}
}