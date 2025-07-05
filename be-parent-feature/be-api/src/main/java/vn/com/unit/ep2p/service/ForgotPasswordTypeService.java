package vn.com.unit.ep2p.service;

import java.util.concurrent.ExecutionException;

public interface ForgotPasswordTypeService {

    public void createForgotType(String key, String type);

    public String getForgotType(String key);

    public boolean checkForgotType(String key, String typeCheck) throws ExecutionException;
}
