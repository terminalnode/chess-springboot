package se.newton.sysjg3.chessapi.entity.chesspieces;

import se.newton.sysjg3.chessapi.entity.Game;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("knight")
public class Knight extends Piece {
  public Knight() {
    // Default no-arg constructor
  }

  public Knight(int id, int x, int y, boolean isWhite, Game game) {
    super(id, x, y, isWhite, game);
  }

  @Override
  public List<int[]> getMoves(List<Piece> pieces) {
    List<int[]> moves = new ArrayList<>();
    int x = getX();
    int y = getY();

    int[][] theoreticalMoves = new int[][]{
        { x - 1, y - 2 },
        { x + 1, y - 2 },
        { x - 1, y + 2 },
        { x + 1, y + 2 },
        { x - 2, y - 1 },
        { x - 2, y + 1 },
        { x + 2, y - 1 },
        { x + 2, y + 1 }
    };

    for (int[] theoreticalMove : theoreticalMoves) {
      addMoveToList(moves, theoreticalMove[0], theoreticalMove[1], pieces);
    }

    return moves;
  }

  @Override
  public Piece clonePiece() {
    return new Knight(getInternalId(), getX(), getY(), isWhite(), getGame());
  }
}
