package server;

import com.google.gson.Gson;
import model.ClearResult;
import model.ErrorResponse;
import service.ClearService;
import spark.Request;
import spark.Response;

public class ClearHandler {

    public Object handleRequest(Request req, Response res){
        try {

            ClearService clearService = new ClearService();
            ClearResult clearResult = clearService.clear();
            return new Gson().toJson(clearResult);
        } catch (Exception e) {
                res.status(500); // Internal Server Error
                return new Gson().toJson(new ErrorResponse("Internal server error"));
        }
    }
}
