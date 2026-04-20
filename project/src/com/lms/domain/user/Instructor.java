package com.lms.domain.user;

import com.lms.common.enums.Role;

public class Instructor extends AbstractUser {
    public Instructor(String id, String name, String email) {
        super(id, name, email, Role.INSTRUCTOR);
    }
}
