package net.daw.alist.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import net.daw.alist.security.jwt.JwtRequestFilter;

@Configuration
@Order(1)
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtRequestFilter jwtRequestFilter;

  @Autowired
  private PasswordEncoder passwordEncoder;


  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

  //Expose AuthenticationManager as a Bean to be used in other services
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.antMatcher("/api/**");

    // URLs that need authentication to access to it
    http
        .authorizeRequests().antMatchers(HttpMethod.POST, "/api/ajax/**").hasRole("USER")
          .antMatchers(HttpMethod.PUT, "/api/auth/**").hasAnyRole("USER", "ADMIN")
          //.antMatchers(HttpMethod.POST, "/api/auth/**").hasAnyRole("USER", "ADMIN")
          .antMatchers(HttpMethod.POST, "/api/comment/**").hasAnyRole("ADMIN", "USER")
          .antMatchers(HttpMethod.DELETE, "/api/comment/**").hasAnyRole("ADMIN", "USER")
          .antMatchers(HttpMethod.POST, "/api/post/**").hasAnyRole("ADMIN", "USER")
          .antMatchers(HttpMethod.DELETE, "/api/post/**").hasRole("ADMIN")
          .antMatchers(HttpMethod.POST, "/api/topic/**").hasRole("ADMIN")
          .antMatchers(HttpMethod.DELETE, "/api/topic/**").hasRole("ADMIN")
          .antMatchers(HttpMethod.PUT, "/api/user/**").hasRole("ADMIN");


    // Other URLs can be accessed without authentication
    http.authorizeRequests().anyRequest().permitAll();

    // Disable CSRF protection (it is difficult to implement in REST APIs)
    http.csrf().disable();

    // Disable Http Basic Authentication
    http.httpBasic().disable();

    // Disable Form login Authentication
    http.formLogin().disable();

    // Avoid creating session
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // Add JWT Token filter
    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

  }
}
