package model;

import chess.ChessGame;

public record JoinGameRequest(ChessGame.TeamColor playerColor, String gameID){
}
