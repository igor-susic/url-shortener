package com.demo.urlshortener.config;

import com.demo.urlshortener.services.impl.UserDetailsServiceImpl;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private DataSource dataSource;

    ApplicationSecurity(UserDetailsServiceImpl userDetailsService, DataSource dataSource) {
        this.userDetailsService = userDetailsService;
        this.dataSource = dataSource;
    }

    /**
     * We use custom defined UserDataService as a way to provide User back for authentification, also dataSource is
     * defined by the spring boot, and as we are using H2 and JPA, we need jdbc. Custom query strings are used as our database
     * schema does not follow spring boot default schema.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder())
                .and()
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT ACCOUNT_ID, PASSWORD FROM ACCOUNTS WHERE ACCOUNT_ID = ?")
                .authoritiesByUsernameQuery("SELECT A.ACCOUNT_ID, R.ROLE FROM ROLES AS R LEFT JOIN ACCOUNTS AS A ON R.ID = A.ROLE_ID WHERE A.ACCOUNT_ID = ?");
    }

    /**
     * Here we enable http basic auth, disable csrf as we dont use forms, and also as this is API, it is stateless,
     * there is no need for user session to remember the user is logged in.
     *
     * Every request that comes unauthorized will be handled by the authenticationEntryPoint()
     *
     * Better option for API is JWT token for auth.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic();

        http
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .exceptionHandling()
                .authenticationEntryPoint((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setContentType("application/json;charset=UTF8");
                    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    try {
                        httpServletResponse.getWriter().write(new JSONObject()
                                .put("message", "Access denied")
                                .put("status", HttpStatus.UNAUTHORIZED)
                                .put("uri", httpServletRequest.getRequestURI())
                                .toString());
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    /**
     * h2-console is needed only for development mode
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/account", "/h2-console/*");
    }
}
