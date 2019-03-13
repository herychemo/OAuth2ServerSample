package com.grayraccoon.OAuth2ClientSample.conf;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean("authenticatedRestTemplate")
    public RestTemplate authenticatedRestTemplate(
            OAuth2AuthorizedClientService clientService) {
        return new RestTemplateBuilder().interceptors((httpRequest, bytes, clientHttpRequestExecution) -> {

            OAuth2AuthenticationToken token =
                    (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

            OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
                    token.getAuthorizedClientRegistrationId(),
                    token.getName()
            );

            httpRequest.getHeaders().add(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", client.getAccessToken().getTokenValue()));
            return clientHttpRequestExecution.execute(httpRequest, bytes);
        }).build();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2Login();
    }

}
