package se.newton.sysjg3.chessapi.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.service.PlayerService;

import java.util.Set;

@RestController
@RequestMapping("/api")
public class FriendRestController {
  private PlayerService playerService;

  @Autowired
  public FriendRestController(PlayerService playerService) {
    this.playerService = playerService;
  }

  @GetMapping("/friends")
  public Set<Player> getFriends(@RequestHeader("Token") String token) {
    return playerService.getFriends(token);
  }

  @PostMapping("/friends")
  public Player addFriend(@RequestHeader("Token") String token, @RequestBody Player newFriend) {
    return playerService.addFriend(token, newFriend);
  }
}
