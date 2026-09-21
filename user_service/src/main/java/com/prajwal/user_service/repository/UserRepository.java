package com.prajwal.user_service.repository;

import com.prajwal.user_service.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Long> {
}
