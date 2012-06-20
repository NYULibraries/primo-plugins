Datawarehouse
=============

The NYU Libraries DataWarehouse is a utility library for retrieving data from a data warehouse.
NYU connects to a variety of databases in the warehouse for Primo enrichment plugins,
but the library can be used for any processes requiring connection to RDBMS.

The NYU Libraries DataWarehouse expects a file specifying the connection information.

The project leverages [Apache Maven](http://maven.apache.org/) for building and deploying jars.

To build the package:

    $ mvn package

To deploy the package:

    $ mvn deploy

NYU uses SQL Server and therefore requires the SQL Server JDBC driver to 
connect to our servers.  In our tests we use this driver.

Once you download the driver, you must it to your local maven repository.

    $ mvn install:install-file -Dfile=sqljdbc4.jar \ 
    -Dpackaging=jar -DgroupId=com.microsoft.sqlserver \ 
    -DartifactId=sqljdbc4 -Dversion=4.0
