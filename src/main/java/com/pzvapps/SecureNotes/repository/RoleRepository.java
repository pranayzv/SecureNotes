package com.pzvapps.SecureNotes.repository;

import com.pzvapps.SecureNotes.model.AppRole;
import com.pzvapps.SecureNotes.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);

}