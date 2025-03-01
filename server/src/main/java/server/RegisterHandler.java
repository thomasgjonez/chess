package server;

import com.google.gson.Gson;
import service.RegisterService;
import spark.Request;
import spark.Response;

public class RegisterHandler {

    public Object handleRequest(Request req, Response res){
        try {
            Gson gson = new Gson();
            RegisterRequest registerRequest = gson.fromJson(req.body(), RegisterRequest.class);

            if (registerRequest.username() == null || registerRequest.password() == null || registerRequest.email() == null) {
                res.status(500);
                return gson.toJson(new ErrorResponse("Missing required fields"));
            }
            RegisterService registerService = new RegisterService();
            RegisterResult registerResult = registerService.register(registerRequest);
            return gson.toJson(registerResult);
        } catch (Exception e) {
            res.status(500); // Internal Server Error
            return new Gson().toJson(new ErrorResponse("Internal server error"));
        }

    }

}
