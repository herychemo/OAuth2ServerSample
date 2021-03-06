package com.grayraccoon.OAuth2ServerSample.services;

import com.grayraccoon.OAuth2ServerSample.data.postgres.domain.Roles;
import com.grayraccoon.OAuth2ServerSample.data.postgres.domain.Users;
import com.grayraccoon.OAuth2ServerSample.data.postgres.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {


    @Autowired
    UsersRepository usersRepository;


    public Users findUserById(String userId) {
        return this.findUserById(UUID.fromString(userId));
    }
    public Users findUserById(UUID userId) {
        return usersRepository.getOne(userId);
    }

    public Users findByUsernameOrEmail(String query) {
        Users u;
        u = usersRepository.findByUsername(query);
        if (u == null) {
            u = usersRepository.findByEmail(query);
        }
        /*
        if (u == null) {
            throw new EntityNotFoundException(query);
        }
        */
        return u;
    }



    @Transactional(readOnly = true)
    public List<Roles> getRolesFor(UUID user_id) {
        Users u = usersRepository.findByUserId(user_id);
        return (u == null) ?
                new ArrayList<>() :
                new ArrayList<>(u.getRolesCollection());
    }


}
