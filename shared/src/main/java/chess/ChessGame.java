package chess;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor teamColor; //whose turn it is, maybe rename the variable?
    private ChessBoard chessBoard;

    public ChessGame() {
        this.chessBoard = new ChessBoard();
        this.teamColor = TeamColor.WHITE;
        this.chessBoard.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamColor;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.teamColor = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */

    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        // this will check all valid moves through the calculator and makes sure it doesn't put the King in Check.
        ChessPiece piece = chessBoard.getPiece(startPosition);
        if (piece == null || piece.getTeamColor() != teamColor) {
            return new ArrayList<>();
        }
        Collection<ChessMove> possibleMoves = piece.pieceMoves(chessBoard, startPosition);

        List<ChessMove> validMoves = new ArrayList<>();
        for (ChessMove move : possibleMoves) {
            if (isSafe(move)){
                validMoves.add(move);
            }
        }
        return validMoves;
    }
    public boolean isSafe(ChessMove move) {
        //basically simulate the move and if it results in check, don't do it!
        //could do a copy method to not overide stuff accidently, but that might be more work
        boolean isSafe = true;
        ChessPiece tempPiece = chessBoard.getPiece(move.getEndPosition());

        chessBoard.addPiece(move.getEndPosition(), tempPiece); // simulate the move and get rid of duplicate
        chessBoard.addPiece(move.getStartPosition(), null);

        if (isInCheck(tempPiece.getTeamColor())){
            isSafe = false;
        }

        chessBoard.addPiece(move.getEndPosition(), null); // return to starting positions
        chessBoard.addPiece(move.getStartPosition(), tempPiece);

        return isSafe;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {

    }


    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        // just check if your next move calculator contians position of Opponents King
        ChessPosition kingPos = findKing(teamColor);
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition currentPos = new ChessPosition(i, j);
                ChessPiece piece = chessBoard.getPiece(currentPos);

                // Skip over empty squares
                if (piece == null || piece.getTeamColor() == teamColor) {
                    continue;
                }

                // check if enemy piece can take King
                Collection<ChessMove> possibleMoves = piece.pieceMoves(chessBoard, currentPos);
                for (ChessMove move : possibleMoves) {
                    if (move.getEndPosition().equals(kingPos)) {
                        return true;
                    }
                }
            }
        }

        return false;

    }

    public ChessPosition findKing(TeamColor teamColor) {
        TeamColor team = teamColor;
        for (int i = 1; i <= 8; i++){
            for (int j = 1; j <= 8 ; j++) {
                ChessPiece currentPiece = chessBoard.getPiece(new ChessPosition(i, j));

                if(currentPiece.getPieceType() == ChessPiece.PieceType.KING && currentPiece.getTeamColor() == team){
                    return new ChessPosition(i, j); // return kingPos
                }
            }
        }
        throw new IllegalStateException("King not found for team " + teamColor);
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (!isInCheck(teamColor)) {
            return false;
        }

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition currentPos = new ChessPosition(i, j);
                ChessPiece currentPiece = chessBoard.getPiece(currentPos);

                if (currentPiece != null && currentPiece.getTeamColor() == teamColor) {
                    Collection<ChessMove> moves = validMoves(currentPos);
                    if (!moves.isEmpty()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        for (int i = 1; i <=8 ; i++) {
            for (int j = 1; j <=8 ; j++) {
                ChessPosition currentPos = new ChessPosition(i, j);
                ChessPiece currentPiece = chessBoard.getPiece(currentPos);
                if (currentPiece != null && currentPiece.getTeamColor() == teamColor) {
                    Collection<ChessMove> moves = validMoves(currentPos);

                    if (!moves.isEmpty()) {
                        return false;
                    }
                }

            }

        }
        return !isInCheck(teamColor);
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.chessBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return chessBoard;
    }
}
