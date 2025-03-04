package server;

import com.google.gson.Gson;
import model.ApiResponse;
import spark.Response;
import spark.Request;

public abstract class BaseHandler {
    private final Gson gson = new Gson();

    // if authToken is valid as in not null send it back
    protected String getValidAuthToken(Request req, Response res) {
        String authToken = req.headers("Authorization");
        if (authToken == null) {
            res.status(401);
            throw new RuntimeException(toJson(new ApiResponse("Error: unauthorized")));
        }
        return authToken;
    }

    // converts objects to jsons
    protected String toJson(Object obj) {
        return gson.toJson(obj);
    }

    // handle exceptions and return standarized error messages
    protected String handleException(Response res, Exception e) {
        res.status(500);
        return toJson(new ApiResponse("Error: " + e.getMessage()));
    }
}