# API Documentation

Base URL: `http://localhost:8080`

## Static Resources
- `GET /` -> `index.html`
- `GET /styles.css`
- `GET /app.js`

## Users
### GET /api/users
Returns all users.

### POST /api/users
Creates a user using form URL encoded body.

Fields:
- `id`
- `name`
- `email`
- `role` (`STUDENT`, `INSTRUCTOR`, `ADMIN`)

Example body:
`id=stu-999&name=Ali&email=ali%40lms.com&role=STUDENT`

## Courses
### GET /api/courses
Returns all courses with lessons and enrolled students.

### POST /api/courses
Creates a course.

Fields:
- `id`
- `title`
- `description`

### POST /api/courses/complete
Creates a fully composed course through the Builder flow.

Fields:
- `id`
- `title`
- `description`
- `instructorId` (must be an `INSTRUCTOR`)
- `lessonTitles` (pipe-separated titles, e.g. `Intro|SOLID|Builder Basics`)

Behavior:
- Rejects incomplete composition.
- Requires at least one lesson.
- Requires a valid instructor.
- Prevents partially initialized course creation in this flow.

### POST /api/courses/{courseId}/assign-instructor
Assigns an instructor to an existing course.

Fields:
- `instructorId`

### POST /api/courses/{courseId}/enroll-student
Enrolls a student in an existing course.

Fields:
- `studentId`

## Error Handling
- `400` for validation/business errors
- `404` for unknown endpoints/static files
- `405` for unsupported HTTP methods
