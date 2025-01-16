package chess;
import java.util.ArrayList;
import java.util.Collection;

public interface PieceMovesCalculator {
    static Collection<ChessMove> KingMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color) {
        Collection<ChessMove> moves = new ArrayList<>();
        return moves;
    }
    static Collection<ChessMove> QueenMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color) {
        Collection<ChessMove> moves = new ArrayList<>();
        return moves;
    }
    static Collection<ChessMove> BishopMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color) {
        Collection<ChessMove> moves = new ArrayList<>();
        return moves;
    }
    static Collection<ChessMove> KnightMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color) {
        Collection<ChessMove> moves = new ArrayList<>();
        return moves;
    }
    static Collection<ChessMove> RookMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color) {
        Collection<ChessMove> moves = new ArrayList<>();
        return moves;
    }
    static Collection<ChessMove> PawnMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color) {
        Collection<ChessMove> moves = new ArrayList<>();
        return moves;
    }
}
