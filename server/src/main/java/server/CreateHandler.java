package server;

import com.google.gson.Gson;
import model.CreateRequest;
import model.CreateResponse;
import model.ErrorResponse;
import service.CreateService;
import spark.Request;
import spark.Response;

public class CreateHandler extends BaseHandler{
    private final CreateService createService = new CreateService();

    public Object handleRequest(Request req, Response res){
        try{
            String authToken = req.headers("Authorization");
            CreateRequest createRequest = new Gson().fromJson(req.body(), CreateRequest.class);

            if (authToken == null) {
                res.status(401);
                return toJson(new ErrorResponse("Error: unauthorized"));
            }

            if (createRequest == null || createRequest.gameName() == null) {
                res.status(400);
                return toJson(new ErrorResponse("Error: bad request"));
            }

            CreateResponse createResponse = createService.create(authToken, createRequest);

            if (createResponse.gameID() != null) {
                res.status(200);
                return toJson(createResponse);
            } else {
                if ("Error: unauthorized".equals(createResponse.message())) {
                    res.status(401);
                } else {
                    res.status(400); // General bad request
                }
                return toJson(new ErrorResponse(createResponse.message()));
            }

        }catch (Exception e) {
            return handleException(res, e);
        }
    }
}
