package se.newton.sysjg3.chessapi.entity.chesspieces;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import se.newton.sysjg3.chessapi.entity.Game;

import javax.persistence.*;
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

  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = "game_id", nullable = false)
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
