package com.lms.domain.user;

import com.lms.common.enums.Role;

public interface User {
    String getId();

    String getName();

    String getEmail();

    Role getRole();
}

