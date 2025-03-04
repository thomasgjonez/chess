package server;

import com.google.gson.Gson;
import model.JoinGameRequest;
import model.ApiResponse;
import service.JoinGameService;
import spark.Request;
import spark.Response;

public class JoinGameHandler extends BaseHandler{
    private final JoinGameService joinGameService = new JoinGameService();

    public Object handleRequest(Request req, Response res){
        try{
            String authToken = getValidAuthToken(req, res);
            JoinGameRequest joinGameRequest = new Gson().fromJson(req.body(), JoinGameRequest.class);

            if (joinGameRequest == null || joinGameRequest.gameID() == null || joinGameRequest.playerColor() == null) {
                res.status(400);
                return toJson(new ApiResponse("Error: bad request"));
            }

            ApiResponse joinGameResult = joinGameService.joinGame(authToken,joinGameRequest );

            if(joinGameResult.message() == null){
                res.status(200);
                return "{}";
            }
            if ("Error: unauthorized".equals(joinGameResult.message())) {
                res.status(401);
            } else if ("Error: already taken".equals(joinGameResult.message())) {
                res.status(403);
            } else {
                res.status(400);
            }

            return toJson(new ApiResponse(joinGameResult.message()));
        } catch (Exception e) {
            return handleException(res, e);
        }
    }
}
