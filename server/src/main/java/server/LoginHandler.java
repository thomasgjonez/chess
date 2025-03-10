package server;

import com.google.gson.Gson;
import model.ApiResponse;
import model.AuthData;
import model.LoginRequest;
import service.LoginService;
import spark.Request;
import spark.Response;

public class LoginHandler extends BaseHandler{
    private final LoginService loginService = new LoginService();

    public Object handleRequest(Request req, Response res){
        try{
            LoginRequest loginRequest = new Gson().fromJson(req.body(), LoginRequest.class);

            if (loginRequest.username() == null || loginRequest.password() == null) {
                res.status(401);
                return toJson(new ApiResponse("Error: unauthorized"));
            }

            AuthData loginResult = loginService.login(loginRequest);

            if (loginResult != null) {
                res.status(200);
                return toJson(loginResult);
            } else {
                res.status(401);
                return toJson(new ApiResponse("Error: unauthorized"));
            }

        } catch (Exception e) {
            return handleException(res, e);
        }
    }
}
