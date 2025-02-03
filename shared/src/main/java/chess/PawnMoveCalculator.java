package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMoveCalculator implements PieceMovesCalculator{
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position){
        ArrayList<ChessMove> moves = new ArrayList<>();

        int direction = board.getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE ? 1 : -1;

        addForwardMove(moves, board, position, direction);
        addCaptureMove(moves, board, position, direction, 1);//to the right
        addCaptureMove(moves, board, position, direction, -1);// to the left
        return moves;
    }

    public void addForwardMove(ArrayList<ChessMove> moves, ChessBoard board, ChessPosition position, int rowDir){
        int currentRow = position.getRow();
        int currentCol = position.getColumn();

        if(inBounds(currentRow+rowDir, currentCol)){
            ChessPosition targetPos = new ChessPosition(currentRow+rowDir, currentCol);
            if(board.getPiece(targetPos) == null){
                if(isPromotionRow(targetPos, rowDir)){
                    addPromotionMove(moves, board, position, targetPos);
                } else{
                    moves.add(new ChessMove(position, targetPos, null));
                }
            }
        }
        if (isStartingRow(position,rowDir)){
            ChessPosition targetPos = new ChessPosition(currentRow+rowDir, currentCol);
            ChessPosition doublePos = new ChessPosition(currentRow+ 2 * rowDir, currentCol);
            if(board.getPiece(targetPos) == null && board.getPiece(doublePos) == null && inBounds(doublePos.getRow(), doublePos.getColumn())){
                moves.add(new ChessMove(position, doublePos, null));
            }

        }
    }

    public void addCaptureMove(ArrayList<ChessMove> moves, ChessBoard board, ChessPosition position, int rowDir, int colDir){
        int currentRow = position.getRow();
        int currentCol = position.getColumn();

        if(inBounds(currentRow+rowDir, currentCol+colDir)){
            ChessPosition targetPos = new ChessPosition(currentRow+rowDir, currentCol+colDir);
            if(board.getPiece(targetPos) != null && board.getPiece(position).getTeamColor() != board.getPiece(targetPos).getTeamColor()){
                if(isPromotionRow(targetPos, rowDir)){
                    addPromotionMove(moves, board, position, targetPos);
                } else{
                    moves.add(new ChessMove(position, targetPos, null));
                }
            }
        }

    }

    public void addPromotionMove(ArrayList<ChessMove> moves, ChessBoard board, ChessPosition position, ChessPosition targetPos){
        ChessPiece.PieceType [] promotionPieces = ChessPiece.PieceType.values();
        for(ChessPiece.PieceType promotionPiece : promotionPieces){
            if(promotionPiece != ChessPiece.PieceType.KING && promotionPiece != ChessPiece.PieceType.PAWN){
                moves.add(new ChessMove(position, targetPos, promotionPiece));
            }
        }
    }

    public boolean isStartingRow(ChessPosition position, int rowDir){
        return (position.getRow() == 2 && rowDir == 1) ||
                (position.getRow() == 7 && rowDir == -1);
    }

    public boolean isPromotionRow(ChessPosition targetPos, int rowDir){
        return (targetPos.getRow() == 8 && rowDir == 1) ||
                (targetPos.getRow() == 1 && rowDir == -1);
    }

    public boolean inBounds(int row, int col){
        return row >= 1 && row <= 8 &&
                col >= 1 && col <= 8;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}