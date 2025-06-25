# 🚀 Spring Boot REST APIs: Multi-Project Repository

![Java](https://img.shields.io/badge/Java-17%2B-blue?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot)
![Maven](https://img.shields.io/badge/Maven-3.6%2B-orange?logo=apachemaven)
![IDE](https://img.shields.io/badge/IDE-IntelliJ%20IDEA%20%7C%20VS%20Code-blueviolet?logo=jetbrains)
![Status](https://img.shields.io/badge/Status-Educational%20Demo-lightgrey)

---

## 📚 About This Repository

This repository contains three independent Java Spring Boot projects, each designed to demonstrate or implement different REST API functionalities and backend features. Each project is self-contained with its own Maven configuration and source code structure.

---

## 👤 Author
- **Adrian Talonia**

---

## 🛠️ Technologies Used
- Java 17+
- Spring Boot 3.x
- Maven 3.6+
- H2 Database (for employee project)
- RESTful APIs
- MySQL

---

## 💻 Recommended IDEs
- IntelliJ IDEA
- Visual Studio Code

---

## 🏷️ Tags
`#Java` `#SpringBoot` `#RESTAPI` `#Maven` `#Backend` `#CRUD` `#Educational` `#Demo` `#IntelliJ` `#VSCode`

---

## Projects Overview

### 1. project-books
A Spring Boot application focused on managing books. It likely includes features such as:
- Book CRUD operations (Create, Read, Update, Delete)
- RESTful API endpoints
- Example domain: library or book store management
- Configuration via `application.properties`

**Main folders:**
- `src/main/java/com/atrdev/projectbooks/` — Java source code
- `src/main/resources/` — Application properties and static resources
- `pom.xml` — Maven build configuration

---

### 2. project-employees
A Spring Boot application for employee management. Key features may include:
- Employee CRUD operations
- RESTful API endpoints
- Example domain: HR or company employee management
- Uses `application.yml` for configuration
- Includes a sample H2 database in the `data/` folder

**Main folders:**
- `src/main/java/com/atrdev/projectemployees/` — Java source code
- `src/main/resources/` — Application configuration and resources
- `data/` — Embedded H2 database files
- `pom.xml` — Maven build configuration

---

### 3. todo-project
A Spring Boot application for managing to-do tasks. Features may include:
- Task CRUD operations
- RESTful API endpoints
- Example domain: personal or team task management
- Configuration via `application.yml`

**Main folders:**
- `src/main/java/com/atrdev/todoproject/` — Java source code
- `src/main/resources/` — Application configuration and resources
- `pom.xml` — Maven build configuration

---

## Common Structure
- Each project uses Maven for build and dependency management.
- Standard Spring Boot project structure: `src/main/java`, `src/main/resources`, `src/test/java`.
- Each project is independent and can be run separately using its own `mvnw` script.

## How to Run
1. Navigate to the desired project folder (e.g., `project-books`).
2. Run the application using Maven Wrapper:
   ```bash
   ./mvnw spring-boot:run
   ```
3. Access the API endpoints as described in each project's documentation or code.

---

## Requirements
- Java 17 or later (recommended)
- Maven 3.6+

---

## License
This repository is for educational and demonstration purposes.

