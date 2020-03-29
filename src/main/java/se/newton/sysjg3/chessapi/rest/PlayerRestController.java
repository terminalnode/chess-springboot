package se.newton.sysjg3.chessapi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.service.PlayerService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PlayerRestController {
  private PlayerService playerService;

  @Autowired
  public PlayerRestController(PlayerService playerService) {
    this.playerService = playerService;
  }

  @GetMapping("/players")
  public List<Player> getAll() {
    return playerService.getAll();
  }

  @PostMapping("/players")
  public Player createUser(@RequestBody Player player) {
    player.setId(0);
    playerService.create(player);
    return player;
  }
}
