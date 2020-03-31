package se.newton.sysjg3.chessapi.dao;

import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.entity.Token;

public interface TokenDAO {

    public Token createTokenForPlayer(Player player);
}
