# Spring Boot IOT Project

This is a Java / Maven / Spring Boot (version 2.5.7) application build a backend service to store and process data from IOT devices. Using mongoDB as database technology.

## How to Run 
* Clone this repository 
* Make sure you are using JDK 1.8, Maven 3.x. And docker aready install on your machine.
* Use need to start docker-composer by running ```docker-compose up -d``` for build mongo db and mongo express.
* Open a command line (or terminal) and navigate to the folder where you have the project files. We can build and run the application by issuing the following command:

	MacOS/Linux:./mvnw spring-boot:run
	Windowns: mvnw spring-boot:run

* Check the stdout or spring-boot-logger.log file to make sure no exceptions are thrown

Once the application runs you should see something like this

```
 [33morg.springframework.boot.web.embedded.tomcat.TomcatWebServer[0;39m: Tomcat started on port(s): 8080 (http) with context path '/iot'
 [33morg.springframework.boot.StartupInfoLogger[0;39m: Started Application in 6.208 seconds (JVM running for 7.053)
```

### To view Swagger API docs
Run the server and browse to http://localhost:8080/iot/swagger-ui/

### To view your mongo database using mongo express
Run docker-compose and browse to http://localhost:8081/

## Explain application

The service is just a simple REST service using spring boot for build. It uses mongoDB to store the data. 
 
Here is what this little application demonstrates: 

* Full integration with the latest **Spring** Framework: inversion of control, dependency injection, etc.
* Writing a RESTful service using annotation: supports JSON request / response
* Automatic CRUD functionality against the data source using Spring *Repository* pattern
* Data return with format if success then return status = true and response . If false then status = false with message or error code.
* Handle global exception mapping from application exceptions to the right HTTP response with exception details in the body.
* Add log track when start or stop each service layer. Then easy to know where error happen.
* Using SpringBootTest and MockMVC test framework with associated libraries for integration test.
* Unit test service using Mockito.
* All APIs are "self-documented" by Swagger using annotations 

Here are some endpoints you can call:

### Create a devices resource

```
POST /iot/api/devices
Accept: application/json
Content-Type: application/json

{
    "deviceId":"123",
    "latitude": 41.25,
    "longitude":-120.9762,
    "data":{
        "humidity":345,
        "temperature":{
            "unit":"C",
            "value":"25.3"
        }
    }

}

RESPONSE: HTTP 201 (Created)
Location header: http://localhost:8080/iot/api/devices
```

### Get device collection with parameter optional startTime and EndTime

```
http://localhost:8080/iot/api/devices/123?startTime=2021-10-06T14:57:13.682&endTime=2021-12-06T14:57:13.682

Response: HTTP 200
Content: device collection 
```


## Summary breakdown of the approximate time 
* Preparation: 1 hour
* Coding: 8 hours (including tests)
* Documentation: 30 minutes
* Building and testing: 30 minutes
* Grand total: 10 hours


