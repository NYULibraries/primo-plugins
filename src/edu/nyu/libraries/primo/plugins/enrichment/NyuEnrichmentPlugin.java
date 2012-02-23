/**
 * 
 */
package edu.nyu.libraries.primo.plugins.enrichment;

import com.exlibris.primo.api.enrichment.plugin.EnrichmentPlugIn;

import edu.nyu.libraries.primo.plugins.NyuPlugin;

/**
 * @author Scot Dalton
 * 
 * Base class for enrichment plugins.  Extends NyuPlugin
 * and implements the enrichment plugin interface provided 
 * by ExLibris.
 *
 */
public abstract class NyuEnrichmentPlugin extends NyuPlugin implements EnrichmentPlugIn {

	/**
	 * Public constructor for NYU Enrichments.
	 */
	public NyuEnrichmentPlugin() {
		super("NyuEnrichment");
	}
}
