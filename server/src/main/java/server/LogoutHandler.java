package server;

import com.google.gson.Gson;
import model.ClearResult;
import model.ErrorResponse;
import model.LogoutRequest;
import service.LogoutService;
import spark.Request;
import spark.Response;

public class LogoutHandler extends BaseHandler{
    private final LogoutService logoutService = new LogoutService();

    public Object handleRequest(Request req, Response res){
        try{
            String authToken = req.headers("Authorization");

            if (authToken == null){
                res.status(401);
                return toJson(new ErrorResponse("Error: unauthorized"));
            }
            //maybe just get rid of this LogoutRequest since its just an authToken??
            LogoutRequest logoutRequest = new LogoutRequest(authToken);
            ClearResult logoutResult = logoutService.logout(logoutRequest);

            if (logoutResult.message() != null) {
                res.status(401);
                return toJson(new ErrorResponse(logoutResult.message()));
            }

            res.status(200); // Success
            return "{}";
        } catch (Exception e) {
            return handleException(res, e);
        }
    }
}
