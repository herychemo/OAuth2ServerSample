package com.grayraccoon.OAuth2ClientSample.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;

@RestController
public class SomeResourceWebService {

    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(@Qualifier("authenticatedRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(value = {"/", "/me"})
    public String getPrincipal(Principal principal){
        return principal.getName();
    }

    @GetMapping("/hello")
    public String getExternalHello() {
        String externalResourceUrl = "http://localhost:9882/ws/authenticated/helloUser";

        ResponseEntity<String> externalHello =
                this.restTemplate.getForEntity(externalResourceUrl, String.class);

        if ( externalHello.getStatusCode().is2xxSuccessful() ) {
            return externalHello.getBody();
        }

        return externalHello.getStatusCode().getReasonPhrase();
    }

}
