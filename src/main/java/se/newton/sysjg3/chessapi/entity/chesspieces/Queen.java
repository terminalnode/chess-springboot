package se.newton.sysjg3.chessapi.entity.chesspieces;

import se.newton.sysjg3.chessapi.entity.Game;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Entity
@DiscriminatorValue("queen")
public class Queen extends Piece {
  public Queen() {
    // Default no-arg constructor
  }

  public Queen(int id, int x, int y, boolean isWhite, Game game) {
    super(id, x, y, isWhite, game);
  }

  @Override
  public List<int[]> getMoves(List<Piece> pieces) {
    return null;
  }
}
