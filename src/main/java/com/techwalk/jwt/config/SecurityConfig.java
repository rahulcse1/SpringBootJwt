package com.techwalk.jwt.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.techwalk.jwt.model.Roles;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    var filter = new AuthenticationFilter(authenticationManager());
    filter.setFilterProcessesUrl("/api/login");
    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.authorizeRequests().antMatchers("/api/login/**", "/api/token/refresh/**").permitAll();
    http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/users/**").hasAnyAuthority(Roles.ROLE_ADMIN.toString());
    http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/roles/**").hasAnyAuthority(Roles.ROLE_ADMIN.toString());
    http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/user/save/**").hasAnyAuthority(Roles.ROLE_ADMIN.toString());
    http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/role/save/**").hasAnyAuthority(Roles.ROLE_ADMIN.toString());
    http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/user/addRoleToUser/**").hasAnyAuthority(Roles.ROLE_ADMIN.toString());
    http.authorizeRequests().anyRequest().authenticated();
    http.addFilter(filter);
  }

  @Bean
  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }
}
