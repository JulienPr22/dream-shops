package com.julienprr.dreamshops.repository;

import com.julienprr.dreamshops.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByName(String role);
}
