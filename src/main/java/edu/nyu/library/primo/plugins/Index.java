/**
 * 
 */
package edu.nyu.library.primo.plugins;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import edu.nyu.library.primo.plugins.enrichment.BsnToOclcMapper;


/**
 * @author Scot Dalton
 *
 */
public class Index {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws IOException, SQLException {
		Properties dataWarehouseProperties = new Properties();
		System.out.println(new File(".").getAbsolutePath());
		InputStream inputStream = 
			ClassLoader.getSystemClassLoader().getResourceAsStream("META-INF/datawarehouse.properties");
		dataWarehouseProperties.load(inputStream);
		for(Object property: dataWarehouseProperties.values())
			System.out.println(property);
		BsnToOclcMapper bsnToOclcMapper = new BsnToOclcMapper();
		ResultSet resultSet = bsnToOclcMapper.getResultSet("001969478");
		resultSet.next();
		System.out.println(resultSet.getString(1));
	}
}