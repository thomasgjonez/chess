package service;

import dataaccess.AuthDAO;
import dataaccess.UserDAO;
import model.AuthData;
import model.LoginRequest;
import model.UserData;

public class LoginService extends BaseService{
    public AuthData login(LoginRequest request) {
        try {
            UserData user = UserDAO.getUser(request.username());


            if (!user.password().equals(request.password())) {
                return null;
            }

            String authToken = generateAuthToken();
            AuthDAO.createAuth(request.username(), authToken);

            return new AuthData(request.username(), authToken);
        } catch (Exception e) {
            return null;
        }
    }
}
