package se.newton.sysjg3.chessapi.service;

import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.entity.Token;

public interface LoginService {

    public Token loginPlayer(Player player);

}
