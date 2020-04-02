package se.newton.sysjg3.chessapi.helpers;


public class ChessMove {

    private int piece;

    private int[] destination;


public ChessMove(int piece, int[] destination) {

    this.piece = piece;

    this.destination = destination;

    }

    //----- Getters and Setters -----//


    public int getPiece() {
        return piece;
    }

    public void setPiece(int piece) {
        this.piece = piece;
    }

    public int[] getDestination() {
        return destination;
    }

    public void setDestination(int[] destination) {
        this.destination = destination;
    }
}


