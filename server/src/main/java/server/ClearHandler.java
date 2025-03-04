package server;

import model.ApiResponse;
import service.ClearService;
import spark.Request;
import spark.Response;

public class ClearHandler extends BaseHandler {

    public Object handleRequest(Request req, Response res){
        try {
            new ClearService().clear();
            res.status(200);
            return toJson(new ApiResponse(null));
        } catch (Exception e) {
            return handleException(res, e);
        }
    }
}
