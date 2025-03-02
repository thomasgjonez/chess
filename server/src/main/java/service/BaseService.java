package service;

import java.util.UUID;

public abstract class BaseService {
    protected String generateAuthToken() {
        return UUID.randomUUID().toString();
    }
}