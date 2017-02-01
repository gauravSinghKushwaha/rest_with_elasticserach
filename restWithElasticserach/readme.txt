*** SOME MAVEN COMMANDS ***

1) runs the server at 9090
mvn jetty:run

2) mvn clean install
Build war using default user profile dev, only runs Unit test cases

3) mvn clean install -Pintegration-test
Build war, skips Unit-test cases, only runs integration tests. (test categorized using marker interface com.gaurav.test.IntegrationTest 

// change log level via log.jsp
http://localhost:9090/driversearch/log.jsp

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

