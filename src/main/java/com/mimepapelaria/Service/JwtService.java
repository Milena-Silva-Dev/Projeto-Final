package com.mimepapelaria.Service;

import com.mimepapelaria.Model.Usuario;
import com.mimepapelaria.Repository.UsuarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtService {

  private final String SECRET_KEY = Base64.getEncoder().encodeToString(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded());

  @Autowired
  private CustomUserDetailsService userDetailsService;

  @Autowired
  private UsuarioRepository usuarioRepository;

  public String generateToken(String email, String role) {
    return Jwts.builder()
            .setSubject(email)
            .claim("role", role)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 864_000_00))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
  }

  public boolean isValidToken(String token) {
    try {
      Jwts.parser()
              .setSigningKey(SECRET_KEY)
              .parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      System.err.println("Invalid token: " + e.getMessage());
      return false;
    }
  }

  public Authentication getAuthentication(String token) {
    Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    String email = claims.getSubject();
    UserDetails userDetails = userDetailsService.loadUserByUsername(email);

    Collection<SimpleGrantedAuthority> authorities = userDetails.getAuthorities()
            .stream()
            .map(auth -> new SimpleGrantedAuthority(auth.getAuthority()))
            .collect(Collectors.toList());

    return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
  }
}
