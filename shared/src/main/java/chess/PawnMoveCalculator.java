package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMoveCalculator implements PieceMovesCalculator {

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        ChessPiece piece = board.getPiece(position);
        if (piece == null || piece.getPieceType() != ChessPiece.PieceType.PAWN) {
            return moves; // Return empty list if no pawn is at the position
        }

        int direction = piece.getTeamColor() == ChessGame.TeamColor.WHITE ? 1 : -1;

        addForwardMoves(moves, board, position, direction, piece);
        addCaptureMoves(moves, board, position, direction, piece);
        return moves;
    }

    void addForwardMoves(ArrayList<ChessMove> moves, ChessBoard board, ChessPosition position, int direction, ChessPiece piece) {
        int row = position.getRow();
        int col = position.getColumn();

        // Single forward move
        ChessPosition targetPosition = new ChessPosition(row + direction, col);
        if (isWithinBounds(targetPosition) && board.getPiece(targetPosition) == null) {
            // Add promotion move if the pawn reaches the last rank
            if (isPromotionRow(targetPosition, piece.getTeamColor())) {
                addPromotionMoves(moves, position, targetPosition);
            } else {
                moves.add(new ChessMove(position, targetPosition, null)); // Regular move
            }
        }

        // Double forward move if pawn's first move
        if ((row == 2 && direction == 1) || (row == 7 && direction == -1)) {
            ChessPosition doubleMovePosition = new ChessPosition(row + 2 * direction, col);
            if (isWithinBounds(doubleMovePosition) && board.getPiece(doubleMovePosition) == null && board.getPiece(targetPosition) == null) {
                moves.add(new ChessMove(position, doubleMovePosition, null));
            }
        }
    }

    void addCaptureMoves(ArrayList<ChessMove> moves, ChessBoard board, ChessPosition position, int direction, ChessPiece piece) {
        int row = position.getRow();
        int col = position.getColumn();

        // Capture diagonally to the right
        ChessPosition rightPosition = new ChessPosition(row + direction, col + 1);
        if (isWithinBounds(rightPosition) && isOpponentPiece(board, position, rightPosition)) {
            if (isPromotionRow(rightPosition, piece.getTeamColor())) {
                addPromotionMoves(moves, position, rightPosition);
            } else {
                moves.add(new ChessMove(position, rightPosition, null)); // Regular capture
            }
        }

        // Capture diagonally to the left
        ChessPosition leftPosition = new ChessPosition(row + direction, col - 1);
        if (isWithinBounds(leftPosition) && isOpponentPiece(board, position, leftPosition)) {
            if (isPromotionRow(leftPosition, piece.getTeamColor())) {
                addPromotionMoves(moves, position, leftPosition);
            } else {
                moves.add(new ChessMove(position, leftPosition, null)); // Regular capture
            }
        }
    }

    private void addPromotionMoves(ArrayList<ChessMove> moves, ChessPosition startPosition, ChessPosition endPosition) {
        ChessPiece.PieceType[] promotionPieces = ChessPiece.PieceType.values();
        for (ChessPiece.PieceType promotionPiece : promotionPieces) {
            if (promotionPiece != ChessPiece.PieceType.PAWN && promotionPiece != ChessPiece.PieceType.KING) {
                moves.add(new ChessMove(startPosition, endPosition, promotionPiece));
            }
        }
    }

    private boolean isWithinBounds(ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        return row >= 1 && row <= 8 && col >= 1 && col <= 8;
    }

    private boolean isOpponentPiece(ChessBoard board, ChessPosition from, ChessPosition to) {
        ChessPiece fromPiece = board.getPiece(from);
        ChessPiece toPiece = board.getPiece(to);
        return toPiece != null && fromPiece.getTeamColor() != toPiece.getTeamColor();
    }

    private boolean isPromotionRow(ChessPosition position, ChessGame.TeamColor teamColor) {
        return (teamColor == ChessGame.TeamColor.WHITE && position.getRow() == 8) ||
                (teamColor == ChessGame.TeamColor.BLACK && position.getRow() == 1);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
