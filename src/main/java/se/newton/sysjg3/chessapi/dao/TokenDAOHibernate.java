package se.newton.sysjg3.chessapi.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.entity.Token;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
public class TokenDAOHibernate implements TokenDAO {
  private EntityManager entityManager;

  @Autowired
  public TokenDAOHibernate(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  @Transactional
  public Token createTokenForPlayer(Player player) {
    Session session = entityManager.unwrap(Session.class);

    // Check if player is a managed entity, and if not try to replace it
    // with one that is. If the ID wasn't provided or invalid, this won't work.
    if (!entityManager.contains(player) && player.getId() != 0) {
      player = session.get(Player.class, player.getId());
      if (player == null) {
        return null;
      }
    }

    // Create token and add it to player
    Token token = new Token(player);
    token.setId(0);
    player.addToken(token);
    session.save(token);

    return token;
  }
}
