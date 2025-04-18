package chess;

import java.util.ArrayList;
import java.util.Collection;

public abstract class SlidingPieceMoveCalculator implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        for (int[] direction : getMovementDirections()) {
            addMovesInDirection(moves, board, position, direction[0], direction[1]);
        }

        return moves;
    }

    protected void addMovesInDirection(ArrayList<ChessMove> moves, ChessBoard board, ChessPosition position, int rowDir, int colDir) {
        int currentRow = position.getRow();
        int currentCol = position.getColumn();

        while (isWithinBounds(currentRow + rowDir, currentCol + colDir)) {
            currentRow += rowDir;
            currentCol += colDir;

            ChessPosition targetPosition = new ChessPosition(currentRow, currentCol);
            ChessPiece targetPiece = board.getPiece(targetPosition);

            if (targetPiece == null) {
                moves.add(new ChessMove(position, targetPosition, null));
            } else if (targetPiece.getTeamColor() != board.getPiece(position).getTeamColor()) {
                moves.add(new ChessMove(position, targetPosition, null));
                break;
            } else {
                break;
            }
        }
    }

    private boolean isWithinBounds(int row, int col) {
        return row >= 1 && row <= 8 && col >= 1 && col <= 8;
    }

    protected abstract int[][] getMovementDirections(); // Each subclass defines its movement directions
}
