package server;

import com.google.gson.Gson;
import model.ClearResult;
import model.ErrorResponse;
import service.ClearService;
import spark.Request;
import spark.Response;

public class ClearHandler extends BaseHandler {

    public Object handleRequest(Request req, Response res){
        try {
            new ClearService().clear();
            res.status(200);
            return toJson(new ClearResult(null));
        } catch (Exception e) {
            return handleException(res, e);
        }
    }
}
