package com.polstat.perpustakaan.repository;

import com.polstat.perpustakaan.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);
}
