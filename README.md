## spring-boot-hikari-connection-pool

Purpose : Use hikari connection pool for db connection object re-use and caching. <br/>
Reason : Improve application run-time performance.  <br/>

### Local run steps <br/>
1- Start Spring Boot API by running main method containing class CustomerInfoApplication.java in your IDE. <br/>
2- Alternatively you can start your Docker container by following the commands below. <br/>
NOT : Execute maven command from where the pom.xml is located in the project directory to create Spring Boot executable jar. <br/>
<pre> 
$ mvn clean install -U -X <br/>
</pre>

2021-10-12 23:12:25,670 DEBUG [main] com.zaxxer.hikari.HikariConfig: SpringBootJPAHikariCP - configuration: <br/>
2021-10-12 23:12:25,699 DEBUG [main] com.zaxxer.hikari.HikariConfig: connectionInitSql..............."select 1" <br/>
2021-10-12 23:12:25,700 DEBUG [main] com.zaxxer.hikari.HikariConfig: connectionTestQuery............."select 1" <br/>
2021-10-12 23:12:25,700 DEBUG [main] com.zaxxer.hikari.HikariConfig: connectionTimeout...............20000 <br/>
2021-10-12 23:12:25,701 DEBUG [main] com.zaxxer.hikari.HikariConfig: dataSourceProperties............{password=<masked>} <br/>
2021-10-12 23:12:25,702 DEBUG [main] com.zaxxer.hikari.HikariConfig: driverClassName................."org.h2.Driver" <br/>
2021-10-12 23:12:25,704 DEBUG [main] com.zaxxer.hikari.HikariConfig: isolateInternalQueries..........false <br/>
2021-10-12 23:12:25,704 DEBUG [main] com.zaxxer.hikari.HikariConfig: jdbcUrl.........................jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1 <br/>
2021-10-12 23:12:25,705 DEBUG [main] com.zaxxer.hikari.HikariConfig: maxLifetime.....................30000 <br/>
2021-10-12 23:12:25,705 DEBUG [main] com.zaxxer.hikari.HikariConfig: maximumPoolSize.................10 <br/>
2021-10-12 23:12:25,708 DEBUG [main] com.zaxxer.hikari.HikariConfig: minimumIdle.....................3 <br/>
2021-10-12 23:12:25,709 DEBUG [main] com.zaxxer.hikari.HikariConfig: password........................<masked> <br/>
2021-10-12 23:12:25,713 DEBUG [main] com.zaxxer.hikari.HikariConfig: poolName........................"SpringBootJPAHikariCP" <br/>
2021-10-12 23:12:25,714 DEBUG [main] com.zaxxer.hikari.HikariConfig: username........................"sa" <br/>
2021-10-12 23:12:25,715 DEBUG [main] com.zaxxer.hikari.HikariConfig: validationTimeout...............250 <br/>
2021-10-12 23:12:25,715 INFO  [main] com.zaxxer.hikari.HikariDataSource: SpringBootJPAHikariCP - Starting... <br/>
2021-10-12 23:12:25,989 INFO  [main] com.zaxxer.hikari.pool.HikariPool: SpringBootJPAHikariCP - Added connection conn0: url=jdbc:h2:mem:testdb user=SA <br/>
2021-10-12 23:12:25,992 INFO  [main] com.zaxxer.hikari.HikariDataSource: SpringBootJPAHikariCP - Start completed. <br/>
2021-10-12 23:12:26,004 INFO  [main] org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration: H2 console available at '/h2'. Database available at 'jdbc:h2:mem:testdb' <br/>

![Hikari_Connection_Pool](doc/hikari_connection_pool_spring_boot.png) <br/>

### Tech Stack
Java 11 <br/>
H2 Database Engine <br/>
spring boot <br/>
spring boot starter data jpa <br/>
spring boot starter web <br/>
spring boot starter test <br/>
hibernate <br/>
logback <br/>
maven <br/>
springfox-swagger-ui <br/>
hikari connection pool <br/>
Docker <br/>
<br/>

### Docker build run steps
NOT : Execute docker commands from where the DockerFile is located. <br/>
NOT : Tested on Windows 10 with Docker Desktop Engine Version : 20.10.8 <br/>
<pre>
$ docker system prune -a --volumes <br/>
$ docker build . --tag demo  <br/>
$ docker images <br/>
$ REPOSITORY   TAG       IMAGE ID       CREATED         SIZE <br/>
$ demo         latest    9d4a0ec3294e   6 minutes ago   288MB <br/>
$ docker run -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=dev" demo:latest <br/>
</pre>

## API OPERATIONS
### Save a new customer to database

Method : HTTP.POST <br/>
URL : localhost:8080/customer-info/customer/save <br/>
Request Body : <br/>
{ <br/>
    "name": "name1", <br/>
    "age": 1, <br/>
    "shippingAddress": { <br/>
        "streetName": "software", <br/>
        "city": "ankara", <br/>
        "country": "TR" <br/>
    } <br/>
} <br/>

Curl Request : <br/>
<pre>
curl --location --request POST 'localhost:8080/customer-info/customer/save' \ 
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "name1",
    "age": 1,
    "shippingAddress": {
        "streetName": "software",
        "city": "ankara",
        "country": "TR"
    }
}'
</pre><br/>

Response : 

HTTP response code 200 <br/>
<pre>
{
    "id": 1,
    "name": "name1",
    "age": 1,
    "shippingAddress": {
        "id": 3,
        "streetName": "software",
        "city": "ankara",
        "country": "TR"
    }
}
</pre>


### List all customers saved to database

Method : HTTP.GET <br/>
URL : localhost:8080/customer-info/customer/list <br/>
Request Body : <br/>
{}<br/>
Curl Request : <br/>
<pre>
curl --location --request GET 'localhost:8080/customer-info/customer/list' \
--header 'Content-Type: application/json' \
--data-raw '{}'
</pre>
<br/>

Response : 

HTTP response code 200 <br/>
<pre>
[
    {
        "id": 1,
        "name": "name1",
        "age": 1,
        "shippingAddress": {
            "id": 1,
            "streetName": "software",
            "city": "ankara",
            "country": "TR"
        }
    }
]
</pre>
<br/>
