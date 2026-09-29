package com.glooneltharion.hospital.repositories;


import com.glooneltharion.hospital.models.User;
import com.glooneltharion.hospital.models.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByRole(Role role);
    @Query("SELECT u FROM User u WHERE u.role = 'DOCTOR'")
    List<User> findDoctors();
}
