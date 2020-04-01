package se.newton.sysjg3.chessapi.dao;

import se.newton.sysjg3.chessapi.entity.Challenge;
import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.entity.Token;

import javax.persistence.EntityManager;

/**
 * Entities gotten through API calls are not managed by hibernate,
 * in order to do pretty much anything with them, such as refreshing
 * the object, updating existing objects, retrieve lazily loaded
 * attributes and so on. This class contains helper methods to do that.
 *
 * The input to the methods are the objects that we need a managed version
 * of and an EntityManager. If no managed entity can be found it will
 * always return null.
 *
 * If the object is already managed it gets returned as is.
 */
public class ManagedEntityHelper {
  public static Challenge getManaged(Challenge challenge, EntityManager entityManager) {
    if (challenge == null || challenge.getId() == 0) {
      return null;
    } else if (entityManager.contains(challenge)) {
      return challenge;
    }

    challenge = entityManager.find(Challenge.class, challenge.getId());
    return challenge;
  }

  /**
   * Get managed Player from Player.
   * @param player The player to be checked.
   * @return A managed version of the Player entity, or null.
   */
  public static Player getManaged(Player player, EntityManager entityManager) {
    if (player == null || player.getId() == 0) {
      return null;
    } else if (entityManager.contains(player)) {
      return player;
    }

    player = entityManager.find(Player.class, player.getId());
    return player;
  }

  /**
   * Get managed Token from Token.
   * @param token The token to be checked.
   * @return A managed version of the Token entity, or null.
   */
  public static Token getManaged(Token token, EntityManager entityManager) {
    if (token == null || token.getId() == 0) {
      return null;
    } else if (entityManager.contains(token)) {
      return token;
    }

    token = entityManager.find(Token.class, token.getId());
    return token;
  }
}
