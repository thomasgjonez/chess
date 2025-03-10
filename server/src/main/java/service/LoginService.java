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
        try (Connection conn = DatabaseManager.getConnection()) {
            UserDAO userDAO = new UserDAO(conn);

            if (userDAO.verifyPassword(request.username(),request.password())){
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
