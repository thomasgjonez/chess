package chess;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor teamColor; //whose turn it is, maybe rename the variable?
    private ChessBoard chessBoard;
    private Boolean gameOver = false;

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
        if (piece == null) {
            return null;
        }
        Collection<ChessMove> possibleMoves = piece.pieceMoves(chessBoard, startPosition);
        List<ChessMove> validMoves = new ArrayList<>();

        for (ChessMove move : possibleMoves) {
            ChessPiece tempPiece = chessBoard.getPiece(move.getEndPosition());
            if(isSafe(move, piece, tempPiece, startPosition)){
                validMoves.add(move);
            }
        }
        return validMoves;
    }
    public boolean isSafe(ChessMove move, ChessPiece piece, ChessPiece tempPiece, ChessPosition startPosition) {
        //basically simulate the move and if it results in check, don't do it!
        //could do a copy method to not override stuff accidentally, but that might be more work
        chessBoard.addPiece(move.getEndPosition(), piece); // simulate the move and get rid of duplicate
        chessBoard.addPiece(startPosition, null);

        boolean isSafe = !isInCheck(piece.getTeamColor());

        chessBoard.addPiece(move.getEndPosition(), tempPiece); // return to starting state of board
        chessBoard.addPiece(startPosition, piece);
        return isSafe;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition start = move.getStartPosition();
        ChessPosition end = move.getEndPosition();

        ChessPiece piece = chessBoard.getPiece(start);
        if (piece == null || piece.getTeamColor() != teamColor) {
            throw new InvalidMoveException("No piece in that start position or its not your turn.");
        }

        if (piece.getPieceType() ==  ChessPiece.PieceType.PAWN) {
            int endRow = move.getEndPosition().getRow();
            boolean isPromotionRank = (endRow == 1 || endRow == 8);
            boolean providedPromotion = move.getPromotionPiece() != null;

            // If a promotion piece is provided, but this move doesn't reach promotion row
            if (providedPromotion && !isPromotionRank) {
                throw new InvalidMoveException("You provided a promotion piece, but your move does not warrant one.");
            }

            // If a pawn reaches the promotion row but no piece is given
            if (!providedPromotion && isPromotionRank) {
                throw new InvalidMoveException("Your pawn reached the promotion row but you didn't specify a promotion piece.");
            }
        }

        if (move.getPromotionPiece() != null && piece.getPieceType() != ChessPiece.PieceType.PAWN) {
            throw new InvalidMoveException("Only pawns can be promoted.");
        }

        Collection<ChessMove> validMoves = validMoves(start);


        if (validMoves == null || !validMoves.contains(move)) {
            throw new InvalidMoveException("You can't move there :/");
        }
        // Perform the move on the board
        chessBoard.addPiece(end, piece);
        chessBoard.addPiece(start, null);

        // Check for promotion, need to add on all the possible moves? or get the required piece.
        if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {
            if ((piece.getTeamColor() == TeamColor.WHITE && end.getRow() == 8) ||
                    (piece.getTeamColor() == TeamColor.BLACK && end.getRow() == 1)) {
                ChessPiece promotionPiece = new ChessPiece(piece.getTeamColor(),move.getPromotionPiece());
                chessBoard.addPiece(move.getEndPosition(),promotionPiece);
            }
        }

        // Switch the turn to the other team
        teamColor = (teamColor == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;

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

                // Skip over empty squares or your team's pieces
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
        for (int i = 1; i <= 8; i++){
            for (int j = 1; j <= 8 ; j++) {
                ChessPosition pos = new ChessPosition(i, j);
                ChessPiece currentPiece = chessBoard.getPiece(new ChessPosition(i, j));

                if (currentPiece != null &&
                        currentPiece.getPieceType() == ChessPiece.PieceType.KING &&
                        currentPiece.getTeamColor() == teamColor) {
                    return pos;
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
        return isInCheck(teamColor) && hasValidMove(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return !isInCheck(teamColor) && hasValidMove(teamColor);
    }

    private boolean hasValidMove(TeamColor teamColor){
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
    public TeamColor getTeamColor() {
        return teamColor;
    }

    public Boolean getGameOver() {
        return gameOver;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean over) {
        this.gameOver = over;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return teamColor == chessGame.teamColor && Objects.equals(chessBoard, chessGame.chessBoard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamColor, chessBoard);
    }
    public String toString() {
        return "ChessGame{" +
                "teamColor=" + teamColor +
                ", gameOver=" + gameOver +
                ", chessBoard=" + chessBoard +
                '}';
    }
}
