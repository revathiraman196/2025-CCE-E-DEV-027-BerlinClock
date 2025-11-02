#Prerequisites

Java 17
Maven 3.6 or higher
Run the AuthService API present in the project and generate a JWT by calling the Login API. Details are available in the README file of the project.

#Build and Run

mvn clean install
mvn spring-boot:run

#API access

Pass the JWT in the Authorization header as a Bearer token when calling the Berlin Clock API endpoints.

