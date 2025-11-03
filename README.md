# Prerequisites

- Java 17
- Maven 3.6 or higher
- Run the AuthService API present in the project and generate a JWT by calling the Login API. Details are available in the README file of the project.

# Build and Run

```bash
mvn clean install
mvn spring-boot:run

# API Access
- Refere Swagger File
- Get JWT from Auth Service by calling the Login API
- Pass this JWT in the Autherization as Bearer Token when accessing the Berlin clock API Endpoints
