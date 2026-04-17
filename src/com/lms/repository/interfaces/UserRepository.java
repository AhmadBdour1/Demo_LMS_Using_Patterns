package com.lms.repository.interfaces;

import com.lms.common.enums.Role;
import com.lms.domain.user.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, String> {
    List<User> findByRole(Role role);
}
