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

    @Transactional
    @Override
    public Token createTokenForPlayer(Player player) {
        Session session = entityManager.unwrap(Session.class);

        if (player == null) {
            System.out.println(">>>PLAYER IS NULL");
        }
        else {
            System.out.println("PLAYER IS: " + player.getName());
        }

        Token token = new Token(player);
        if (token == null) {
            System.out.println(">>>TOKEN IS NULL");
        }
        else {
            System.out.println("TOKEN IS: " + token.getTokenString());
        }

        token.setId(0);
        player.addToken(token);
        session.save(token);


        return token;
    }



}
