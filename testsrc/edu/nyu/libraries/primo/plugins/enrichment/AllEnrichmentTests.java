/**
 * 
 */
package edu.nyu.libraries.primo.plugins.enrichment;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses( {
	AlephBsnMapperTest.class,
	BsnToOclcMapperTest.class,
	SingleTableMapperTest.class
	})

/**
 * @author Scot Dalton
 *
 */
public class AllEnrichmentTests {
}
