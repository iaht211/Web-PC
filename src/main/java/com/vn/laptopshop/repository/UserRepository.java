package com.vn.laptopshop.repository;

import org.springframework.data.repository.CrudRepository;

import com.vn.laptopshop.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User save(User user);
}
