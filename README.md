## spring-boot-hikari-connection-pool

Purpose : Use hikari connection pool for db connection object re-use and caching. <br/>
Reason : Improve application run-time performance. <br/>

<br/>
### Local run steps <br/>
1- Start Spring Boot API by running main method containing class CustomerInfoApplication.java in your IDE. <br/>
2- Alternatively you can start your Docker container by following the commands below. <br/>
NOT : Execute maven command from where the pom.xml is located in the project directory to create Spring Boot executable jar. <br/>
<pre> 
$ mvn clean install <br/>
</pre>

2021-10-10 16:48:42,787 DEBUG [main] com.zaxxer.hikari.HikariConfig: SpringBootJPAHikariCP - configuration: <br/>
2021-10-10 16:48:42,812 DEBUG [main] com.zaxxer.hikari.HikariConfig: allowPoolSuspension.............false <br/>
2021-10-10 16:48:42,813 DEBUG [main] com.zaxxer.hikari.HikariConfig: autoCommit......................true <br/>
2021-10-10 16:48:42,813 DEBUG [main] com.zaxxer.hikari.HikariConfig: catalog.........................none <br/>
2021-10-10 16:48:42,821 DEBUG [main] com.zaxxer.hikari.HikariConfig: connectionTestQuery............."select 1" <br/>
2021-10-10 16:48:42,822 DEBUG [main] com.zaxxer.hikari.HikariConfig: connectionTimeout...............20000 <br/>
2021-10-10 16:48:42,827 DEBUG [main] com.zaxxer.hikari.HikariConfig: dataSourceProperties............{password=<masked>} <br/>
2021-10-10 16:48:42,827 DEBUG [main] com.zaxxer.hikari.HikariConfig: driverClassName................."org.h2.Driver" <br/>
2021-10-10 16:48:42,835 DEBUG [main] com.zaxxer.hikari.HikariConfig: poolName........................"SpringBootJPAHikariCP" <br/>
2021-10-10 16:48:42,841 INFO  [main] com.zaxxer.hikari.HikariDataSource: SpringBootJPAHikariCP - Starting... <br/>
2021-10-10 16:48:47,364 INFO  [main] com.zaxxer.hikari.pool.HikariPool: SpringBootJPAHikariCP - Added connection conn1: url=jdbc:h2:file:~/cust user=SA <br/>
2021-10-10 16:48:47,367 INFO  [main] com.zaxxer.hikari.HikariDataSource: SpringBootJPAHikariCP - Start completed. <br/>

![Hikari_Connection_Pool](docs/hikari_connection_pool_spring_boot.png) <br/>

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
<pre>
$ docker system prune <br/>
$ docker build . --tag demo  <br/>
$ docker run -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=dev" demo:latest <br/>
</pre>

## API OPERATIONS
### Save store with products successfully to database

Method : HTTP.POST <br/>
URL : http://localhost:8080/customer-info/store/save <br/>

Request : 
<pre>
curl --location --request POST 'http://localhost:8080/customer-info/store/save' \
--header 'Content-Type: application/json' \
--data-raw '{
  "name": "jeans_store",
  "products": [
    {
      "name": "prod1"
    },
    {
      "name": "prod2"
    },
    {
      "name": "prod3"
    }
  ]
}'
</pre><br/>

Response : 

HTTP response code 200 <br/>
<pre>
{
    "id": 1,
    "name": "jeans_store",
    "products": [
        {
            "id": 1,
            "name": "prod3"
        },
        {
            "id": 2,
            "name": "prod1"
        },
        {
            "id": 3,
            "name": "prod2"
        }
    ]
}
</pre>


### List Store saved to database

Method : HTTP.GET <br/>
URL : http://localhost:8080/customer-info/store/list <br/>

Request : 
<pre>
curl --location --request GET 'http://localhost:8080/customer-info/store/list'
</pre><br/>

Response : 

HTTP response code 200 <br/>
<pre>
[
    {
        "id": 1,
        "name": "jeans_store",
        "products": [
            {
                "id": 1,
                "name": "prod3"
            },
            {
                "id": 2,
                "name": "prod1"
            },
            {
                "id": 3,
                "name": "prod2"
            }
        ]
    }
]
</pre><br/>
