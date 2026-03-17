# Ostad Java SpringBoot 3 - Module 15 Assignment

Secure Note-Taking REST API (Authentication & Authorization)

A Spring Boot application that demonstrates building a secure RESTful API for managing personal notes with HTTP Basic Authentication and Role-Based Access Control (RBAC).

---

## 📋 Project Overview

This project is part of the **Ostad Java & SpringBoot 3 Backend Web Development** course (Module 15). The goal is to implement a secure Note Management API with user authentication, role-based authorization, and ownership-based access control.

The application showcases:
- HTTP Basic Authentication
- Role-Based Access Control (RBAC) with two roles: `ROLE_USER` and `ROLE_ADMIN`
- Public registration endpoints for user and admin accounts
- Method-level security with `@PreAuthorize`
- Custom user details service for authentication
- Ownership validation for user-owned resources
- H2 in-memory database with JPA persistence

---

## 🎯 Assignment Goals

Build a secure RESTful API for managing personal notes with the following operations:

### Public Endpoints (No Authentication Required)
- `POST /api/auth/register/user`          — Register a new regular user
- `POST /api/auth/register/admin`         — Register a new admin user

### User Endpoints (ROLE_USER)
- `POST   /api/notes`                      — Create a new note
- `GET    /api/notes`                      — Get all own notes
- `GET    /api/notes/{id}`                 — Get specific note (must be owner)
- `PUT    /api/notes/{id}`                 — Update note (must be owner)
- `DELETE /api/notes/{id}`                 — Delete note (must be owner)

### Admin Endpoints (ROLE_ADMIN)
- `GET    /api/admin/notes`                — Get all notes in system
- `DELETE /api/admin/notes/{id}`           — Delete any note by id

---

## 🛠 Technology Stack

- **Java:** 21
- **Spring Boot:** 4.0.3 (spring-boot-starter-parent)
- **Spring Security:** For authentication and authorization
- **Spring Data JPA:** For database operations
- **H2 Database:** In-memory relational database
- **Maven:** Build automation and dependency management

### Key Dependencies
- spring-boot-starter-security
- spring-boot-starter-data-jpa
- spring-boot-starter-webmvc
- com.h2database:h2
- spring-boot-h2console

---

## 📁 Project Structure

```
Ostad_Java_SpringBoot_3_Module_15_Assignment/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/inventory/module_15_assignment/
│   │   │       ├── OstadJavaSpringBoot3Module15AssignmentApplication.java (Main entry point)
│   │   │       ├── controller/
│   │   │       │   ├── AuthController.java           (Registration endpoints)
│   │   │       │   ├── NoteController.java           (User note endpoints)
│   │   │       │   └── AdminNoteController.java      (Admin note endpoints)
│   │   │       ├── service/
│   │   │       │   ├── UserService.java              (User management)
│   │   │       │   └── NoteService.java              (Note business logic)
│   │   │       ├── repository/
│   │   │       │   ├── UserRepository.java           (User persistence)
│   │   │       │   └── NoteRepository.java           (Note persistence)
│   │   │       ├── entity/
│   │   │       │   ├── AppUser.java                  (User JPA entity)
│   │   │       │   ├── Note.java                     (Note JPA entity)
│   │   │       │   └── Role.java                     (Role enum: USER, ADMIN)
│   │   │       ├── dto/
│   │   │       │   ├── RegisterRequest.java          (Registration DTO)
│   │   │       │   ├── NoteRequest.java              (Note creation/update DTO)
│   │   │       │   └── NoteResponse.java             (Note response DTO)
│   │   │       └── security/
│   │   │           ├── CustomUserDetailsService.java (Spring Security integration)
│   │   │           └── SecurityConfig.java           (Security configuration)
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/example/inventory/module_15_assignment/
│               └── OstadJavaSpringBoot3Module15AssignmentApplicationTests.java
├── pstman / Postman_Collection.json                          (API testing collection)
├── pom.xml
├── mvnw / mvnw.cmd
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

- JDK 21 or higher
- Maven (or use the included Maven wrapper)
- Optional: Postman or similar REST client for testing

### Build

Open a terminal in the project root and run:

**Windows:**
```bash
mvnw.cmd clean package
```

**Linux/Mac:**
```bash
./mvnw clean package
```

### Run

**Option 1: Using Maven Spring Boot plugin (Windows):**
```bash
mvnw.cmd spring-boot:run
```

**Option 2: Using Maven Spring Boot plugin (Linux/Mac):**
```bash
./mvnw spring-boot:run
```

**Option 3: Run the built JAR (Windows/Linux/Mac):**
```bash
java -jar target/Module_15_Assignment-0.0.1-SNAPSHOT.jar
```

By default, the application starts on: **http://localhost:8080**

### Access H2 Console (Development)
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:notesdb`
- Username: `sa`
- Password: (leave blank)

---

## 🔌 API Endpoints

### Base URL
`http://localhost:8080`

---

### 1️⃣ Authentication Endpoints (Public)

#### Register User
- **POST** `/api/auth/register/user`
- **Content-Type:** `application/json`
- **Body:**
```json
{
  "username": "john_user",
  "password": "password123"
}
```
- **Response (201 Created):**
```json
{
  "id": 1,
  "username": "john_user",
  "role": "ROLE_USER"
}
```

#### Register Admin
- **POST** `/api/auth/register/admin`
- **Content-Type:** `application/json`
- **Body:**
```json
{
  "username": "admin_user",
  "password": "admin123"
}
```
- **Response (201 Created):**
```json
{
  "id": 2,
  "username": "admin_user",
  "role": "ROLE_ADMIN"
}
```

---

### 2️⃣ User Note Endpoints (ROLE_USER - Requires HTTP Basic Auth)

#### Create Note
- **POST** `/api/notes`
- **Auth:** HTTP Basic Auth
- **Content-Type:** `application/json`
- **Body:**
```json
{
  "title": "My First Note",
  "content": "This is the content of my note"
}
```
- **Response (201 Created):**
```json
{
  "id": 1,
  "title": "My First Note",
  "content": "This is the content of my note",
  "ownerUsername": "john_user"
}
```

#### Get All User Notes
- **GET** `/api/notes`
- **Auth:** HTTP Basic Auth
- **Response (200 OK):**
```json
[
  {
    "id": 1,
    "title": "My First Note",
    "content": "This is the content of my note",
    "ownerUsername": "john_user"
  },
  {
    "id": 2,
    "title": "Second Note",
    "content": "Another note",
    "ownerUsername": "john_user"
  }
]
```

#### Get Note by ID
- **GET** `/api/notes/{id}`
- **Auth:** HTTP Basic Auth
- **Example:** `GET /api/notes/1`
- **Response (200 OK - if owner):**
```json
{
  "id": 1,
  "title": "My First Note",
  "content": "This is the content of my note",
  "ownerUsername": "john_user"
}
```
- **Response (403 Forbidden - if not owner):**
```json
{
  "error": "You don't have permission to access this resource"
}
```
- **Response (404 Not Found - if note doesn't exist):**
```json
{
  "error": "Note not found"
}
```

#### Update Note
- **PUT** `/api/notes/{id}`
- **Auth:** HTTP Basic Auth
- **Content-Type:** `application/json`
- **Body:**
```json
{
  "title": "Updated Title",
  "content": "Updated content"
}
```
- **Response (200 OK - if owner):**
```json
{
  "id": 1,
  "title": "Updated Title",
  "content": "Updated content",
  "ownerUsername": "john_user"
}
```
- **Response (403 Forbidden - if not owner):**
```json
{
  "error": "You don't have permission to access this resource"
}
```

#### Delete Note
- **DELETE** `/api/notes/{id}`
- **Auth:** HTTP Basic Auth
- **Example:** `DELETE /api/notes/1`
- **Response (204 No Content - if successful)**
- **Response (403 Forbidden - if not owner):**
```json
{
  "error": "You don't have permission to access this resource"
}
```

---

### 3️⃣ Admin Endpoints (ROLE_ADMIN - Requires HTTP Basic Auth)

#### Get All Notes (System-wide)
- **GET** `/api/admin/notes`
- **Auth:** HTTP Basic Auth (Admin credentials)
- **Response (200 OK):**
```json
[
  {
    "id": 1,
    "title": "My First Note",
    "content": "This is the content of my note",
    "ownerUsername": "john_user"
  },
  {
    "id": 2,
    "title": "Jane's Task",
    "content": "Complete assignment",
    "ownerUsername": "jane_user"
  }
]
```
- **Response (403 Forbidden - if user role):**
```json
{
  "error": "Access Denied"
}
```

#### Delete Any Note (Admin Override)
- **DELETE** `/api/admin/notes/{id}`
- **Auth:** HTTP Basic Auth (Admin credentials)
- **Example:** `DELETE /api/admin/notes/5`
- **Response (204 No Content - if successful)**
- **Response (404 Not Found - if note doesn't exist):**
```json
{
  "error": "Note not found"
}
```
- **Response (403 Forbidden - if user role):**
```json
{
  "error": "Access Denied"
}
```

---

## 📦 Data Models

### AppUser Entity
```json
{
  "id": "Long (Primary Key)",
  "username": "String (Unique, Not Null)",
  "password": "String (Encrypted, Not Null)",
  "role": "Role Enum (ROLE_USER or ROLE_ADMIN)"
}
```

### Note Entity
```json
{
  "id": "Long (Primary Key)",
  "title": "String (Not Null)",
  "content": "String (Not Null)",
  "ownerUsername": "String (Not Null, Foreign key reference)"
}
```

### RegisterRequest DTO
```json
{
  "username": "String",
  "password": "String"
}
```

### NoteRequest DTO
```json
{
  "title": "String",
  "content": "String"
}
```

### NoteResponse DTO
```json
{
  "id": "Long",
  "title": "String",
  "content": "String",
  "ownerUsername": "String"
}
```

---

## 🔐 Security Architecture

### Authentication
- **Type:** HTTP Basic Authentication
- **Credentials:** Username and password base64-encoded in Authorization header
- **Implementation:** Spring Security with `CustomUserDetailsService`

### Authorization
- **Mechanism:** Role-Based Access Control (RBAC)
- **Roles:** 
  - `ROLE_USER` - Regular users can manage only their own notes
  - `ROLE_ADMIN` - Admins can view and delete any note
- **Implementation:** `@PreAuthorize` annotations on controller methods

### Ownership Validation
- Every note is tied to its creator via `ownerUsername`
- User endpoints verify ownership before allowing modifications
- Admins bypass ownership checks

---

## 🧪 Testing with Postman

A comprehensive Postman collection (`Postman / Postman_Collection.json`) is included with the project. It contains:

### Predefined Test Data
- **Regular Users:** john_user, jane_user
- **Admins:** admin_user, super_admin
- **Sample Notes:** Pre-configured requests for CRUD operations

### Collection Folders
1. **Auth** - User and admin registration
2. **User Notes** - CRUD operations for regular users
3. **Admin Notes** - Admin operations
4. **Error Cases** - Testing authorization failures

### How to Import
1. Open Postman
2. Click `Import` → Select `Postman / Postman_Collection.json`
3. The collection will load with all pre-configured requests
4. Use the Basic Auth credentials provided in each request
5. Execute requests in order to seed data and test functionality

---

## 📋 Key Features

✅ **HTTP Basic Authentication**
- Secure credentials transmission
- Spring Security integration

✅ **Role-Based Access Control**
- Two-level authorization (USER and ADMIN)
- Method-level security with @PreAuthorize

✅ **Ownership-Based Access**
- Users can only access their own notes
- Admins have system-wide access

✅ **JPA Persistence**
- H2 in-memory database
- Spring Data repositories for data access

✅ **Clean Architecture**
- Separation of concerns (Controller, Service, Repository)
- DTOs for API request/response
- Custom exception handling

✅ **Public Registration**
- New users can self-register
- New admins can be registered (Note: In production, this should be restricted)

---

## ⚠️ Important Notes

1. **H2 In-Memory Database:** Data is lost on application restart
2. **Passwords:** Currently stored in plain text (Hash them in production using BCryptPasswordEncoder)
3. **Admin Registration:** Public endpoint (restrict in production)
4. **CORS:** Not configured (add if frontend on different domain)
5. **API Documentation:** Consider adding Swagger/SpringDoc OpenAPI for production

---

## 🔧 Customization & Extension

### Adding New Features
1. Create new entity classes in `entity/` folder
2. Create corresponding repositories in `repository/`
3. Implement business logic in `service/`
4. Create controllers in `controller/` with appropriate `@PreAuthorize` annotations
5. Create DTOs in `dto/` for API contracts

### Enhancing Security
- Replace plain text passwords with BCryptPasswordEncoder
- Add JWT token-based authentication
- Implement CORS configuration
- Add rate limiting
- Implement audit logging

---

## 📝 Common Issues & Troubleshooting

| Issue | Solution |
|-------|----------|
| 401 Unauthorized | Ensure credentials in Basic Auth are correct |
| 403 Forbidden | Check user role and ownership of resource |
| 404 Not Found | Verify note ID exists and belongs to authenticated user |
| Port already in use | Change port in `application.properties` or kill existing process |
| H2 Console not accessible | Ensure `spring.h2.console.enabled=true` in properties |

---

## 📚 Learning Outcomes

After completing this assignment, you will understand:
- REST API design principles with CRUD operations
- HTTP Basic Authentication implementation
- Role-Based Access Control (RBAC) design
- Spring Security configuration and method-level security
- JPA entity relationships and repositories
- Controller, Service, and Repository pattern
- Exception handling in Spring Boot applications
- Testing APIs with Postman

---

## 🎓 Course Information

**Course:** Ostad Java & SpringBoot 3 Backend Web Development

**Module:** 15 - Authentication & Authorization

**Assignment Type:** Practical Implementation

**Duration:** Module 15 Assignment

---

## 📞 Support & References

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [H2 Database](http://www.h2database.com/)
- [Maven Documentation](https://maven.apache.org/)

---

