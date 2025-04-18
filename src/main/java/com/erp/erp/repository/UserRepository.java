package com.erp.erp.repository;


import com.erp.erp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT e FROM User e WHERE e.department.id = :id")
    List<User> findUserByDepartmentId(@Param("id") String id);
    Optional<User> findByEmail(String email);;

}
