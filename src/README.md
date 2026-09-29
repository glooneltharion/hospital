# 🏥 Hospital Management System

A full-stack hospital management application built with **Java 21 and Spring Boot 4**.

The project demonstrates the development of a modular backend application for managing patients, doctors, appointments, medical records and prescriptions. It combines a traditional server-rendered web interface with a REST API and event-driven communication using Apache Kafka.

---

## 🚀 Project Overview

The Hospital Management System provides functionality for:

* 👤 User and role management
* 🧑‍⚕️ Patient management
* 📅 Appointment management
* 🩺 Medical records
* 💊 Prescriptions
* 🔐 Authentication and authorization
* 🌐 REST API
* 🖥️ Server-side rendered UI with Thymeleaf
* 📨 Event-driven communication with Apache Kafka
* 🗄️ MySQL persistence
* 🧪 Unit, MVC and JPA integration testing
* 🐳 Docker-based Kafka infrastructure

The project is primarily intended as a **learning and portfolio project demonstrating modern Spring Boot development practices**.

---

# 🛠️ Technologies

| Technology        | Purpose                        |
| ----------------- | ------------------------------ |
| Java 21           | Programming language           |
| Spring Boot 4.0.6 | Application framework          |
| Spring MVC        | Web application and REST API   |
| Spring Data JPA   | Database access                |
| Hibernate         | ORM                            |
| Spring Security   | Authentication & authorization |
| Spring Validation | Input validation               |
| Spring Kafka      | Event-driven communication     |
| Thymeleaf         | Server-side HTML rendering     |
| MySQL             | Production database            |
| H2                | Test database                  |
| Lombok            | Boilerplate reduction          |
| MapStruct         | DTO mapping                    |
| JUnit 5           | Testing                        |
| Mockito           | Unit testing and mocking       |
| MockMvc           | MVC/API testing                |
| Gradle            | Build automation               |
| Docker            | Infrastructure                 |
| Apache Kafka      | Asynchronous messaging         |

---

# 🏗️ Architecture

The application follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

Additional components:

```text
REST Controller
      ↓
   Service
      ↓
 Repository
      ↓
    MySQL
```

Event-driven communication:

```text
Application
     │
     │ AppointmentCreated event
     ▼
   Kafka
     │
     ▼
 Kafka Consumer
     │
     ▼
 Event processing
```

The project separates responsibilities between controllers, services, repositories, entities, DTOs, mappers and infrastructure components.


---

# 👥 User Management

The system supports different user roles:

```text
ADMIN
DOCTOR
NURSE
RECEPTIONIST
```

Spring Security is used to protect application resources and implement role-based access control.

Example:

```text
ADMIN
 ├── User management
 ├── System administration
 └── Full access

DOCTOR
 ├── Patients
 ├── Appointments
 ├── Medical records
 └── Prescriptions

NURSE
 ├── Patients
 ├── Appointments
 └── Nurse notes

RECEPTIONIST
 ├── Patients
 └── Appointments
```

---

# 🧑‍⚕️ Patient Management

The patient module demonstrates standard CRUD operations.

Supported operations include:

* Create patient
* Read patient
* Update patient
* Delete patient
* List patients
* Search patients
* Sort patients
* Check whether an email already exists

Example repository query:

```java
List<Patient> findByLastNameContainingIgnoreCase(String lastName);

boolean existsByEmail(String email);
```

---

# 📅 Appointment Management

Appointments connect patients with doctors.

An appointment contains information such as:

* Patient
* Doctor
* Start time
* End time
* Status
* Reason

Appointment statuses are represented using an enum.

Example:

```text
SCHEDULED
```

The application also demonstrates relationships between JPA entities.

---

# 🩺 Medical Records

Medical records are associated with:

* Patient
* Doctor
* Diagnosis
* Symptoms
* Treatment
* Notes

This demonstrates entity relationships and persistence using Spring Data JPA.

---

# 💊 Prescriptions

Doctors can create prescriptions associated with patients.

A prescription contains:

* Medication
* Dosage
* Frequency
* Duration
* Patient
* Doctor

---

# 🌐 REST API

The application exposes REST endpoints under:

```text
/api
```

Example patient endpoints:

```http
GET    /api/patients
GET    /api/patients/{id}
POST   /api/patients
PUT    /api/patients/{id}
DELETE /api/patients/{id}
```

Example request:

```http
POST /api/patients
Content-Type: application/json
```

```json
{
  "firstName": "John",
  "lastName": "Smith",
  "email": "john@example.com"
}
```

The REST API demonstrates:

* JSON serialization
* Request body handling
* DTOs
* Validation
* HTTP methods
* HTTP status codes
* REST controller design

---

# 🖥️ Thymeleaf Web Interface

The application also provides a traditional server-side rendered web interface using **Thymeleaf**.

The web layer demonstrates:

* Thymeleaf templates
* Model attributes
* Form binding
* Form validation
* Redirects
* Reusable layout fragments
* Spring Security integration
* Role-based UI elements

Example flow:

```text
Browser
   ↓
PatientController
   ↓
PatientService
   ↓
PatientRepository
   ↓
MySQL
```

---

# 🔐 Spring Security

Spring Security is used for application authentication and authorization.

The project demonstrates:

* Login
* Password hashing
* Role-based authorization
* Protected endpoints
* Security configuration
* Integration with Thymeleaf

Passwords are encoded using Spring Security's `PasswordEncoder`.

---

# 📨 Apache Kafka

The application demonstrates event-driven communication using **Apache Kafka**.

For example, when an appointment is created, an event can be published:

```text
Appointment
     │
     ▼
AppointmentCreated
     │
     ▼
Kafka topic
     │
     ▼
Kafka Consumer
```

Kafka is used to demonstrate asynchronous communication between application components.

The configured topic is:

```text
appointment-created
```

---

# 🐳 Docker

Kafka is run using Docker.

Example infrastructure:

```text
Docker
  │
  └── Kafka
       │
       └── appointment-created
```

The application can connect to Kafka through:

```text
localhost:9092
```

---

# 🗄️ Database

The application uses **MySQL** as its main database.

Main entities include:

```text
User
Patient
Appointment
MedicalRecord
Prescription
NurseNote
```

Relationships are implemented using JPA annotations such as:

```java
@OneToMany
@ManyToOne
@OneToOne
```

Enums are persisted using:

```java
@Enumerated(EnumType.STRING)
```

---

# 🌱 Demo Data

The project contains a `DataInitializer` that creates demo data when the database is empty.

The initializer creates:

* Admin
* Doctor
* Nurse
* Receptionist
* Patients
* Appointments
* Medical records
* Prescriptions

Demo data is initialized only when the database does not already contain users.

The initializer is disabled during tests using the `test` profile.

```java
@Configuration
@Profile("!test")
public class DataInitializer {
    ...
}
```

This prevents test environments from being polluted by application demo data.

---

# 🧪 Testing

The project demonstrates several types of automated tests.

## Repository tests

Spring Data JPA repositories are tested against an in-memory H2 database.

Example:

```java
@DataJpaTest
@ActiveProfiles("test")
class PatientRepositoryTest {
    ...
}
```

Repository tests verify:

* Custom queries
* Case-insensitive searches
* Existence checks
* JPA persistence

---

## Service unit tests

Services are tested independently using Mockito.

Example:

```java
@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {
    ...
}
```

Dependencies such as repositories and mappers are mocked.

This allows the business logic to be tested without starting Spring.

---

## MVC Controller tests

The Thymeleaf controller is tested using:

```java
@WebMvcTest(PatientController.class)
```

Tests cover:

* Page rendering
* Form submission
* Redirects
* Validation errors
* Model attributes

---

## REST Controller tests

REST endpoints are tested with MockMvc.

Example:

```java
@WebMvcTest(PatientRestController.class)
class PatientRestControllerTest {
    ...
}
```

Tests cover:

* GET requests
* POST requests
* DELETE requests
* JSON serialization
* HTTP status codes
* Service interaction

---

# 🔄 Test Profiles

The project uses a separate `test` Spring profile.

Example:

```text
application.properties
application-test.properties
```

Tests can activate it using:

```java
@ActiveProfiles("test")
```

The test environment uses H2 instead of the normal MySQL database.

This keeps automated tests isolated from the development database.

---

# 🧩 DTOs and Mapping

The application separates persistent entities from objects exposed through the API and web layer.

For example:

```text
Patient
   │
   ▼
PatientDTO
   │
   ▼
Controller
```

MapStruct is used to reduce manual mapping code.

This demonstrates separation between:

* Persistence models
* DTOs
* Business logic
* API contracts

---

# ✅ Validation

Jakarta Bean Validation is used for validating incoming data.

Example:

```java
@Pattern(
    regexp = "^\\+4219\\d{8}$",
    message = "Phone number must be in format +4219XXXXXXXX"
)
private String phoneNumber;
```

Validation is used in both:

* Thymeleaf forms
* REST requests

---

# 🧠 Concepts Demonstrated

This project brings together several important backend development concepts:

### Java

* OOP
* Enums
* Collections
* Streams
* `LocalDate`
* `LocalDateTime`
* Exception handling
* Dependency injection

### Spring Boot

* Dependency injection
* Configuration
* Profiles
* Spring MVC
* Spring Data JPA
* Spring Security
* Validation
* Spring Kafka

### Persistence

* JPA entities
* Entity relationships
* Repository pattern
* Derived queries
* Transactions
* MySQL
* H2

### Web development

* MVC
* REST
* HTTP methods
* JSON
* DTOs
* Thymeleaf
* Form validation

### Messaging

* Kafka producers
* Kafka consumers
* Topics
* Event-driven architecture

### Testing

* JUnit 5
* Mockito
* MockMvc
* `@WebMvcTest`
* `@DataJpaTest`
* Unit testing
* Integration testing
* Test profiles
* H2

### Infrastructure

* Gradle
* Docker
* Kafka
* Environment-specific configuration

---

# ▶️ Running the Application

## Requirements

Before running the project, install:

* Java 21
* Docker
* MySQL
* Git

---

## Clone the repository

```bash
git clone <repository-url>
cd hospital
```

---

## Start Kafka

Start the Docker infrastructure:

```bash
docker compose up -d
```

---

## Configure MySQL

Create a database:

```sql
CREATE DATABASE hospital;
```

Configure the connection in:

```text
src/main/resources/application.properties
```

---

## Run the application

Using Gradle:

```bash
./gradlew bootRun
```

On Windows:

```powershell
.\gradlew bootRun
```

The application will start on the configured Spring Boot port.

---

# 🧪 Running Tests

Run the complete test suite:

```bash
./gradlew test
```

Windows:

```powershell
.\gradlew test
```

Run a specific test:

```powershell
.\gradlew test --tests "com.glooneltharion.hospital.repositories.PatientRepositoryTest"
```

---

# 🔮 Possible Future Improvements

Potential extensions include:

* More comprehensive validation
* Global REST exception handling
* Pagination
* Advanced patient search
* Appointment conflict detection
* Audit logging
* More Kafka event types
* Notification system
* OpenAPI / Swagger documentation
* More comprehensive integration tests
* Testcontainers for MySQL and Kafka
* CI/CD pipeline
* Improved frontend UX
* Monitoring and health checks

---
