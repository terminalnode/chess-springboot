package se.newton.sysjg3.chessapi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.rest.exceptions.PlayerCreateMissingFieldsException;
import se.newton.sysjg3.chessapi.rest.exceptions.PlayerCreateUsernameTaken;
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
    // Setting ID to 0 is basically unsetting it
    player.setId(0);

    // Validate input before creating user
    if (player.getName() == null && player.getPassword() == null) {
      throw new PlayerCreateMissingFieldsException("Missing fields: username, password");
    } else if (player.getName() == null) {
      throw new PlayerCreateMissingFieldsException("Missing field: username");
    } else if (player.getPassword() == null) {
      throw new PlayerCreateMissingFieldsException("Missing field: password");
    } else if (!playerService.checkUsernameAvailability(player.getName())) {
      throw new PlayerCreateUsernameTaken(String.format("Username taken: %s", player.getName()));
    }

    // Create and return the new player
    playerService.create(player);
    return player;
  }
}
