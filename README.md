# **Build Restful CRUD API for a blog using Spring Boot, Mysql, JPA, Hibernate, and Swagger**

### **Steps to Setup**

1. **Clone the application**
https://github.com/TayefHussain123/spring-boot-blog-api.git
2. **Mysql database**
The root folder of the project contains the SQL file inside. The folder name is sql.

3. **Change mysql username and password as per your installation**
open src/main/resources/application.properties.
change spring.datasource.username and spring.datasource.password as per your mysql installation.

4. **Run the app using maven**
mvn spring-boot:run

**The app will start running at** 
http://localhost:8000

4. **Find all APIs in Swagger**
http://localhost:8000/api/blog-api/v1/swagger-ui/index.html?configUrl=/api/blog-api/v1/swagger-endpoints/swagger-config