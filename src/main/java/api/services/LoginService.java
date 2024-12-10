package api.services;

import api.RequestManager;
import api.models.LoginRequest;
import com.microsoft.playwright.APIResponse;

public class LoginService {

    private final RequestManager requestManager;
    public static final String loginEndpoint = "/api/login";
    public static final String logoutEndpoint = "/logout";

    public LoginService(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    public APIResponse login(LoginRequest loginRequest){
        return requestManager.post(loginEndpoint, loginRequest);
    }

    public APIResponse logout(){
        requestManager.logout();
        return requestManager.get(logoutEndpoint);
    }
}
