package se.newton.sysjg3.chessapi.security;



import org.springframework.context.annotation.Configuration;


import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated();


    }

    public void configure(WebSecurity web)
            throws Exception {
        web.ignoring().antMatchers(HttpMethod.POST, "/api/players");
        web.ignoring().antMatchers(HttpMethod.POST, "/api/login");
    }
}
