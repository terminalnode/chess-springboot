package se.newton.sysjg3.chessapi.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.entity.Token;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
public class TokenDAOHibernate implements TokenDAO {
  private EntityManager entityManager;

  @Value("${token.expiration}")
  private long tokenExpiration;

  @Autowired
  public TokenDAOHibernate(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  //----- Helper Methods -----//

  /**
   * Checks if a Token is managed by the EntityManager, and if it's
   * not tries to replace it with a version that is.
   * @param token The token to be checked.
   * @return Null or a managed version of the token.
   */
  public Token getManagedToken(Token token) {
    if (token == null || token.getId() == 0) {
      return null;
    } else if (entityManager.contains(token)) {
      return token;
    }

    token = entityManager.find(Token.class, token.getId());
    return token;
  }

  /**
   * Checks if a Player is managed by the EntityManager, and if it's
   * not tries to replace it with a version that is.
   * @param player The player to be checked.
   * @return Null or a managed version of the token.
   */
  public Player getManagedPlayer(Player player) {
    if (player == null || player.getId() == 0) {
      return null;
    } else if (entityManager.contains(player)) {
      return player;
    }

    player = entityManager.find(Player.class, player.getId());
    return player;
  }

  //----- Implemented Methods -----//
  @Override
  @Transactional
  public Token createTokenForPlayer(Player player) {
    player = getManagedPlayer(player);
    if (player == null) {
      return null;
    }

    // Create token and add it to player
    Session session = entityManager.unwrap(Session.class);
    Token token = new Token(player);
    token.setId(0);
    player.addToken(token);
    session.save(token);

    return token;
  }

  @Override
  public Player getPlayerFromToken(Token token) {
    token = getManagedToken(token);
    if (token == null) {
      return null;
    }

    return token.getPlayer();
  }

  @Override
  public boolean checkTokenExpiration(Token token) {
    token = getManagedToken(token);
    if (token == null) {
      return false;
    }

    long timeSinceCreation = System.currentTimeMillis() - token.getCreatedAt();
    return timeSinceCreation < tokenExpiration;
  }

  @Override
  public Token extendToken(Token token) {
    token = getManagedToken(token);
    if (token == null) {
      return null;
    }

    // Extend token and save to database
    Session session = entityManager.unwrap(Session.class);
    token.updateCreatedAt();
    session.save(token);

    return token;
  }

  @Override
  public Token getTokenFromTokenString(Token token) {
    return getTokenFromTokenString(token.getTokenString());
  }

  @Override
  public Token getTokenFromTokenString(String tokenString) {
    Session session = entityManager.unwrap(Session.class);
    return session.byNaturalId(Token.class)
        .using("tokenString", tokenString)
        .load();
  }

  @Override
  @Transactional
  public void destroyToken(Token token) {
    token = getTokenFromTokenString(token);

    System.out.println(token);
    if (token != null) {
      Session session = entityManager.unwrap(Session.class);
      session.delete(token);
    }
  }
}
