package com.lms.domain.user;

import com.lms.common.enums.Role;

public class Student extends AbstractUser {
    public Student(String id, String name, String email) {
        super(id, name, email, Role.STUDENT);
    }
}
