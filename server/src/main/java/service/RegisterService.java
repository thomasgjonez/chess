package service;

import dataaccess.UserDAO;
import dataaccess.AuthDAO;
import model.AuthData;
import model.RegisterRequest;





public class RegisterService extends BaseService{
    public AuthData register(RegisterRequest request) {
        // Validate if user exists, store user in database, return success or failure
        if (UserDAO.userExists(request.username())) {
            return new AuthData(null, null);
        }
        String authToken = generateAuthToken();

        UserDAO.createUser(request.username(), request.password(), request.email());
        AuthDAO.createAuth(request.username(), authToken);

        return new AuthData(request.username(), authToken);
    }

}
