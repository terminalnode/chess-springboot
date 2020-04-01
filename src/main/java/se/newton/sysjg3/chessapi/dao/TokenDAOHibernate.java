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

  //----- Implemented Methods -----//
  @Override
  @Transactional
  public Token createTokenForPlayer(Player player) {
    player = ManagedEntityHelper.getManaged(player, entityManager);
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
    token = ManagedEntityHelper.getManaged(token, entityManager);
    if (token == null) {
      return null;
    }

    return token.getPlayer();
  }

  @Override
  public Player getPlayerFromTokenString(String tokenString) {
    Token token = getTokenFromTokenString(tokenString);
    token = ManagedEntityHelper.getManaged(token, entityManager);
    if (token == null) {
      return null;
    }

    return token.getPlayer();
  }

  @Override
  public boolean checkTokenExpiration(Token token) {
    token = ManagedEntityHelper.getManaged(token, entityManager);
    if (token == null) {
      return false;
    }

    long timeSinceCreation = System.currentTimeMillis() - token.getCreatedAt();
    return timeSinceCreation < tokenExpiration;
  }

  @Override
  @Transactional
  public Token extendToken(Token token) {
    token = ManagedEntityHelper.getManaged(token, entityManager);
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
