package se.newton.sysjg3.chessapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import se.newton.sysjg3.chessapi.entity.chesspieces.*;
import se.newton.sysjg3.chessapi.helpers.ChessMove;
import se.newton.sysjg3.chessapi.rest.exceptions.IllegalMoveException;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "game")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

  @Column(name = "black_in_check")
  private boolean isBlackInCheck;

  @Column(name = "white_in_check")
  private boolean isWhiteInCheck;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
  private List<Piece> pieces;

  @Column(name = "finished")
  private boolean finished;

  @Transient
  Map<Integer, Piece> pieceMap;

  @Transient
  boolean isGettingPlayerWhite;

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
    this.isWhiteInCheck = false;
    this.isBlackInCheck = false;
    whitesTurn = true;
    turnsTaken = 0;
    pieces = generatePieces();
  }

  //----- Methods -----//
  private List<Piece> generatePieces() {
    List<Piece> list = new ArrayList<>();
    int id = 1;

    // Add pawns
    for (int x = 0; x < 8; x++) {
      list.add(new Pawn(++id, x, 1, WHITE, this));
      list.add(new Pawn(++id, x, 6, BLACK, this));
    }

    // Add rooks
    list.add(new Rook(++id, 0, 0, WHITE, this));
    list.add(new Rook(++id, 7, 0, WHITE, this));
    list.add(new Rook(++id, 0, 7, BLACK, this));
    list.add(new Rook(++id, 7, 7, BLACK, this));

    // Add knights
    list.add(new Knight(++id, 1, 0, WHITE, this));
    list.add(new Knight(++id, 6, 0, WHITE, this));
    list.add(new Knight(++id, 1, 7, BLACK, this));
    list.add(new Knight(++id, 6, 7, BLACK, this));

    // Add bishops
    list.add(new Bishop(++id, 2, 0, WHITE, this));
    list.add(new Bishop(++id, 5, 0, WHITE, this));
    list.add(new Bishop(++id, 2, 7, BLACK, this));
    list.add(new Bishop(++id, 5, 7, BLACK, this));

    // Add kings and queens
    list.add(new King(++id, 4, 0, WHITE, this));
    list.add(new King(++id, 4, 7, BLACK, this));
    list.add(new Queen(++id, 3, 0, WHITE, this));
    list.add(new Queen(++id, 3, 7, BLACK, this));

    return list;
  }

  public void populatePieceMap() {
    pieceMap = new HashMap<>();
    for (Piece piece:pieces) {
      pieceMap.put(piece.getInternalId(), piece);
    }
  }


  public void movePiece(ChessMove chessMove) {
    Piece movingPiece = getPieceByIdNumber(chessMove.getPieceNumber());
    if (movingPiece == null) {
      throw new IllegalMoveException("The piece is not on the board!");
    }
    else {
      System.out.println("Moving piece " + movingPiece.getInternalId() + " to (" + chessMove.getDestination()[0] + "," + chessMove.getDestination()[0] + ")");

      Piece takenPiece = null;
      for (Piece piece:pieceMap.values()) {
        if (piece.getX() == chessMove.getDestination()[0]) {
          if (piece.getY() == chessMove.getDestination()[1]) {
            takenPiece = piece;
            break;
          }
        }
      }
      if (takenPiece != null) {
        pieces.remove(takenPiece);
        pieceMap.remove(takenPiece);
      }

      movingPiece.setX(chessMove.getDestination()[0]);
      movingPiece.setY(chessMove.getDestination()[1]);

      this.turnsTaken++;
      whitesTurn = !whitesTurn;

    }
  }

  public Piece getPieceByIdNumber(int idNumber) {
    return pieceMap.get(idNumber);
  }

  public Player getCurrentPlayer() {
    return whitesTurn ? whitePlayer : blackPlayer;
  }

  public boolean validateMove(ChessMove move) {
      this.populatePieceMap();
      Piece movingPiece = pieceMap.get(move.getPieceNumber());
      if (movingPiece == null) {
        return false;
      }
      for (int[] possibleMove:movingPiece.getMoves(pieces)) {
        if (Arrays.equals(move.getDestination(), possibleMove)) {
          if (!checkForCheck())
            System.out.println("Move is valid");
          return true;
        }
      }
    System.out.println("Move is invalid");
      return false;
  }

  boolean isCurrentPlayerInCheck() {
    return whitesTurn ? isWhiteInCheck : isBlackInCheck;
  }

  public boolean checkForCheck() {

    int kingId = whitesTurn ? 29 : 30;
    int[] kingPosition = new int[] {pieceMap.get(kingId).getX(), pieceMap.get(kingId).getY()};

    List<Piece> opponentsPieces = getOneColourPieces(!whitesTurn);
    for (Piece opponentsPiece:opponentsPieces) {
      for (int[] position : opponentsPiece.getMoves(new ArrayList<Piece>(pieceMap.values()))) {
        if (Arrays.equals(position, kingPosition)) {
          return true;
        }
      }
    }
    return false;
  }



  public boolean checkForCheckMate() {

    int kingId = whitesTurn ? 29 : 30;
    int[] kingPosition = new int[] {pieceMap.get(kingId).getX(), pieceMap.get(kingId).getY()};

    List<Piece> yourPieces = getOneColourPieces(whitesTurn);

    for (Piece yourPiece:yourPieces) {
      for (int[] position : yourPiece.getMoves(new ArrayList<Piece>(pieceMap.values()))) {
        ChessMove potentialMove = new ChessMove(yourPiece.getInternalId(), position);

        Piece takenPiece = null;
        Piece originalPiece = pieceMap.get(potentialMove.getPieceNumber());

        int[] originalPosition= new int[] {originalPiece.getX(), originalPiece.getY()};

        for (Piece piece:pieceMap.values()) {
          if (piece.getX() == potentialMove.getDestination()[0]) {
            if (piece.getY() == potentialMove.getDestination()[1]) {
              takenPiece = piece;
              break;
            }
          }
        }
        if (takenPiece != null) {
          pieceMap.remove(takenPiece);
        }

        originalPiece.setX(potentialMove.getDestination()[0]);
        originalPiece.setX(potentialMove.getDestination()[1]);

        if(!checkForCheck()) {
          if (takenPiece != null) {
            pieceMap.put(takenPiece.getInternalId(),takenPiece);
          }
          originalPiece.setX(originalPosition[0]);
          originalPiece.setY(originalPosition[1]);
          return false;
        }

        if (takenPiece != null) {
          pieceMap.put(takenPiece.getInternalId(), takenPiece);
        }
        originalPiece.setX(originalPosition[0]);
        originalPiece.setY(originalPosition[1]);
      }

    }
    return true;
  }

  public List<Piece> getOneColourPieces(boolean whitePieces) {

    List<Piece> listOfPieces = new ArrayList<>();
    for (Piece piece : pieces) {
      if (piece.isWhite() == whitePieces) {
        listOfPieces.add(piece);
      }
    }
    return listOfPieces;
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

  public void setBlackInCheck(boolean blackInCheck) {
    isBlackInCheck = blackInCheck;
  }

  public void setWhiteInCheck(boolean whiteInCheck) {
    isWhiteInCheck = whiteInCheck;
  }

  public void setGettingPlayerWhite(boolean gettingPlayerWhite) {
    this.isGettingPlayerWhite = gettingPlayerWhite;
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

  public boolean isBlackInCheck() {
    return isBlackInCheck;
  }

  public boolean isWhiteInCheck() {
    return isWhiteInCheck;
  }

  public boolean getGettingPlayerWhite() {
    return isGettingPlayerWhite;
  }
}
