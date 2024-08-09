package br.dev.dantas.point.controller.csrftokencontroller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfController {

  @GetMapping("/csrf")
  public CsrfToken csrfToken(CsrfToken token) {
    return token;
  }
}
