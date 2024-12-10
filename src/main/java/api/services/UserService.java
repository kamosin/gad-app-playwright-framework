package api.services;

import api.RequestManager;
import api.models.User;
import com.google.gson.JsonParser;
import com.microsoft.playwright.APIResponse;

public class UserService {

    private final RequestManager requestManager;
    private static final String usersEndpoint = "/api/users";

    public UserService(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    public APIResponse getUsers(){
        return requestManager.get(usersEndpoint);
    }

    public APIResponse createUser(User userRequest){
        return requestManager.post(usersEndpoint, userRequest);
    }

    public APIResponse getUserById(int userId){
        return requestManager.get(usersEndpoint+"/"+userId);
    }

    public int getNumberOfUsers(){
        return JsonParser.parseString(getUsers().text()).getAsJsonObject().getAsJsonArray().size();
    }

}
