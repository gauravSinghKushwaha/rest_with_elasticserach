Description:-

It's java based Web project, exposing four REST based web services for adding , updating , searching drivers.
It uses MAVEN for building the project using java-8 and compatiable jars.

* The project requires atleast one running memcached instances, port and host to be declared in /src/main/resources/default.properties
* The project uses ElasticSearch for storing and querying drivers by location search. The elastic search cluster to be used could be set
  in /src/main/resources/default.properties file. For local dev we are running ElastiSearch in embedded mode, setting for that is already
  there in /src/main/resources/default.properties.
* In order to build project run mvn install
* In order to build project and run integration tests as well use mvn install -Pintegration-test   

Directory Structure

src/man/java JAVA classes and interfaces
src/man/resources resource files such as spring configuration, properties..etc 

src/test/java Test classes
src/test/resources resource files such as spring configuration, properties..etc for test classes

/src/integration-test/java Integration test classes
/src/integration-test/resources integration test resources

Primary tech stack is.

1) JAVA 8
2) CXF for REST implemetation
3) Spring 4.2.* for dependency injection
4) spring-data-elasticsearch for elasticsearch
5) log4j for logging
6) Junit for testing
7) Spymemcached for memcached client
8) Jetty 9.*


SOME useful MAVEN COMMANDS

1) runs the server at 9090
mvn jetty:run

2) mvn clean install
Build war using default user profile dev, only runs Unit test cases

3) mvn clean install -Pintegration-test
Build war, skips Unit-test cases, only runs integration tests. (test categorized using marker interface com.gaurav.test.IntegrationTest 

Changing log level at run time.

// change log level via log.jsp
http://localhost:9090/driversearch/log.jsp

Rest-Interfaces with example data

// ADD
POST http://localhost:9090/driversearch/resources/rest/drivers

[{"id":1,
"name":"AXB singh",
"latitude": 12.97161923,
"longitude": 77.59463452,
"accuracy": 0.7
},
{"id":2,
"name":"ABC singh",
"latitude": 12.97161923,
"longitude": 77.59463452,
"accuracy": 0.7
},
{"id":3,
"name":"XYZ singh"
}]

// Update driver location
PUT http://localhost:9090/driversearch/resources/rest/driver/3/location

{
"latitude": 12.97161923,
"longitude": 77.59463452,
"accuracy": 0.7
}

// Get all drivers
http://localhost:9090/driversearch/resources/rest/drivers/all?limit=10

// Get All Drivers
GET http://localhost:9090/driversearch/resources/rest/drivers?latitude=12.97161923&longitude=77.59463452&radius=500&limit=10

