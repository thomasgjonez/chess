package chess;

import java.util.ArrayList;
import java.util.Collection;

public abstract class SingleStepPieceMoveCalculator implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> moves = new ArrayList<>();

        for (int[] direction : getMovementDirections()) {
            addSingleMove(moves, board, position, direction[0], direction[1]);
        }

        return moves;
    }

    private void addSingleMove(ArrayList<ChessMove> moves, ChessBoard board, ChessPosition position, int rowDir, int colDir) {
        int targetRow = position.getRow() + rowDir;
        int targetCol = position.getColumn() + colDir;

        ChessPosition targetPosition = new ChessPosition(targetRow, targetCol);

        if (isWithinBounds(targetPosition)) {
            ChessPiece targetPiece = board.getPiece(targetPosition);
            ChessPiece currentPiece = board.getPiece(position);

            if (targetPiece == null || targetPiece.getTeamColor() != currentPiece.getTeamColor()) {
                moves.add(new ChessMove(position, targetPosition, null));
            }
        }
    }

    private boolean isWithinBounds(ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        return row >= 1 && row <= 8 && col >= 1 && col <= 8;
    }

    protected abstract int[][] getMovementDirections();
}
