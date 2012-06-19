Primo Plugins
=============
Primo plugins are hooks into Primo to extend functionality.

NYU uses or will use 

1. [Enrichment Plugins](http://exlibrisgroup.org/display/PrimoOI/Enrichment+Plug-In+%28new+version%29)
2. [RTA Plugins](http://exlibrisgroup.org/display/PrimoOI/RTA+Plug-In+%28new+version%29)
3. [Push To Plugins](http://exlibrisgroup.org/display/PrimoOI/PushTo+Plug-In+%28export+records+to+social+bookmark+sites%29)
4. [File Splitter Plugins](http://exlibrisgroup.org/display/PrimoOI/File+Splitter+Plug-In)

The project leverages [Apache Maven](http://maven.apache.org/) for building and deploying jars.

To build the package:

    $ mvn package

To deploy the package:

    $ mvn deploy

To implement Primo Plugins several Primo libraries are required. 
To build the maven project we must download the appropriate JARs from the Primo servers and 
store them in our workstation's local maven repository.

Enrichment Plugins and RTA Plugins Dependency

    Download jar at: $primo_dev/ng/primo/home/system/publish/client/primo_common-api.jar
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

