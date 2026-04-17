#  Project Work Plan: LMS Design Patterns Project

This document serves as the official team reference for distributing programming and documentation tasks, ensuring 100% coverage of all project files and clear alignment 



**Project Introduction and Objectives**

This project aims to develop a comprehensive Learning Management System (LMS) based on software engineering principles and advanced design patterns, specifically the Builder Pattern.

---

## 1. Project Structure Overview

The following tree structure illustrates the project's organization, which has been used to allocate files across the four team members:

```
Demo_LMS_UsingPatterns/
├── src/com/lms/
│   ├── patterns/builder/          # Design Pattern Implementation (Builder)
│   ├── domain/                    # Data Objects (Course, User, Lesson)
│   ├── repository/                # Data Access Layer (Interfaces & Impl)
│   ├── service/                   # Business Logic Layer (Services)
│   ├── controller/                # API and Integration Layer
│   ├── common/                    # Shared Utilities (Enums, Exceptions)
│   ├── web/                       # Web Infrastructure (Utils, WebServer)
│   ├── LmsApplication.java        # Application Configuration
│   └── Main.java                  # System Entry Point
├── web/                           # Frontend (HTML, CSS, JS)
└── docs/                          # Technical Documentation & UML Diagrams
```

---

## 2. Detailed Member Roles & File Allocation (Full Paths)

### **Member 1: Backend Pattern Architect**

(Ahmad Bdour)

**Responsibility:** Implementation of the Builder Pattern and core data infrastructure.

| Full File Path | Programming Function |
| --- | --- |
| `src/com/lms/patterns/builder/CourseBuilder.java` | Defines the Builder Interface |
| `src/com/lms/patterns/builder/DefaultCourseBuilder.java` | Concrete Builder implementation |
| `src/com/lms/domain/course/Course.java` | Primary Course entity |
| `src/com/lms/domain/lesson/Lesson.java` | Lesson entities linked to Courses |
| `src/com/lms/repository/interfaces/CourseRepository.java` | Course Repository Interface |
| `src/com/lms/repository/interfaces/UserRepository.java` | User Repository Interface |
| `src/com/lms/repository/interfaces/CrudRepository.java` | Base CRUD Interface |
| `src/com/lms/repository/impl/InMemoryCourseRepository.java` | In-memory Course storage implementation |
| `src/com/lms/repository/impl/InMemoryUserRepository.java` | In-memory User storage implementation |

- **Additional Deliverables:** Problem Analysis & Trade-off Report (C2), GitHub Repo Setup & Project Board (C1).

---

### **Member 2: Backend Logic & Service Developer**

(Ahmad Shomar)

**Responsibility:** Business logic management, user roles, and exception handling.

| Full File Path | Programming Function |
| --- | --- |
| `src/com/lms/service/course/CourseService.java` | Course Service Interface |
| `src/com/lms/service/course/CourseServiceImpl.java` | Course Service logic implementation |
| `src/com/lms/service/user/UserService.java` | User Service Interface |
| `src/com/lms/service/user/UserServiceImpl.java` | User Service logic implementation |
| `src/com/lms/domain/user/User.java` | Base User Interface |
| `src/com/lms/domain/user/AbstractUser.java` | Abstract class for shared user properties |
| `src/com/lms/domain/user/Admin.java`, `Instructor.java`, `Student.java` | Specific User types and permissions |
| `src/com/lms/common/enums/Role.java` | User Role definitions (Enums) |
| `src/com/lms/common/exceptions/EntityNotFoundException.java` | "Entity Not Found" exception |
| `src/com/lms/common/exceptions/ValidationException.java` | "Data Validation Failed" exception |

- **Additional Deliverables:** UML Class Diagrams (C2), Code Review (C1).

---

### **Member 3: Full-Stack Integration Developer**

(Rayan Sawalha)

**Responsibility:** Bridging Frontend and Backend, data processing, and API control.

| Full File Path | Programming Function |
| --- | --- |
| `src/com/lms/controller/CourseController.java` | Course API Controller |
| `src/com/lms/controller/UserController.java` | User API Controller |
| `src/com/lms/web/JsonUtil.java` | JSON data conversion utilities |
| `src/com/lms/web/FormUtil.java` | Web form processing utilities |
| `web/app.js` | Frontend logic and user interaction |

- **Additional Deliverables:** Professional README.md file (C1), GitHub Contribution Log (C1).

---

### **Member 4: UI Designer & Operations Engineer**

(Heyam Badran)

**Responsibility:** Visual design, system startup, and final project delivery.

| Full File Path | Programming Function |
| --- | --- |
| `web/index.html` | Core UI structure |
| `web/styles.css` | Aesthetic design and styling (CSS) |
| `src/com/lms/web/LmsWebServer.java` | Embedded web server configuration |
| `src/com/lms/LmsApplication.java` | LMS application configuration |
| `src/com/lms/Main.java` | Main entry point for system startup |
| `docs/` (Entire Folder) | Organizing existing technical docs and UMLs |

- **Additional Deliverables:** Final Presentation Slide Deck (C1), Demo Video (C1), Final Project Quality Assurance (C1).

---

## 3. Final Responsibility Matrix (Audit Summary)

| File Name | Short Path | Responsible Member | Grading Criteria |
| --- | --- | --- | --- |
| `CourseBuilder.java` | `src/.../patterns/builder/` | **Member 1** | C2 - Pattern Selection |
| `CourseServiceImpl.java` | `src/.../service/course/` | **Member 2** | C2 - Implementation |
| `CourseController.java` | `src/.../controller/` | **Member 3** | C1 - Team Output |
| `index.html` | `web/` | **Member 4** | C1 - Team Output |
| `app.js` | `web/` | **Member 3** | C1 - Individual Cont. |
| `UML_Digrams/` | `docs/` | **Member 2** | C2 - UML Diagrams |
| `README.md` | Root | **Member 3** | C2 - Documentation |

---




