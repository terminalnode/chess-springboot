package se.newton.sysjg3.chessapi.dao;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.entity.Token;

import javax.persistence.EntityManager;

public class TokenDAOHibernate implements TokenDAO {
    private EntityManager entityManager;

    @Autowired
    public TokenDAOHibernate(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Token createTokenForPlayer(Player player) {
        Session session = entityManager.unwrap(Session.class);

        Token token = new Token(player);
        player.addToken(token);

        session.save(token);

        return token;
    }



}
