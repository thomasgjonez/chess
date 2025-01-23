package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMoveCalculator implements PieceMovesCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        addMovesInDirection(moves, board, position,-2,1 );//UpRight
        addMovesInDirection(moves, board, position,-2,-1 );//UpLeft
        addMovesInDirection(moves, board, position,-1,2 );//RightUp
        addMovesInDirection(moves, board, position,1,2 ); //RightDown
        addMovesInDirection(moves, board, position,-1,-2 );//LeftUp
        addMovesInDirection(moves, board, position,1,-2 );//LeftDown
        addMovesInDirection(moves, board, position,2,1 );//DownRight
        addMovesInDirection(moves, board, position,2,-1 );//DownLeft
        return moves;
    }

    private void addMovesInDirection(ArrayList<ChessMove> moves, ChessBoard board, ChessPosition position, int rowDir, int colDir) {
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