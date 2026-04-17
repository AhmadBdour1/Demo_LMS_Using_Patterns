package com.lms.domain.user;

import com.lms.common.enums.Role;

import java.util.Objects;

public abstract class AbstractUser implements User {
    private final String id;
    private final String name;
    private final String email;
    private final Role role;

    protected AbstractUser(String id, String name, String email, Role role) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.email = Objects.requireNonNull(email, "email cannot be null");
        this.role = Objects.requireNonNull(role, "role cannot be null");
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
