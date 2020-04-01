package se.newton.sysjg3.chessapi.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.entity.Token;
import se.newton.sysjg3.chessapi.service.TokenService;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginRestController {
  private TokenService tokenService;

  @Autowired
  public LoginRestController(TokenService tokenService) {
    this.tokenService = tokenService;
  }

  @PostMapping("/login")
  public Token loginPlayer(@RequestBody Player player) {
    return tokenService.createTokenForPlayer(player);
  }

  @PostMapping("/logout")
  public String logoutPlayer(@RequestHeader(value="Token") String tokenString) {
    tokenService.destroyToken(tokenString);
    return "Token deleted succesfully!";
  }
}
