package com.reesedevelopment.greatneckzmanim.admin.users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Integer> {
    AppUser findByUsername(String username);
}