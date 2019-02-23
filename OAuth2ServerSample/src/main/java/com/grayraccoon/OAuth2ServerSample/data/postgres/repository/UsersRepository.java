package com.grayraccoon.OAuth2ServerSample.data.postgres.repository;

import com.grayraccoon.OAuth2ServerSample.data.postgres.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {

    Users findByUsername(String s);

    Users findByEmail(String email);

    Users findByUserId(UUID id);

}

