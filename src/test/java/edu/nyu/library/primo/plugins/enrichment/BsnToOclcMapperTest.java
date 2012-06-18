/**
 * 
 */
package edu.nyu.library.primo.plugins.enrichment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.sql.ResultSet;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import main.java.edu.nyu.library.primo.plugins.enrichment.BsnToOclcMapper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.w3c.dom.Document;

import com.exlibris.primo.api.common.IMappingTablesFetcher;
import com.exlibris.primo.api.common.IPrimoLogger;
import com.exlibris.primo.api.plugins.enrichment.IEnrichmentDocUtils;
import com.google.common.collect.Maps;
import com.google.inject.Guice;

import edu.nyu.libary.datawarehouse.DataWarehouse;
import edu.nyu.libary.datawarehouse.DataWarehouseModule;
import edu.nyu.library.primo.plugins.test.util.EnrichmentDocUtils;
import edu.nyu.library.primo.plugins.test.util.MappingTableFetcher;
import edu.nyu.library.primo.plugins.test.util.PrimoLogger;


/**
 * @author Scot Dalton
 *
 */
public class BsnToOclcMapperTest {
	private IPrimoLogger primoLogger;
	private IMappingTablesFetcher mappingTableFetcher;
	private Map<String, Object> enrichmentPluginParams;
	private IEnrichmentDocUtils enrichmentDocUtils;
	private Document doc;
	private DataWarehouse dataWarehouse;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setup() throws Exception {
		enrichmentDocUtils = new EnrichmentDocUtils();
		primoLogger = new PrimoLogger();
		mappingTableFetcher = new MappingTableFetcher();
		enrichmentPluginParams = Maps.newHashMap();
		doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().
			parse(new File("testfiles/nyu_aleph.xml"));
		dataWarehouse = Guice.createInjector(
			new DataWarehouseModule("./META-INF/datawarehouse.properties")).
				getInstance(DataWarehouse.class);
	}

	/**
	 * Test instatiation of the BsnToOclc enrichment plugin
	 * @throws Exception 
	 */
	@Test
	public void testNew() {
		BsnToOclcMapper bsnToOclcMapper = new BsnToOclcMapper(dataWarehouse);
		assertNotNull(bsnToOclcMapper);
	}
	
	@Test
	public void testInit() throws Exception {
		BsnToOclcMapper bsnToOclcMapper = new BsnToOclcMapper(dataWarehouse);
		bsnToOclcMapper.init(primoLogger, mappingTableFetcher, 
			enrichmentPluginParams);
	}
	
	@Test
	public void testGetResultSet() throws Exception {
		BsnToOclcMapper bsnToOclcMapper = new BsnToOclcMapper(dataWarehouse);
		ResultSet resultSet = bsnToOclcMapper.getResultSet("001969478");
		resultSet.next();
		assertEquals("22983279", resultSet.getString(1));
	}
	
	@Test
	public void testEnrich() throws Exception {
		BsnToOclcMapper bsnToOclcMapper = new BsnToOclcMapper(dataWarehouse);
		bsnToOclcMapper.init(primoLogger, mappingTableFetcher, enrichmentPluginParams);
		assertNotNull(doc.getElementsByTagName("isbn").item(0));
		assertNull(doc.getElementsByTagName("oclcid").item(0));
		bsnToOclcMapper.enrich(doc, enrichmentDocUtils);
		assertNotNull(doc.getElementsByTagName("oclcid").item(0));
		assertEquals("22983279", 
			doc.getElementsByTagName("oclcid").item(0).getTextContent());
	}
}