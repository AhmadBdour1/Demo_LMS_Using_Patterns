# Demo LMS Using Patterns

A clean Java backend foundation for a modular Learning Management System (LMS).

## Overview
This project provides a production-style baseline backend architecture that is intentionally prepared for future design pattern integration (Abstract Factory, Builder, Composite, Flyweight, Observer, Chain of Responsibility).

The current implementation includes:
- User management (Student, Instructor, Admin)
- Course management
- Lesson model
- Builder Pattern for strict complete-course creation
- In-memory repositories
- Service and controller layers
- Lightweight HTTP server (no frameworks)
- Basic frontend for manual interaction

## Tech Stack
- Java 21
- Built-in `com.sun.net.httpserver.HttpServer`
- HTML/CSS/JavaScript frontend
- In-memory persistence (`Map`, `List`)

## Prerequisites
- **Java Development Kit (JDK) 21** or newer. You can download it from [Oracle](https://www.oracle.com/java/technologies/downloads/) or use OpenJDK.

## Quick Start

### For macOS / Linux

1.  **Create output directory:**
    ```bash
    mkdir -p out
    ```
2.  **Compile Java source files:**
    ```bash
    find src -name "*.java" > sources.txt
    javac -d out @sources.txt
    ```
3.  **Run the application:**
    ```bash
    java -cp out com.lms.Main
    ```

### For Windows (PowerShell)

1.  **Create output directory:**
    ```powershell
    mkdir out
    ```
2.  **Compile Java source files:**
    ```powershell
    $javaFiles = Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
    javac -d out $javaFiles
    ```
3.  **Run the application:**
    ```powershell
    java -cp out com.lms.Main
    ```

### For Windows (Command Prompt - CMD)

1.  **Create output directory:**
    ```cmd
    mkdir out
    ```
2.  **Compile Java source files:**
    ```cmd
    dir /s /B src\*.java > sources.txt
    javac -d out @sources.txt
    ```
3.  **Run the application:**
    ```cmd
    java -cp out com.lms.Main
    ```

After running the application, open your web browser and navigate to: [http://localhost:8080](http://localhost:8080)

## Documentation Index
- `docs/ARCHITECTURE.md`
- `docs/PROJECT-STRUCTURE.md`
- `docs/API.md`
- `docs/RUNNING.md`

## Design Notes
- Domain objects remain simple and focused.
- Business logic lives in services, not controllers.
- Repositories are interface-driven to allow persistence replacement later.
- Web layer is an adapter over the same controllers/services.

## Builder Pattern Impact (Before vs After)
- Before: course setup happened across multiple operations, which allowed partially initialized courses to exist.
- After: `POST /api/courses/complete` uses `DefaultCourseBuilder` to enforce required composition before persistence.
- Enforced in builder flow:
  - non-blank id/title/description
  - valid instructor
  - at least one lesson
