package com.grayraccoon.OAuth2ServerSample.ws;

import com.grayraccoon.OAuth2ServerSample.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.grayraccoon.OAuth2ServerSample.config.LocalResourceServer.getExtraInfo;

@RestController
@RequestMapping("/ws")
public class UsersWebService {

    @Autowired
    private UserService userService;


    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/secured/hello")
    public String securedHello() {
        return "Secured Hello!";
    }


    @GetMapping("/authenticated/helloUser")
    public String authenticatedHelloUser(Authentication authentication) {
        Map<String,Object> extraInfo = getExtraInfo(authentication);
        String userId = (String) extraInfo.get("userId");
        return String.format("Authenticated Hello %s -> %s !", userId, authentication.getName());
    }


}
