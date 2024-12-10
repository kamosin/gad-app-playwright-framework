package api.models;

public record ArticleRequest(String title,
                             String body,
                             String date,
                             String image){}