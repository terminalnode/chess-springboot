package se.newton.sysjg3.chessapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.newton.sysjg3.chessapi.dao.PlayerDAO;
import se.newton.sysjg3.chessapi.dao.TokenDAO;
import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.entity.Token;
import se.newton.sysjg3.chessapi.rest.exceptions.LoginFailedException;


@Service
public class LoginServiceImplementation implements LoginService {

    private PlayerDAO playerDAO;
    private TokenDAO tokenDAO;

    @Autowired
    public LoginServiceImplementation(PlayerDAO playerDAO, TokenDAO tokenDAO) {
        this.playerDAO = playerDAO;
        this.tokenDAO = tokenDAO;
    }

    @Override
    @Transactional
    public Token loginPlayer(Player player) {

        boolean passwordVerified;

        passwordVerified = playerDAO.verifyPassword(player.getName(), player.getPassword());
        if (passwordVerified) return tokenDAO.createTokenForPlayer(player);
        else return null;

    }


}
