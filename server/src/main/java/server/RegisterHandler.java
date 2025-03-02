package server;

import com.google.gson.Gson;
import model.AuthData;
import model.ErrorResponse;
import model.RegisterRequest;
import service.RegisterService;
import spark.Request;
import spark.Response;


public class RegisterHandler extends BaseHandler {
    private final RegisterService registerService = new RegisterService();

    public Object handleRequest(Request req, Response res) {
        try {
            RegisterRequest registerRequest = new Gson().fromJson(req.body(), RegisterRequest.class);

            if (registerRequest.username() == null || registerRequest.password() == null || registerRequest.email() == null) {
                res.status(400);
                return toJson(new ErrorResponse("Error: bad request"));
            }

            AuthData registerResult = registerService.register(registerRequest);

            if (registerResult.authToken() != null) {
                res.status(200);
                return toJson(registerResult);
            } else {
                res.status(403);
                return toJson(new ErrorResponse("Error: already taken"));
            }
        } catch (Exception e) {
            return handleException(res, e);
        }
    }
}
