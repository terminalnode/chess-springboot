package se.newton.sysjg3.chessapi.entity.chesspieces;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import se.newton.sysjg3.chessapi.entity.Game;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Inheritance
@DiscriminatorColumn(name = "piece_type")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "class")
@JsonSubTypes(value = {
    @JsonSubTypes.Type(value = Bishop.class, name = "bishop"),
    @JsonSubTypes.Type(value = Knight.class, name = "knight"),
    @JsonSubTypes.Type(value = King.class, name = "king"),
    @JsonSubTypes.Type(value = Pawn.class, name = "pawn"),
    @JsonSubTypes.Type(value = Queen.class, name = "queen"),
    @JsonSubTypes.Type(value = Rook.class, name = "rook")
})
@Table(name = "piece")
public abstract class Piece {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  @Column(name = "internal_id")
  protected int internalId;

  @Column(name = "x_pos")
  protected int x;

  @Column(name = "y_pos")
  protected int y;

  @Column(name = "is_white")
  protected boolean isWhite;

  @Column(name = "moved")
  protected boolean moved;

  @OneToOne
  @JsonIgnore
  @JoinColumn(name = "game_id")
  private Game game;


  //----- Constructors -----//
  public Piece() {
    // Empty no-arg constructor
  }

  public Piece(int internalId, int x, int y, boolean isWhite, Game game) {
    this.internalId = internalId;
    this.x = x;
    this.y = y;
    this.isWhite = isWhite;
    this.game = game;
    moved = false;
  }

  //----- Abstract methods -----//
  public abstract List<int[]> getMoves(List<Piece> pieces);

  //----- Methods -----//
  public void move(int x, int y) {
    this.x = x;
    this.y = y;
  }

  List<int[]> getStraightMoves(List<Piece> pieces) {
    List<int[]> moves = new ArrayList<>();
    boolean downBlocked = false;
    boolean upBlocked = false;
    boolean rightBlocked = false;
    boolean leftBlocked = false;

    for (int i = 1; i < 8; i++) {
      int[] down = new int[]{x, y + i};
      int[] up = new int[]{x, y - i};
      int[] right = new int[]{x + i, y};
      int[] left = new int[]{x - i, y};

      if (!downBlocked) {
        downBlocked = !addMoveToList(moves, down, pieces);
      }

      if (!upBlocked) {
        upBlocked = !addMoveToList(moves, up, pieces);
      }

      if (!rightBlocked) {
        rightBlocked = !addMoveToList(moves, right, pieces);
      }

      if (!leftBlocked) {
        leftBlocked = !addMoveToList(moves, left, pieces);
      }
    }

    return moves;
  }

  List<int[]> getDiagonalMoves(List<Piece> pieces) {
    List<int[]> moves = new ArrayList<>();
    boolean upRightBlocked = false;
    boolean upLeftBlocked = false;
    boolean downRightBlocked = false;
    boolean downLeftBlocked = false;

    for (int i = 1; i < 8; i++) {
      int[] upRight   = new int[]{x + i, y - i};
      int[] upLeft    = new int[]{x - i, y - i};
      int[] downRight = new int[]{x + i, y + i};
      int[] downLeft  = new int[]{x - i, y + i};

      if (!upRightBlocked) {
        upRightBlocked = !addMoveToList(moves, upRight, pieces);
      }

      if (!upLeftBlocked) {
        upLeftBlocked = !addMoveToList(moves, upLeft, pieces);
      }

      if (!downRightBlocked) {
        downRightBlocked = !addMoveToList(moves, downRight, pieces);
      }

      if (!downLeftBlocked) {
        downLeftBlocked = !addMoveToList(moves, downLeft, pieces);
      }
    }

    return moves;
  }

  private Piece pieceAtPosition(int[] position, List<Piece> pieces) {
    int xHere = position[0];
    int yHere = position[1];

    for (Piece piece : pieces) {
      if (piece.getX() == xHere && piece.getY() == yHere) {
        return piece;
      }
    }
    return null;
  }

  private boolean isPositionOutOfBounds(int[] position) {
    int xHere = position[0];
    int yHere = position[1];

    return
        xHere < 0 || xHere > 7 ||
            yHere < 0 || yHere > 7;
  }

  public PieceType[] upgrade() {
    return null;
  }

  boolean addMoveToList(List<int[]> moves, int x, int y, List<Piece> otherPieces) {
    return addMoveToList(moves, new int[]{x, y}, otherPieces);
  }

  boolean addMoveToList(List<int[]> moves, int[] position, List<Piece> otherPieces) {
    if (isPositionOutOfBounds(position)) {
      return false;
    }

    Piece pieceHere = pieceAtPosition(position, otherPieces);
    boolean blockedByPiece = pieceHere != null;
    boolean blockedByOwnColor = blockedByPiece && pieceHere.isWhite() == isWhite();

    if (blockedByOwnColor) {
      return false;
    } else if (blockedByPiece) {
      moves.add(position);
      return false;
    } else {
      moves.add(position);
      return true;
    }
  }

  @Override
  public String toString() {
    return String.format("<%s (%s,%s)>", this.getClass().getName(), x, y);
  }

  //----- Setters -----//
  public void setId(long id) {
    this.id = id;
  }

  public void setInternalId(int internalId) {
    this.internalId = internalId;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void setWhite(boolean white) {
    isWhite = white;
  }

  public void setMoved(boolean moved) {
    this.moved = moved;
  }

  public void setGame(Game game) {
    this.game = game;
  }

  //----- Getters -----//
  public long getId() {
    return id;
  }

  public int getInternalId() {
    return internalId;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public boolean isWhite() {
    return isWhite;
  }

  public boolean isMoved() {
    return moved;
  }

  public Game getGame() {
    return game;
  }
}
