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
    boolean noName = (player.getName() == null || player.getName().isEmpty());
    boolean noPassword = (player.getPassword() == null || player.getPassword().isEmpty());

    // Validate input before creating user
    if (noName && noPassword) {
      throw new PlayerCreateMissingFieldsException(
          "Missing fields: name, password",
          new String[]{ "name", "password" });

    } else if (noName) {
      throw new PlayerCreateMissingFieldsException(
          "Missing field: name",
          new String[]{ "name" });

    } else if (noPassword) {
      throw new PlayerCreateMissingFieldsException(
          "Missing field: password",
          new String[]{ "password" });

    }

    player.setName(player.getName().toLowerCase());
    boolean userNameAvailable = playerService.checkUsernameAvailability(player.getName());
    if (!userNameAvailable) {
      throw new PlayerCreateUsernameTaken(String.format("Username taken: %s", player.getName()));
    }

    // Create and return the new player
    playerService.create(player);
    return player;
  }
}
