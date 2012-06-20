/**
 * 
 */
package edu.nyu.library.primo.plugins.enrichment;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( {
	AlephBsnMapperTest.class,
	BsnToOclcMapperTest.class,
	ConfigurableSingleTableMapperTest.class,
	SingleTableMapperTest.class
	})

/**
 * @author Scot Dalton
 *
 */
public class AllEnrichmentTests {
}
