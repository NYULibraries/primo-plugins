/**
 * 
 */
package edu.nyu.libraries.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

/**
 * @author Scot Dalton
 * 
 * Class for querying the NYU Libraries Data Warehouse.
 */
public class DataWarehouse {
	private Connection connection;
	private Map<String, PreparedStatement> preparedStatements;
	private PreparedStatement activePreparedStatement;
	
	@Inject
	public DataWarehouse(Connection connection) {
		this.connection = connection;
		preparedStatements = Maps.newHashMap();
	}
	
	/**
	 * Prepare a statement for querying the DataWarehouse
	 * @param sql
	 * @throws SQLException
	 */
	public void prepareStatement(String sql) throws SQLException {
		if(preparedStatements.containsKey(sql))
			activePreparedStatement = preparedStatements.get(sql);
		else {
			activePreparedStatement = connection.prepareStatement(sql);
			preparedStatements.put(sql, activePreparedStatement);
		}
	}
	
	/**
	 * Execute the prepared statement.
	 * Throws a NullPointer if a statement was not prepared.
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executePreparedStatement() throws SQLException {
		if(activePreparedStatement == null)
			throw new NullPointerException(
				"You must set a SQL statement before executing.");
		ResultSet activeResultSet = activePreparedStatement.getResultSet();
		activePreparedStatement = null;
		return activeResultSet;
	}
}