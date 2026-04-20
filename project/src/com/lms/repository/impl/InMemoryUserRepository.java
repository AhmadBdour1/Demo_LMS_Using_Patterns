package com.lms.repository.impl;

import com.lms.common.enums.Role;
import com.lms.domain.user.User;
import com.lms.repository.interfaces.UserRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> users = new LinkedHashMap<>();

    @Override
    public User save(User entity) {
        users.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void deleteById(String id) {
        users.remove(id);
    }

    @Override
    public boolean existsById(String id) {
        return users.containsKey(id);
    }

    @Override
    public List<User> findByRole(Role role) {
        return users.values().stream()
                .filter(user -> user.getRole() == role)
                .toList();
    }
}
