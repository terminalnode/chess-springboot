package se.newton.sysjg3.chessapi.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestRestController {
  @GetMapping("/eclipse")
  public String getEclipse() {
    return "Friends don't let friends get Eclipse.";
  }
}
