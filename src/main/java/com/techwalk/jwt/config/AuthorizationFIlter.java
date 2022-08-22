package com.techwalk.jwt.config;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class AuthorizationFIlter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    if (request.getServletPath().equals("/api/login")) {
      filterChain.doFilter(request, response);
    } else {
      var authHeader = request.getHeader(AUTHORIZATION);
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        try {
          var token = authHeader.substring("Bearer ".length());
          var algo = Algorithm.HMAC256("secret".getBytes());
          var verifier = JWT.require(algo).build();
          var decode = verifier.verify(token);
          var username = decode.getSubject();
          var roles = decode.getClaim("roles").asArray(String.class);
          var authorities = new ArrayList<SimpleGrantedAuthority>();
          Stream.of(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
          var authToken = new UsernamePasswordAuthenticationToken(username, null);
          SecurityContextHolder.getContext().setAuthentication(authToken);
        } catch (IllegalArgumentException e) {
          log.error(e.getMessage());
        } catch (JWTVerificationException e) {
          log.error("not a valid token");
        }
      } else {
        filterChain.doFilter(request, response);
      }
    }
  }
}
