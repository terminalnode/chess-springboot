package se.newton.sysjg3.chessapi.entity;

import se.newton.sysjg3.chessapi.entity.chesspieces.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chessboard")
public class Game {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  @ManyToOne
  @JoinColumn(name = "white_player")
  private Player whitePlayer;

  @ManyToOne
  @JoinColumn(name = "black_player")
  private Player blackPlayer;

  @Column(name = "whites_turn")
  private boolean whitesTurn;

  @Column(name = "turns_taken")
  private int turnsTaken;

  @OneToMany
  @Column(name = "pieces")
  @JoinTable(
      name = "piece",
      joinColumns = @JoinColumn(name="game_id"),
      inverseJoinColumns = @JoinColumn(name="piece_id")
  )
  private List<Piece> pieces;

  @Column(name = "finished")
  private boolean finished;

  //----- Static fields -----//
  private static boolean WHITE = true;
  private static boolean BLACK = false;

  //----- Constructors -----//
  public Game() {
    // Empty no-args constructor required by Hibernate.
  }

  public Game(Player whitePlayer, Player blackPlayer) {
    this.whitePlayer = whitePlayer;
    this.blackPlayer = blackPlayer;
    whitesTurn = true;
    turnsTaken = 0;
  }

  //----- Methods -----//
  private List<Piece> generatePieces() {
    List<Piece> list = new ArrayList<>();
    int id = 1;

    // Add pawns
    for (int x = 0; x < 8; x++) {
      list.add(new Pawn(++id, x, 1, WHITE));
      list.add(new Pawn(++id, x, 6, BLACK));
    }

    // Add rooks
    list.add(new Rook(++id, 0, 0, WHITE));
    list.add(new Rook(++id, 7, 0, WHITE));
    list.add(new Rook(++id, 0, 7, BLACK));
    list.add(new Rook(++id, 7, 7, BLACK));

    // Add knights
    list.add(new Knight(++id, 1, 0, WHITE));
    list.add(new Knight(++id, 6, 0, WHITE));
    list.add(new Knight(++id, 1, 7, BLACK));
    list.add(new Knight(++id, 6, 7, BLACK));

    // Add bishops
    list.add(new Bishop(++id, 2, 0, WHITE));
    list.add(new Bishop(++id, 5, 0, WHITE));
    list.add(new Bishop(++id, 2, 7, BLACK));
    list.add(new Bishop(++id, 5, 7, BLACK));

    // Add kings and queens
    list.add(new King(++id, 4, 0, WHITE));
    list.add(new King(++id, 4, 7, BLACK));
    list.add(new Queen(++id, 3, 0, WHITE));
    list.add(new Queen(++id, 3, 7, BLACK));

    return list;
  }

  //----- Setters -----//
  public void setId(long id) {
    this.id = id;
  }

  public void setWhitePlayer(Player whitePlayer) {
    this.whitePlayer = whitePlayer;
  }

  public void setBlackPlayer(Player blackPlayer) {
    this.blackPlayer = blackPlayer;
  }

  public void setWhitesTurn(boolean whitesTurn) {
    this.whitesTurn = whitesTurn;
  }

  public void setTurnsTaken(int turnsTaken) {
    this.turnsTaken = turnsTaken;
  }

  public void setPieces(List<Piece> pieces) {
    this.pieces = pieces;
  }

  public void setFinished(boolean finished) {
    this.finished = finished;
  }

  //----- Getters -----//
  public long getId() {
    return id;
  }

  public Player getWhitePlayer() {
    return whitePlayer;
  }

  public Player getBlackPlayer() {
    return blackPlayer;
  }

  public boolean isWhitesTurn() {
    return whitesTurn;
  }

  public int getTurnsTaken() {
    return turnsTaken;
  }

  public List<Piece> getPieces() {
    return pieces;
  }

  public boolean isFinished() {
    return finished;
  }
}
