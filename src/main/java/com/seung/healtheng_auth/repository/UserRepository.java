package com.seung.healtheng_auth.repository;

import com.seung.healtheng_auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
