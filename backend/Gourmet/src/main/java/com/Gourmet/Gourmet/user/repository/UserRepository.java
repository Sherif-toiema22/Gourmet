package com.Gourmet.Gourmet.user.repository;
import com.Gourmet.Gourmet.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


    boolean existsByEmail(String email);
}