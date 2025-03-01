package server;

import spark.Request;
import spark.Response;

public class ClearHandler {

    public Object handleRequest(Request req, Response res){
        return "{ \"message\": \"clear Handler was called\" }";
    }
}
