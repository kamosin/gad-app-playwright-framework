package api.services;

import api.RequestManager;
import api.models.ArticleRequest;
import com.google.gson.JsonParser;
import com.microsoft.playwright.APIResponse;

public class ArticlesService {

    private final RequestManager requestManager;
    private static final String articlesEndpoint = "/api/articles";

    public ArticlesService(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    public APIResponse getArticles(){
        return requestManager.get(articlesEndpoint);
    }

    public APIResponse createArticle(ArticleRequest articleRequest){
        return requestManager.post(articlesEndpoint, articleRequest);
    }

    public int getNumberOfArticles(String baseURI){
        return JsonParser.parseString(getArticles().text()).getAsJsonObject().getAsJsonArray().size();
    }
}
