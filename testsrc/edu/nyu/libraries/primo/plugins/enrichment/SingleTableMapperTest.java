/**
 * 
 */
package edu.nyu.libraries.primo.plugins.enrichment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.sql.ResultSet;
import java.util.List;
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
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Guice;

import edu.nyu.libraries.primo.test.util.EnrichmentDocUtils;
import edu.nyu.libraries.primo.test.util.MappingTableFetcher;
import edu.nyu.libraries.primo.test.util.PrimoLogger;
import edu.nyu.libraries.util.DataWarehouse;
import edu.nyu.libraries.util.DataWarehouseModule;

/**
 * @author Scot Dalton
 *
 */
public class SingleTableMapperTest {
	private IPrimoLogger primoLogger;
	private IMappingTablesFetcher mappingTableFetcher;
	private Map<String, Object> enrichmentPluginParams;
	private IEnrichmentDocUtils enrichmentDocUtils;
	private Document doc;
	private DataWarehouse dataWarehouse;
	private String mappingTableName;
	private String mapToColumnName;
	private String mapFromColumnName;
	private SectionTag mapFromSectionTag;
	private List<SectionTag> enrichmentSectionTags;
	
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
		mappingTableName = "HARVARD_PROJECT_OCLC_KEYS";
		mapToColumnName = "OCLC_MASTER";
		mapFromColumnName = "ALEPH_BSN";
		mapFromSectionTag = new SectionTag("control", "sourcerecordid");
		enrichmentSectionTags = Lists.newArrayList(
			new SectionTag("addata", "oclcid"), 
			new SectionTag("search", "oclc"));
	}

	/**
	 * Test instatiation of the BsnToOclc enrichment plugin
	 * @throws Exception 
	 */
	@Test
	public void testNew() {
		SingleTableMapper bsnToOclc = 
			new SingleTableMapper(mappingTableName, mapToColumnName, 
				mapFromColumnName, mapFromSectionTag, dataWarehouse, 
					enrichmentSectionTags);
		assertNotNull(bsnToOclc);
	}
	
	@Test
	public void testInit() throws Exception {
		SingleTableMapper bsnToOclc = 
			new SingleTableMapper(mappingTableName, mapToColumnName, 
				mapFromColumnName, mapFromSectionTag, dataWarehouse, 
					enrichmentSectionTags);
		bsnToOclc.init(primoLogger, mappingTableFetcher, 
			enrichmentPluginParams);
	}
	
	@Test
	public void testGetResultSet() throws Exception {
		SingleTableMapper bsnToOclc = 
			new SingleTableMapper(mappingTableName, mapToColumnName, 
				mapFromColumnName, mapFromSectionTag, dataWarehouse, 
					enrichmentSectionTags);
		ResultSet resultSet = bsnToOclc.getResultSet("001969478");
		resultSet.next();
		assertEquals("22983279", resultSet.getString(1));
	}
	
	@Test
	public void testEnrich() throws Exception {
		SingleTableMapper bsnToOclc = 
			new SingleTableMapper(mappingTableName, mapToColumnName, 
				mapFromColumnName, mapFromSectionTag, dataWarehouse, 
					enrichmentSectionTags);
		bsnToOclc.init(primoLogger, mappingTableFetcher, enrichmentPluginParams);
		assertNotNull(doc.getElementsByTagName("isbn").item(0));
		assertNull(doc.getElementsByTagName("oclcid").item(0));
		bsnToOclc.enrich(doc, enrichmentDocUtils);
		assertNotNull(doc.getElementsByTagName("oclcid").item(0));
		assertEquals("22983279", 
			doc.getElementsByTagName("oclcid").item(0).getTextContent());
	}
}