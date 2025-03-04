package server;

import model.ApiResponse;
import service.LogoutService;
import spark.Request;
import spark.Response;

public class LogoutHandler extends BaseHandler{
    private final LogoutService logoutService = new LogoutService();

    public Object handleRequest(Request req, Response res){
        try{
            String authToken = getValidAuthToken(req, res);
            //maybe just get rid of this LogoutRequest since its just an authToken??
            ApiResponse logoutResult = logoutService.logout(authToken);

            if (logoutResult.message() != null) {
                res.status(401);
                return toJson(new ApiResponse(logoutResult.message()));
            }

            res.status(200); // Success
            return "{}";
        } catch (Exception e) {
            return handleException(res, e);
        }
    }
}
