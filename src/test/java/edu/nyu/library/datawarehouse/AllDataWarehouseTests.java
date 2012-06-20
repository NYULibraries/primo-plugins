/**
 * 
 */
package edu.nyu.library.datawarehouse;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses( { 
	DataWarehouseModuleTest.class, 
	DataWarehouseTest.class })

/**
 * @author Scot Dalton
 * Suite for running all tests.
 */
public class AllDataWarehouseTests {
}
