package com.shubham.fitness;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
@Configuration
public class GatewaySecurity {
 @Bean SecurityWebFilterChain security(ServerHttpSecurity http){
  return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
   .authorizeExchange(e->e.pathMatchers("/actuator/health").permitAll().anyExchange().authenticated())
   .oauth2ResourceServer(o->o.jwt(j->{})).build();
 }
}