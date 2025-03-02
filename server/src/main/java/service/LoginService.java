package service;

import dataaccess.AuthDAO;
import dataaccess.UserDAO;
import model.AuthData;
import model.LoginRequest;

public class LoginService extends BaseService{
    public AuthData login(LoginRequest request){
        if (UserDAO.userExists(request.username())){
            String authToken = generateAuthToken();

            AuthDAO.createAuth(request.username(), authToken);

            return new AuthData(request.username(), authToken);

        } else {
            return new AuthData(null, null);
        }
    }
}
