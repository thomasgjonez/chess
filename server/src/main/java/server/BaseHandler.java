package server;

import com.google.gson.Gson;
import model.ApiResponse;
import spark.Response;

public abstract class BaseHandler {
    private final Gson gson = new Gson();

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