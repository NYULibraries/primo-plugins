/**
 * 
 */
package edu.nyu.libraries.primo.plugins.enrichment;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.w3c.dom.Document;

import com.exlibris.primo.api.common.IMappingTablesFetcher;
import com.exlibris.primo.api.common.IPrimoLogger;
import com.exlibris.primo.api.plugins.enrichment.IEnrichmentDocUtils;
import com.google.common.collect.Maps;

import edu.nyu.libraries.primo.test.utilities.EnrichmentDocUtils;
import edu.nyu.libraries.primo.test.utilities.MappingTableFetcher;
import edu.nyu.libraries.primo.test.utilities.PrimoLogger;


/**
 * @author Scot Dalton
 *
 */
public class BsnToOclcTest {
	private IPrimoLogger primoLogger;
	private IMappingTablesFetcher mappingTableFetcher;
	private Map<String, Object> enrichmentPluginParams;
	private IEnrichmentDocUtils enrichmentDocUtils;
	private Document doc;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setup() throws Exception {
		enrichmentDocUtils = new EnrichmentDocUtils();
		primoLogger = new PrimoLogger();
		mappingTableFetcher = new MappingTableFetcher();
		enrichmentPluginParams = Maps.newHashMap();
		doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().
			parse(new File("testhelp/nyu_aleph.xml"));
	}

	/**
	 * Test instatiation of the BsnToOclc enrichment plugin
	 * @throws Exception 
	 */
	@Test
	public void testNew() throws Exception {
		BsnToOclc bsnToOclc = new BsnToOclc();
		assertNotNull(bsnToOclc);
	}
	
	@Test
	public void testInit() throws Exception {
		BsnToOclc bsnToOclc = new BsnToOclc();
		bsnToOclc.init(primoLogger, mappingTableFetcher, 
			enrichmentPluginParams);
	}
	
	@Test
	public void testEnrich() throws Exception {
		BsnToOclc bsnToOclc = new BsnToOclc();
		bsnToOclc.init(primoLogger, mappingTableFetcher, enrichmentPluginParams);
		assertNotNull(doc.getElementsByTagName("isbn").item(0));
		assertNull(doc.getElementsByTagName("oclcid").item(0));
		bsnToOclc.enrich(doc, enrichmentDocUtils);
		assertNotNull(doc.getElementsByTagName("oclcid").item(0));
		assertEquals("22983279", 
			doc.getElementsByTagName("oclcid").item(0).getTextContent());
	}
}