package com.grayraccoon.OAuth2ServerSample.ws;

import com.grayraccoon.OAuth2ServerSample.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/ws")
public class UsersWebService {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenStore tokenStore;


    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/secured/hello")
    public String securedHello() {
        return "Secured Hello!";
    }

    @GetMapping("/authenticated/helloUser")
    public String authenticatedHelloUser(OAuth2Authentication authentication) {
        Map<String,Object> extraInfo = getExtraInfo(authentication);

        String userId = (String) extraInfo.get("userId");
        return String.format("Authenticated Hello %s -> %s !", userId, authentication.getName());
    }


    public Map<String, Object> getExtraInfo(OAuth2Authentication auth) {
        OAuth2AuthenticationDetails details
                = (OAuth2AuthenticationDetails) auth.getDetails();
        OAuth2AccessToken accessToken = tokenStore
                .readAccessToken(details.getTokenValue());
        return accessToken.getAdditionalInformation();
    }

}
