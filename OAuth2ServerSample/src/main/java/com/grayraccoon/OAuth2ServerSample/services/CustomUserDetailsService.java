package com.grayraccoon.OAuth2ServerSample.services;

import com.grayraccoon.OAuth2ServerSample.data.postgres.domain.CustomUserDetails;
import com.grayraccoon.OAuth2ServerSample.data.postgres.domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users user
                = userService.findByUsernameOrEmail(s);
        if (user == null) {
            throw new UsernameNotFoundException("Username Not found");
        }
        CustomUserDetails userDetails = new CustomUserDetails(user);

        userDetails.setRolesCollection(
                userService.getRolesFor(userDetails.getUserId())
        );
        return userDetails;
    }
}
