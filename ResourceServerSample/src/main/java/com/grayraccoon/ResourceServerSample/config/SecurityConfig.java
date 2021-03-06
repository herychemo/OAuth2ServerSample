package com.grayraccoon.ResourceServerSample.config;

import com.grayraccoon.ResourceServerSample.config.beans.CustomAccessTokenConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;


@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.signing-key}")
    private String signingKey;

    @Autowired
    private CustomAccessTokenConverter customAccessTokenConverter;


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }




    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(signingKey);
        converter.setAccessTokenConverter(customAccessTokenConverter);
        return converter;
    }



    /*
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
    @Bean
    @Primary
    //Making this primary to avoid any accidental duplication with another token service instance of the same name
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }*/

    @Bean
    @Primary
    public RemoteTokenServices tokenServices() {
        RemoteTokenServices tokenService = new RemoteTokenServices();

        tokenService.setCheckTokenEndpointUrl( // OAuth2 Server Url
                "http://localhost:9887/oauth/check_token");

        tokenService.setAccessTokenConverter(customAccessTokenConverter);

        // Client Id n Secret from oauth_client_details table
        tokenService.setClientId("default_oauth2_client_id");
        tokenService.setClientSecret("QWERTY74gray75raccoon634");

        return tokenService;
    }

}
