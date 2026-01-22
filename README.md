# Old Legasy System – Java Application for Smart Utility Management

Java application focused on customer registration for a utility company providing electricity, water, and gas. 

---
## 1. Features:

REST Services: Implementation of a REST service for managing the customer layer and another for handling consumption readings per apartment.

Database Integration: Use of JDBC for database communication, along with Jersey and Jakarta technologies to support the API.

Unit Testing: Execution of unit tests using JUnit to validate both the service layer and the API layer.

Dependency Management: Use of Maven for dependency management and build automation.


## 2. Architecture

### REST API Architecture

The application adheres to REST (Representational State Transfer) principles, ensuring scalability and maintainability.

### Key Characteristics of REST:

**1. Statelessness**: Each request from the client must contain all the information needed to process the request. The server does not store any client context between requests, enhancing scalability and reliability.

**2. Client-Server Architecture**: Separates the user interface concerns from the data storage concerns, allowing the client and server to evolve independently.

**3. Uniform Interface**: Defines a standardized way of communicating between client and server. Standard URIs for Resource Identification, a consistent representation of messages (such as JSON or XML), and the use of HTTP methods (GET, POST, PUT, DELETE, etc.) according to RESTful principles ensure a uniform and scalable API design.

**4. Layered System**:  RESTful arquitecture are built in hierarchical layers, allowing components to operate independently. Client → Proxy or Cache → Load Balancer → Backend Server.

**5. Cacheability**: The server can indicate whether a response is cacheable by the browser (e.g., using headers like Cache-Control: max-age=3600 or Expires: [date]). This allows the browser to retrieve data from the cache, reducing latency and improving performance.


### API Endpoints

#### 1. Customer API #### 

- POST /api/customers<br> Request Body:

![image](https://github.com/user-attachments/assets/658ce419-b084-4ddf-b877-3ae937de3f29)

- GET /api/customers/{uuid}

- GET /api/customers

- PUT /api/customers<br>
Request Body:

![image](https://github.com/user-attachments/assets/f5f1e4ec-2087-4808-9249-200c0f518142)

- DELETE /api/customers/{uuid}

#### 2. Reading API

- POST /api/readings<br>
Request Body:

![image](https://github.com/user-attachments/assets/10cf1ab6-37a4-4432-9400-faa4581c03c8)

- GET /api/readings/{uuid}

- GET /api/readings

- PUT /api/readings<br>
Request Body:

![image](https://github.com/user-attachments/assets/bd175b2c-ada4-47bc-81f9-918e279f5854)

- DELETE /api/readings/{uuid}

## 3. Technology Used

- **MariaDB**: An open-source relational database management system, used to store and manage application data.

- **Apache Maven 3.9.9**: A build automation tool primarily for Java projects, managing project dependencies and build lifecycle.

- **Java HotSpot™ 21.0**: The Java Virtual Machine (JVM) implementation used to run Java applications.

- **Postman**: An API client used for testing and documenting APIs.

- **Visual Studio Code**: A lightweight but powerful source code editor, ideal for frontend development.

- **IntelliJ IDEA**: An integrated development environment (IDE) for Java development.

- **Git**: A distributed version control system for tracking changes in source code during software development.


## 4. Diagram

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


## 5. Deploy & Execution

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
