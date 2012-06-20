/**
 * 
 */
package edu.nyu.library.datawarehouse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.google.common.collect.Maps;
import com.google.inject.Singleton;

/**
 * @author Scot Dalton
 * DataWarehouse provides an interface to query a Data Warehouse.
 * 
 */
@Singleton
public class DataWarehouse {
	private Connection connection;
	private Map<String, PreparedStatement> preparedStatements;
	private PreparedStatement activePreparedStatement;
	
	/**
	 * Public constructor takes the connection information for the 
	 * DataWarehouse.
	 * @param connection
	 */
	public DataWarehouse(Connection connection) {
		this.connection = connection;
		preparedStatements = Maps.newHashMap();
	}
	
	/**
	 * Close the DataWarehouse connection.
	 * @throws SQLException 
	 */
	public void closeConnection() throws SQLException {
		connection.close();
	}
	
	/**
	 * Execute the given SQL command
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executeQuery(String sql) throws SQLException {
		return connection.createStatement().executeQuery(sql);
	}
	
	/**
	 * Activate a prepared statement for querying the DataWarehouse
	 * @param sql
	 * @throws SQLException
	 */
	public void activatePreparedStatement(String sql) 
			throws SQLException {
		if(preparedStatements.containsKey(sql))
			activePreparedStatement = preparedStatements.get(sql);
		else {
			activePreparedStatement = connection.prepareStatement(sql);
			preparedStatements.put(sql, activePreparedStatement);
		}
	}
	
	/**
	 * Sets the designated parameter to the given int value for the
	 * activated prepared statement. 
	 * @param index
	 * @param value
	 * @throws SQLException
	 */
	public void setIntForActivatedPreparedStatement(int index, int value) 
			throws SQLException {
		if(activePreparedStatement == null)
			throw new NullPointerException(
				"You must activate a SQL statement before setting its value.");
		activePreparedStatement.setInt(index, value);
	}
	
	/**
	 * Sets the designated parameter to the given String value for the
	 * activated prepared statement. 
	 * @param index
	 * @param value
	 * @throws SQLException
	 */
	public void setStringForActivatedPreparedStatement(int index, String value) 
			throws SQLException {
		if(activePreparedStatement == null)
			throw new NullPointerException(
				"You must activate a SQL statement before setting its value.");
		activePreparedStatement.setString(index, value);
	}
	
	/**
	 * Execute a query for the activated prepared statement.
	 * Throws a NullPointer if a statement was not prepared.
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executeQueryForActivatedPreparedStatement() 
			throws SQLException {
		if(activePreparedStatement == null)
			throw new NullPointerException(
				"You must activate a SQL statement before executing.");
		return activePreparedStatement.executeQuery();
	}

	/**
	 * Only implemented for testing purposes.
	 * @return
	 */
	protected PreparedStatement getActivatedPreparedStatement() {
		if(activePreparedStatement == null)
			throw new NullPointerException(
				"You must activate a SQL statement before executing.");
		return activePreparedStatement;
	}
}