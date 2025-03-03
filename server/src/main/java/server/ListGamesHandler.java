package server;

import model.ErrorResponse;
import model.ListGamesResult;
import service.ListGamesService;
import spark.Request;
import spark.Response;

public class ListGamesHandler extends BaseHandler{
    private final ListGamesService listGamesService = new ListGamesService();

    public Object handleRequest(Request req, Response res){
        try{
            String authToken = req.headers("Authorization");

            if (authToken == null){
                res.status(401);
                return toJson(new ErrorResponse("Error: unauthorized"));
            }
            ListGamesResult listGamesResult = listGamesService.listGames(authToken);

            if(listGamesResult != null){
                res.status(200);
                return toJson(listGamesResult);
            } else{
                res.status(401);
                return toJson(new ErrorResponse("Error: unauthorized"));
            }


        } catch (Exception e) {
            return handleException(res, e);
        }
    }
}
