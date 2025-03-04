package chess;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessGame.TeamColor color;
    private PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.color = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return this.color;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        PieceMovesCalculator moves = switch(getPieceType()){
            case KING -> new KingMoveCalculator();
            case QUEEN -> new QueenMoveCalculator();
            case BISHOP -> new BishopMoveCalculator();
            case KNIGHT -> new KnightMoveCalculator();
            case ROOK -> new RookMoveCalculator();
            case PAWN -> new PawnMoveCalculator();

        };
        return moves.pieceMoves(board, myPosition);
    }
    @Override
    public String toString() {
        return switch (type) {
            case KING -> color == ChessGame.TeamColor.WHITE ? "K" : "k";
            case QUEEN -> color == ChessGame.TeamColor.WHITE ? "Q" : "q";
            case BISHOP -> color == ChessGame.TeamColor.WHITE ? "B" : "b";
            case KNIGHT -> color == ChessGame.TeamColor.WHITE ? "N" : "n";
            case ROOK -> color == ChessGame.TeamColor.WHITE ? "R" : "r";
            case PAWN -> color == ChessGame.TeamColor.WHITE ? "P" : "p";
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){ return true;}
        if (o == null || getClass() != o.getClass()){ return false;}
        ChessPiece that = (ChessPiece) o;
        return color == that.color && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, type);
    }
}
