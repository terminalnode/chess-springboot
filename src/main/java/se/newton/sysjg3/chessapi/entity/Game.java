package se.newton.sysjg3.chessapi.entity;

import se.newton.sysjg3.chessapi.entity.chesspieces.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chessboard")
public class Game {
  private Player whitePlayer;
  private Player blackPlayer;
  private boolean whitesTurn;
  private int turnsTaken;
  private List<Piece> pieces;

  private static boolean WHITE = true;
  private static boolean BLACK = false;

  public Game() {
    // Empty no-args constructor required by Hibernate.
  }

  public Game(Player whitePlayer, Player blackPlayer) {
    this.whitePlayer = whitePlayer;
    this.blackPlayer = blackPlayer;
    whitesTurn = true;
    turnsTaken = 0;
  }

  private List<Piece> generatePieces() {
    List<Piece> list = new ArrayList<>();
    int id = 0;

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
}
