/**
 * 
 */
package edu.nyu.libraries.util;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.sql.Driver;

import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

/**
 * @author Scot Dalton
 *
 */
public class DataWarehouseConnectionModule extends AbstractModule {
	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface ConnectionURL {};

	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface Username {};

	@BindingAnnotation 
	@Target({ FIELD, PARAMETER, METHOD }) 
	@Retention(RUNTIME)
	public @interface Password {};

	private final String CONNECTION_URL = 
		"jdbc:sqlserver://"+
		"cadmus.bobst.nyu.edu\\CADMUS_SQLSERVER:1433"+
		";databaseName=*****"+
		";selectMethod=cursor";
	private final String USER_NAME = "*****";
	private final String PASSWORD = "*****";

	@Override
	protected void configure() {
		bind(Driver.class).to(SQLServerDriver.class);
		bind(String.class).annotatedWith(ConnectionURL.class)
			.toInstance(CONNECTION_URL);
		bind(String.class).annotatedWith(Username.class)
			.toInstance(USER_NAME);
		bind(String.class).annotatedWith(Password.class)
			.toInstance(PASSWORD);
	}
}
