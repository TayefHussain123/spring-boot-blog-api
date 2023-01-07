# **Build Restful CRUD API for a blog using Spring Boot, Mysql, JPA, Hibernate, and Swagger**

### **Steps to Setup**

1. **Clone the application** <br />
https://github.com/TayefHussain123/spring-boot-blog-api.git
2. **Mysql database** <br />
The root folder of the project contains the SQL file inside. The folder name is sql.

3. **Change mysql username and password as per your installation** <br />
open src/main/resources/application.properties.
change spring.datasource.username and spring.datasource.password as per your mysql installation.

4. **Run the app using maven** <br />
mvn spring-boot:run

7. **The app will start running at** <br />
http://localhost:8000

6. **Find all APIs in Swagger**<br />
http://localhost:8000/api/blog-api/v1/swagger-ui/index.html?configUrl=/api/blog-api/v1/swagger-endpoints/swagger-config