Primo Plugins
=============
Primo plugins are hooks into Primo to extend functionality. 
This project represents the base classes and common utilities shared across plugins.

For more information, see the [API documentation](./apidocs)

NYU uses or will use 

1. [Enrichment Plugins](http://exlibrisgroup.org/display/PrimoOI/Enrichment+Plug-In+%28new+version%29)
2. [RTA Plugins](http://exlibrisgroup.org/display/PrimoOI/RTA+Plug-In+%28new+version%29)
3. [Push To Plugins](http://exlibrisgroup.org/display/PrimoOI/PushTo+Plug-In+%28export+records+to+social+bookmark+sites%29)
4. [File Splitter Plugins](http://exlibrisgroup.org/display/PrimoOI/File+Splitter+Plug-In)

The project leverages [Apache Maven](http://maven.apache.org/) for managing dependencies, building, packaging and generating Javadocs.

To install the package locally, run:

    $ mvn install

To implement Primo plugins several Primo libraries are required. 
To build the maven project we must download the appropriate JARs from the Primo servers and 
store them in our workstation's local maven repository.

Enrichment Plugins and RTA Plugins Dependency

    Download jar from the back office server at: $primo_dev/ng/primo/home/system/publish/client/primo_common-api.jar
    $ mvn install:install-file -Dfile=primo_common-api.jar \
    -Dpackaging=jar -DgroupId=com.exlibrisgroup.primo \ 
    -DartifactId=primo_common-api -Dversion=1.0

Push To Plugins Dependencies

    Download jar at: $primo_dev/ng/primo/home/thirdparty/openserver/server/search/deploy/primo_library-app.ear/lib/primo_library-common.jar
    $ mvn install:install-file -Dfile=primo_library-common.jar \
    -Dpackaging=jar -DgroupId=com.exlibrisgroup.primo \ 
    -DartifactId=primo_library-common -Dversion=1.0

    Download jar at: $primo_dev/ng/primo/home/system/thirdparty/openserver/server/search/deploy/jaguar-web.ear/lib/jaguar-client.jar
    $ mvn install:install-file -Dfile=jaguar-client.jar \
    -Dpackaging=jar -DgroupId=com.exlibrisgroup.primo \ 
    -DartifactId=jaguar-client -Dversion=1.0

File Splitter Plugins Dependency

    Download jar at: $primo_dev/ng/primo/home/system/publish/client/primo__publishing-api.jar
    $ mvn install:install-file -Dfile=primo_publishing-api.jar \
    -Dpackaging=jar -DgroupId=com.exlibrisgroup.primo \ 
    -DartifactId=primo_publishing-api -Dversion=1.0

The NYU Libraries uses [Capistrano](https://github.com/capistrano/capistrano) as its deploy tool. 
The deploy mechanism assumes [rvm](https://rvm.io/ "Ruby Version Manager") and 
[Ruby 1.9.3-p125](http://www.ruby-lang.org/en/news/2012/02/16/ruby-1-9-3-p125-is-released/) 
on the local deploy host but they may not be necessary. 
In order to deploy: 

    $ cap [-S branch=<branch-name>] [-S user=<user-name>] [staging|production] deploy:<task>

Since the Primo plugins library can be leveraged across servers, deploy tasks are created for specific deploys.
Currently the following deploy tasks are available

  - enrichment
