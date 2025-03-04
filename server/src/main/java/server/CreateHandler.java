package server;

import com.google.gson.Gson;
import model.ApiResponse;
import model.CreateRequest;
import model.CreateResult;
import service.CreateService;
import spark.Request;
import spark.Response;

public class CreateHandler extends BaseHandler{
    private final CreateService createService = new CreateService();

    public Object handleRequest(Request req, Response res){
        try{
            String authToken = getValidAuthToken(req, res);
            CreateRequest createRequest = new Gson().fromJson(req.body(), CreateRequest.class);

            if (createRequest == null || createRequest.gameName() == null) {
                res.status(400);
                return toJson(new ApiResponse("Error: bad request"));
            }

            CreateResult createResponse = createService.create(authToken, createRequest);

            if (createResponse.gameID() != null) {
                res.status(200);
                return toJson(createResponse);
            } else {
                if ("Error: unauthorized".equals(createResponse.message())) {
                    res.status(401);
                } else {
                    res.status(400); // General bad request
                }
                return toJson(new ApiResponse(createResponse.message()));
            }

        }catch (Exception e) {
            return handleException(res, e);
        }
    }
}
