/**
 * 
 */
package edu.nyu.libraries.util;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.FileReader;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.sql.Driver;
import java.util.Properties;

import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

/**
 * @author Scot Dalton
 *
 */
public class DataWarehouseConnectionModule extends AbstractModule {
	private final String PROPERTIES_FILE = 
		"./config/datawarehouse.properties";
	private Properties properties;

	
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
	
	public DataWarehouseConnectionModule() {
		super();
		properties = new Properties();
		try {
			properties.load(new FileReader(PROPERTIES_FILE));
		} catch (Exception e) {
			addError(e);
		}
	}

	@Override
	protected void configure() {
		bind(Driver.class).to(SQLServerDriver.class);
		bind(String.class).annotatedWith(ConnectionURL.class)
			.toInstance(properties.getProperty("connectionURL"));
		bind(String.class).annotatedWith(Username.class)
			.toInstance(properties.getProperty("username"));
		bind(String.class).annotatedWith(Password.class)
			.toInstance(properties.getProperty("password"));
	}
}
