package chess;

import java.util.Collection;

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
        switch (getPieceType()) {
            case KING:
                return PieceMovesCalculator.KingMoves(board, myPosition, color);
            case QUEEN:
                return PieceMovesCalculator.QueenMoves(board, myPosition, color);
            case BISHOP:
                return PieceMovesCalculator.BishopMoves(board, myPosition, color);
            case KNIGHT:
                return PieceMovesCalculator.KnightMoves(board, myPosition, color);
            case ROOK:
                return PieceMovesCalculator.RookMoves(board, myPosition, color);
            case PAWN:
                return PieceMovesCalculator.PawnMoves(board, myPosition, color);
            default:
                break;
        }

        throw new RuntimeException("Not implemented");
    }
    @Override
    public String toString() {
        return this.type.toString();
    }
}
