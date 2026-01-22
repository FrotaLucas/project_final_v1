# Old Legasy System – Java Application for Smart Utility Management

Java application focused on customer registration for a utility company providing electricity, water, and gas. 

---
## Features:

REST Services: Implementation of a REST service for managing the customer layer and another for handling consumption readings per apartment.

Database Integration: Use of JDBC for database communication, along with Jersey and Jakarta technologies to support the API.

Unit Testing: Execution of unit tests using JUnit to validate both the service layer and the API layer.

Dependency Management: Use of Maven for dependency management and build automation.


## Technology Used

- **MariaDB**: An open-source relational database management system, used to store and manage application data.

- **Apache Maven 3.9.9**: A build automation tool primarily for Java projects, managing project dependencies and build lifecycle.

- **Java HotSpot™ 21.0**: The Java Virtual Machine (JVM) implementation used to run Java applications.

- **Postman**: An API client used for testing and documenting APIs.

- **Visual Studio Code**: A lightweight but powerful source code editor, ideal for frontend development.

- **IntelliJ IDEA**: An integrated development environment (IDE) for Java development.

- **Git**: A distributed version control system for tracking changes in source code during software development.



## Entity Diagram

#### Customer and Reading
![image](https://github.com/user-attachments/assets/e5c91287-11fc-485c-822f-003f43e84ec0)

 
Notes:
Relationship
1. Relationship: One-to-Many (One Customer can have multiple Readings).
2. Key caractheristics of Entitieas: optional to optional. 
> *Note: customer can exist without being linked to reading and reading can have its customer_id set to null* <


## Sequence Diagram

#### Flow: User  →  UI Layer  →  Controllers  →  Repositories →  Database  →  MariaDB Server
![image](https://github.com/user-attachments/assets/2962f44f-6faa-4e09-b008-7c933b49b456)


## Deploy & Execution

### Test Execution Commands

- **Run all tests**:  
  Execute `mvn test`.

- **Run a specific test class**:  
  Use `mvn -Dtest=MyClassOfTests test`.

- **Run a specific test method**:  
  Execute `mvn -Dtest=MyClassOfTests#myMethod test`.
  
### Technologies

- **JUnit**: A unit testing framework for Java, allowing developers to write and run repeatable tests.

- **Rest Assured**: A Java library for testing RESTful web services, simplifying the validation of responses.


### Run the application:
- mvn exec:java -DskipTests
