package se.newton.sysjg3.chessapi.entity.chesspieces;

import se.newton.sysjg3.chessapi.entity.Game;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Entity
@DiscriminatorValue("bishop")
public class Bishop extends Piece {
  public Bishop() {
    // Default no-arg constructor
  }

  public Bishop(int id, int x, int y, boolean isWhite, Game game) {
    super(id, x, y, isWhite, game);
  }

  @Override
  public List<int[]> getMoves(List<Piece> pieces) {
    return getDiagonalMoves(pieces);
  }

  @Override
  public Piece clonePiece() {
    return new Bishop(getInternalId(), getX(), getY(), isWhite(), getGame());
  }
}
