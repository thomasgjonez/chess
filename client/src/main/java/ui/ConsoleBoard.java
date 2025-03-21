package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class ConsoleBoard {
    private final ChessBoard board;
    private final ChessGame.TeamColor playerColor;

    public ConsoleBoard(ChessBoard board, ChessGame.TeamColor playerColor){
        this.board = board;
        this.playerColor = playerColor;

    }

    public void renderBoard() {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);

        int[] rowRange = (playerColor == ChessGame.TeamColor.WHITE) ? rangeDescending(1, 8) : rangeAscending(1, 8);
        int[] colRange = (playerColor == ChessGame.TeamColor.WHITE) ? rangeAscending(1, 8) : rangeDescending(1, 8);

        // Print top column labels with black background
        out.print(SET_BG_COLOR_BLACK + SET_TEXT_COLOR_WHITE + "   ");
        for (int col : colRange) {
            out.print(" " + (char) ('a' + col - 1) + " ");
        }
        out.println("   " + RESET_TEXT_COLOR + RESET_BG_COLOR); // Extra black background at end

        for (int row : rowRange) {
            // Print left row label with black background
            out.print(SET_BG_COLOR_BLACK + SET_TEXT_COLOR_WHITE + " " + row + " " + RESET_TEXT_COLOR + RESET_BG_COLOR);

            for (int col : colRange) {
                ChessPosition pos = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(pos);
                boolean isLightSquare = (row + col) % 2 != 0;// With the way the index is set up you have to do the opposite lol
                printSquare(out, piece, isLightSquare);
            }

            // Print right row label with black background
            out.print(SET_BG_COLOR_BLACK + SET_TEXT_COLOR_WHITE + " " + row + " " + RESET_TEXT_COLOR + RESET_BG_COLOR);
            out.println();
        }

        // Print bottom column labels with black background
        out.print(SET_BG_COLOR_BLACK + SET_TEXT_COLOR_WHITE + "   ");
        for (int col : colRange) {
            out.print(" " + (char) ('a' + col - 1) + " ");
        }
        out.println("   " + RESET_TEXT_COLOR + RESET_BG_COLOR);
    }


    private void printSquare(PrintStream out, ChessPiece piece, boolean isLightSquare) {
        String bgColor = isLightSquare ? SET_BG_COLOR_WHITE : SET_BG_COLOR_LIGHT_GREY;
        String symbol = (piece != null) ? getSymbol(piece) : " ";
        out.print(bgColor + SET_TEXT_COLOR_BLACK);
        out.print(" " + symbol + " ");
        out.print(RESET_BG_COLOR);
    }

    private String getSymbol(ChessPiece piece) {
        return switch (piece.getPieceType()) {
            case KING   -> piece.getTeamColor() == ChessGame.TeamColor.WHITE ? WHITE_KING : BLACK_KING;
            case QUEEN  -> piece.getTeamColor() == ChessGame.TeamColor.WHITE ? WHITE_QUEEN: BLACK_QUEEN;
            case ROOK   -> piece.getTeamColor() == ChessGame.TeamColor.WHITE ? WHITE_ROOK: BLACK_ROOK;
            case BISHOP -> piece.getTeamColor() == ChessGame.TeamColor.WHITE ? WHITE_BISHOP : BLACK_BISHOP;
            case KNIGHT -> piece.getTeamColor() == ChessGame.TeamColor.WHITE ? WHITE_KNIGHT : BLACK_KNIGHT;
            case PAWN   -> piece.getTeamColor() == ChessGame.TeamColor.WHITE ? WHITE_PAWN : BLACK_PAWN;
        };
    }

    private int[] rangeAscending(int start, int end) {
        int[] range = new int[end - start + 1];
        for (int i = 0; i < range.length; i++) range[i] = start + i;
        return range;
    }

    private int[] rangeDescending(int start, int end) {
        int[] range = new int[end - start + 1];
        for (int i = 0; i < range.length; i++) range[i] = end - i;
        return range;
    }
}
