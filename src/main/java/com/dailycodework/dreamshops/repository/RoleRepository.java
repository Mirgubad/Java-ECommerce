package com.dailycodework.dreamshops.repository;

import com.dailycodework.dreamshops.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(String name);
    Role findByName(String roleUser);
}
