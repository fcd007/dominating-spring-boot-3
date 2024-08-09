package br.dev.dantas.point.domain.mappers;

import br.dev.dantas.point.annotation.EncodedMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordEncodedMapper {

  private final PasswordEncoder passwordEncoder;

  @EncodedMapping
  public String enconde(String rawPassword) {
    return rawPassword == null ? null: passwordEncoder.encode(rawPassword);
  }
}
