package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMoveCalculator implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        ChessPiece piece = board.getPiece(position);
        int direction = piece.getTeamColor() == ChessGame.TeamColor.WHITE ? 1 : -1; // Direction of movement, 1 is down, -1 is up

        ForwardMoves(moves, board, position, direction);
        CaptureMoves(moves, board, position, direction);
        return moves;
    };

    void ForwardMoves(ArrayList<ChessMove> moves, ChessBoard board, ChessPosition position, int direction) {
        int row = position.getRow();
        int col = position.getColumn();

        ChessPosition targetPosition = new ChessPosition(row + direction, col);
        //move one space
        if (isWithinBounds(targetPosition) && board.getPiece(targetPosition) == null) {
            moves.add(new ChessMove(position, targetPosition, null));
        }

        // Move two squares forward (only if it's the pawn's first move)
        if ((row == 2 && direction == 1) || (row == 7 && direction == -1)) {
            targetPosition = new ChessPosition(row + direction * 2, col);
            if (isWithinBounds(targetPosition) && board.getPiece(targetPosition) == null) {
                moves.add(new ChessMove(position, targetPosition, null));
            }
        }
    };
    void CaptureMoves(ArrayList<ChessMove> moves, ChessBoard board, ChessPosition position, int direction) {
        int row = position.getRow();
        int col = position.getColumn();

        ChessPosition rightPosition = new ChessPosition(row + direction, col + 1);
        ChessPosition leftPosition = new ChessPosition(row + direction, col - 1);

        if (isWithinBounds(rightPosition) && board.getPiece(rightPosition) != null &&
                board.getPiece(rightPosition).getTeamColor() != board.getPiece(position).getTeamColor()) {
            moves.add(new ChessMove(position, rightPosition, null));
        }

        if (isWithinBounds(leftPosition) && board.getPiece(leftPosition) != null &&
                board.getPiece(leftPosition).getTeamColor() != board.getPiece(position).getTeamColor()) {
            moves.add(new ChessMove(position, leftPosition, null));
        }
    }
    private boolean isWithinBounds(ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        return row >= 1 && row <= 8 && col >= 1 && col <= 8;
    }


}
