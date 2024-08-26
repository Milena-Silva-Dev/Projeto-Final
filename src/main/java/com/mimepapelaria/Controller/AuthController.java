package com.mimepapelaria.Controller;

import com.mimepapelaria.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtService jwtService;

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@RequestParam String email, @RequestParam String senha) {
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, senha)
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String role = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

    String jwt = jwtService.generateToken(email, role);

    Map<String, String> response = new HashMap<>();
    response.put("accessToken", jwt);
    response.put("tokenType", "Bearer");

    return ResponseEntity.ok(response);
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logoutUser() {
    return ResponseEntity.ok().body("Logout successful");
  }
}
