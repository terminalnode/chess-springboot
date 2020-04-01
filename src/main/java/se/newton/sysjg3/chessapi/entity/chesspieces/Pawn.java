package se.newton.sysjg3.chessapi.entity.chesspieces;

import java.util.List;

public class Pawn extends Piece {
  public Pawn(int id, int x, int y, boolean isWhite) {
    super(id, x, y, isWhite);
  }

  @Override
  public List<int[]> getMoves(List<Piece> pieces) {
    return null;
  }
}
