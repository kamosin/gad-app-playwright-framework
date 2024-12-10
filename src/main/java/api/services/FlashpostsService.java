package api.services;

import api.RequestManager;
import api.models.flashpost.FlashpostRequest;
import com.google.gson.JsonParser;
import com.microsoft.playwright.APIResponse;

public class FlashpostsService {

    private final RequestManager requestManager;
    private static final String flashPostsEndpoint = "/api/flashposts";

    public FlashpostsService(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    public APIResponse getFlashposts(){
        return requestManager.get(flashPostsEndpoint);
    }

    public APIResponse getFlashpostsById(int id){
        return requestManager.get(flashPostsEndpoint+"/"+id);
    }

    public APIResponse createFlashpost(FlashpostRequest flashpostRequest){
        return requestManager.post(flashPostsEndpoint, flashpostRequest);
    }

    public int getNumberOfFlashposts(){
        return JsonParser.parseString(getFlashposts().text()).getAsJsonArray().size();
    }
}
