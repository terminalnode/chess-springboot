package se.newton.sysjg3.chessapi.helpers;


import se.newton.sysjg3.chessapi.rest.exceptions.IllegalMoveException;

public class ChessMove {

    private int pieceNumber;

    private int[] destination;



    //----- Constructors -----//

public ChessMove(int piece, int[] destination) {

    this.pieceNumber = piece;

    this.destination = destination;

    validateDestination();
    }


    //----- Methods -----//
    private void validateDestination() {

        if (destination[0] > 7||destination[1] > 7) {
            throw new IllegalMoveException("Destination (" + destination[0] + "," + destination[1] + ") is out of bounds");
        }
        else if (destination[0] < 0||destination[1] < 0) {
            throw new IllegalMoveException("Destination (" + destination[0] + "," + destination[1] + ") is out of bounds");
        }

    }


    //----- Getters and Setters -----//


    public int getPieceNumber() {
        return pieceNumber;
    }

    public void setPiece(int pieceNumber) {
        this.pieceNumber = pieceNumber;
    }

    public int[] getDestination() {
        return destination;
    }

    public void setDestination(int[] destination) {
        this.destination = destination;
        validateDestination();
    }
}


