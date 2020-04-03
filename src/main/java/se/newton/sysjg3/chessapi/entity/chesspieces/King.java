package se.newton.sysjg3.chessapi.entity.chesspieces;

import se.newton.sysjg3.chessapi.entity.Game;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("king")
public class King extends Piece {
  public King() {
    // Default no-arg constructor
  }

  public King(int id, int x, int y, boolean isWhite, Game game) {
    super(id, x, y, isWhite, game);
  }

  @Override
  public List<int[]> getMoves(List<Piece> pieces) {
    List<int[]> moves = new ArrayList<>();
    int x = getX();
    int y = getY();

    int[][] theoreticalMoves = new int[][]{
        { x    , y + 1 },
        { x    , y - 1 },
        { x + 1, y     },
        { x - 1, y     },
        { x + 1, y + 1 },
        { x + 1, y - 1 },
        { x - 1, y + 1 },
        { x - 1, y - 1 }
    };

    for (int[] theoreticalMove : theoreticalMoves) {
      addMoveToList(moves, theoreticalMove[0], theoreticalMove[1], pieces);
    }
    return moves;
  }
}
