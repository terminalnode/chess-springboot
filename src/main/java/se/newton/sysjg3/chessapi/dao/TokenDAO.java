package se.newton.sysjg3.chessapi.dao;

import se.newton.sysjg3.chessapi.entity.Player;
import se.newton.sysjg3.chessapi.entity.Token;

public interface TokenDAO {
    /**
     * Creates a new token for a Player entity.
     * @param player The Player entity for whom to create the token.
     * @return The newly created token.
     */
    public Token createTokenForPlayer(Player player);

    /**
     * Get the Player entity associated with a token.
     * @param token The token from which to extract the Player entity.
     * @return The token's associated Player entity.
     */
    public Player getPlayerFromToken(Token token);


    /**
     * Get the Player entity associated with a token.
     * @param tokenString The token string from which to extract the Player entity.
     * @return The token's associated Player entity.
     */
    public Player getPlayerFromTokenString(String tokenString);

    /**
     * Check if a token has expired or not.
     * @param token The token to check.
     * @return True if the token has not expired, otherwise False.
     */
    public boolean checkTokenExpiration(Token token);

    /**
     * Extend the createdAt-time for the token, ensuring that it stays
     * valid for longer.
     * @param token The token to extend.
     * @return The token with the extended expiration date.
     */
    public Token extendToken(Token token);

    /**
     * Retrieve a full token (with expiration date, id and everything)
     * from the tokenString it contains.
     * @param tokenString The tokenString.
     * @return The full version of the token.
     */
    public Token getTokenFromTokenString(String tokenString);

    /**
     * Retrieve a full token (with expiration date, id and everything)
     * from the tokenString it contains.
     * @param token The token containing the string.
     * @return The full version of the token.
     */
    public Token getTokenFromTokenString(Token token);

    /**
     * Destroy an active token, removing it from the database.
     * @param token The token to be destroyed.
     */
    public void destroyToken(Token token);
}
