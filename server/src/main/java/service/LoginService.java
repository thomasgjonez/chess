package service;

import dataaccess.AuthDAO;
import dataaccess.DatabaseManager;
import dataaccess.UserDAO;
import model.AuthData;
import model.LoginRequest;
import model.UserData;

import java.sql.Connection;

public class LoginService extends BaseService{
    public AuthData login(LoginRequest request) {
        try {
            if (UserDAO.verifyPassword(request.username(),request.password())){
                String authToken = generateAuthToken();
                AuthDAO.createAuth(request.username(), authToken);

                return new AuthData(request.username(), authToken);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
