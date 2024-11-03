package com.nqt.cs3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nqt.cs3.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
