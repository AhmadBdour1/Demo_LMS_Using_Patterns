package com.lms.domain.user;

import com.lms.common.enums.Role;

public class Admin extends AbstractUser {
    public Admin(String id, String name, String email) {
        super(id, name, email, Role.ADMIN);
    }
}
