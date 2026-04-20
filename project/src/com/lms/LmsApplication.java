package com.lms;

import com.lms.controller.CourseController;
import com.lms.controller.UserController;
import com.lms.domain.course.Course;
import com.lms.domain.lesson.Lesson;
import com.lms.domain.user.Admin;
import com.lms.domain.user.Instructor;
import com.lms.domain.user.Student;
import com.lms.repository.impl.InMemoryCourseRepository;
import com.lms.repository.impl.InMemoryUserRepository;
import com.lms.repository.interfaces.CourseRepository;
import com.lms.repository.interfaces.UserRepository;
import com.lms.service.course.CourseService;
import com.lms.service.course.CourseServiceImpl;
import com.lms.service.user.UserService;
import com.lms.service.user.UserServiceImpl;

/**
 * Central composition root for the LMS backend.
 * <p>
 * It wires infrastructure and application layers and provides reusable startup helpers.
 */
public class LmsApplication {
    private final UserController userController;
    private final CourseController courseController;

    public LmsApplication(UserController userController, CourseController courseController) {
        this.userController = userController;
        this.courseController = courseController;
    }

    /**
     * Creates the default runtime graph using in-memory repositories.
     */
    public static LmsApplication createDefault() {
        // Infrastructure layer: repositories.
        UserRepository userRepository = new InMemoryUserRepository();
        CourseRepository courseRepository = new InMemoryCourseRepository();

        // Application layer: business services.
        UserService userService = new UserServiceImpl(userRepository);
        CourseService courseService = new CourseServiceImpl(courseRepository, userRepository);

        // Interface layer: controllers exposed to the web server.
        return new LmsApplication(new UserController(userService), new CourseController(courseService));
    }

    /**
     * Seeds realistic sample data for demos and manual testing.
     */
    public void seedDemoData() {
        // Users
        Student studentA = new Student("stu-100", "Ahmad Student", "ahmad.student@lms.com");
        Student studentB = new Student("stu-101", "Lina Student", "lina.student@lms.com");
        Student studentC = new Student("stu-102", "Omar Student", "omar.student@lms.com");
        Instructor instructorA = new Instructor("ins-200", "Sara Instructor", "sara.instructor@lms.com");
        Instructor instructorB = new Instructor("ins-201", "Nour Instructor", "nour.instructor@lms.com");
        Admin admin = new Admin("adm-300", "Maya Admin", "maya.admin@lms.com");

        userController.createUser(studentA);
        userController.createUser(studentB);
        userController.createUser(studentC);
        userController.createUser(instructorA);
        userController.createUser(instructorB);
        userController.createUser(admin);

        // Courses
        Course courseA = new Course("crs-10", "Design Patterns 101", "A foundational course for object-oriented patterns.");
        Course courseB = new Course("crs-11", "Java Backend Fundamentals", "Core backend concepts for enterprise Java systems.");
        Course courseC = new Course("crs-12", "Database Essentials", "Practical relational modeling and SQL workflows.");

        courseController.createCourse(courseA);
        courseController.createCourse(courseB);
        courseController.createCourse(courseC);

        // Instructor assignments
        courseController.assignInstructor("crs-10", "ins-200");
        courseController.assignInstructor("crs-11", "ins-201");
        courseController.assignInstructor("crs-12", "ins-200");

        // Lessons
        courseController.addLesson("crs-10", new Lesson("les-1", "Introduction", "Welcome to the LMS foundation module."));
        courseController.addLesson("crs-10", new Lesson("les-2", "SOLID Principles", "Learn how SOLID improves maintainability."));
        courseController.addLesson("crs-11", new Lesson("les-3", "Layered Architecture", "Understand repository, service, and controller layers."));
        courseController.addLesson("crs-12", new Lesson("les-4", "Normalization Basics", "How to model consistent and scalable data."));

        // Enrollments
        courseController.enrollStudent("crs-10", "stu-100");
        courseController.enrollStudent("crs-10", "stu-101");
        courseController.enrollStudent("crs-11", "stu-101");
        courseController.enrollStudent("crs-11", "stu-102");
        courseController.enrollStudent("crs-12", "stu-100");
        courseController.enrollStudent("crs-12", "stu-102");
    }

    /**
     * Exposes user use-cases to adapters (e.g., web server).
     */
    public UserController getUserController() {
        return userController;
    }

    /**
     * Exposes course use-cases to adapters (e.g., web server).
     */
    public CourseController getCourseController() {
        return courseController;
    }
}
