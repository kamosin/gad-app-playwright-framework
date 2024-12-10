package api;

import api.models.LoginRequest;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;

import java.util.HashMap;
import java.util.Map;

import static api.TestUtils.getJsonPath;

public class RequestManager {

    private static final String baseUrl = TestUtils.getGlobalValue("baseUrl");
    private static final String loginEndpoint = "/api/login";
    private String token;

    public void setToken(LoginRequest loginRequest) {
        var response = post(loginEndpoint, loginRequest);
        this.token = getJsonPath(response, "access_token");
    }

    public String getToken() {
        return token!=null ? token : "";
    }

    public void logout(){
        this.token = "";
    }

    public APIRequestContext createRequestSpecification() {

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        if (token != null && !token.isEmpty()) {
            headers.put("Authorization", "Bearer " + token);
        }

        return Playwright.create().request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL(baseUrl)
                        .setExtraHTTPHeaders(headers)
                );
    }

    public <T> APIResponse post(String endpoint, T body) {
        return createRequestSpecification()
                .post(endpoint, RequestOptions.create().setData(body));
    }

    public APIResponse get(String endpoint) {
        return createRequestSpecification().get(endpoint);
    }

    public APIResponse delete(String endpoint) {
        return createRequestSpecification().delete(endpoint);
    }
}