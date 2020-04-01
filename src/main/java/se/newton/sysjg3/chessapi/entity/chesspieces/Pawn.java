package se.newton.sysjg3.chessapi.entity.chesspieces;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Entity
@DiscriminatorValue("pawn")
public class Pawn extends Piece {
  public Pawn() {
    // Default no-arg constructor
  }

  public Pawn(int id, int x, int y, boolean isWhite) {
    super(id, x, y, isWhite);
  }

  @Override
  public List<int[]> getMoves(List<Piece> pieces) {
    return null;
  }
}
