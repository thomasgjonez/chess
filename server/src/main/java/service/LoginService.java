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

            if (user == null) {
                return new AuthData(null,null);
            }

            if (!user.password().equals(request.password())) {
                return new AuthData(null, null);
            }

            String authToken = generateAuthToken();
            AuthDAO.createAuth(request.username(), authToken);

            return new AuthData(request.username(), authToken);
        } catch (Exception e) {
            return new AuthData(null, "Error: internal server error");
        }
    }
}
