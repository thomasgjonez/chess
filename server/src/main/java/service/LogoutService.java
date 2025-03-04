package service;

import model.ApiResponse;
import dataaccess.AuthDAO;


public class LogoutService extends BaseService{
    public ApiResponse logout(String authToken){
        try {
            if (!AuthDAO.isValidAuth(authToken)) {
                return new ApiResponse("Error: unauthorized");
            }

            AuthDAO.deleteAuth(authToken);
            return new ApiResponse(null);

        } catch (Exception e) {
            return new ApiResponse("Error: internal server error");
        }
    }
}
