package server;

import service.JoinService;
import spark.Request;
import spark.Response;

public class JoinHandler extends BaseHandler{
    private final JoinService joinService = new JoinService();

    public Object handleRequest(Request req, Response res){
        return null;
    }
}
