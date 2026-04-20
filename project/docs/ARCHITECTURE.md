# Architecture Documentation

## Architectural Style
The project follows a layered architecture aligned with clean architecture principles:

1. **Domain Layer**
   - Core business entities: `User`, `Course`, `Lesson`
   - No framework dependencies

2. **Repository Layer**
   - Interfaces define persistence contracts
   - Current implementations are in-memory (`InMemoryUserRepository`, `InMemoryCourseRepository`)

3. **Service Layer**
   - Application/business rules
   - Validation and use-case orchestration

4. **Controller Layer**
   - Thin use-case entry points
   - Delegates logic to services

5. **Web Adapter Layer**
   - `LmsWebServer` maps HTTP endpoints to controllers
   - Serves static frontend assets

6. **Bootstrap Layer**
   - `LmsApplication` wires dependencies
   - `Main` starts the runtime

## Why This Structure
- Keeps business logic isolated from infrastructure
- Supports swapping persistence strategy without touching use-cases
- Enables design-pattern extensions with minimal refactoring

## Future Pattern Integration Points
- **Abstract Factory**: instantiate user/course variants by context
- **Builder**: assemble complex course definitions
- **Composite**: hierarchical lesson/content tree
- **Flyweight**: reusable content fragments
- **Observer**: domain events/notifications
- **Chain of Responsibility**: validation/processing pipeline

## Builder Pattern (Applied)
The project now includes a strict Builder implementation under `com.lms.patterns.builder`:

- `CourseBuilder` (contract)
- `DefaultCourseBuilder` (strict validation + build)

### Problem Before
- Course creation was split into multiple calls:
  1) create course
  2) assign instructor
  3) add lessons
- This allowed temporary invalid states (course without instructor/lessons).

### Solution After
- New use-case: `createCompleteCourse(...)` builds and validates the full aggregate before persisting.
- New endpoint: `POST /api/courses/complete`.
- Validation now happens at build time:
  - non-blank id/title/description
  - instructor is required
  - at least one lesson is required

### Why Builder over Abstract Factory here
- The core problem was **complex object construction validity**, not switching between product families.
- Builder gives a stronger before/after impact for this LMS phase, while staying beginner-friendly.
