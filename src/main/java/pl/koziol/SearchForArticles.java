package pl.koziol;

import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

public class SearchForArticles {
    /**
     * The object is created based on com.kwabenaberko.newsapilib in accordance with newsapi.org
     */
    NewsApiClient newsApiClient = new NewsApiClient("de34650309de4142993937b0bc36d549");
    /**
     * A method that implements basic business logic.
     * We are looking for articles from the business category and about Poland.
     * In case of success, the article list is saved in the txt document.
     */
    public void startGenerate() {
        SaveTXT saveTXT = new SaveTXT();
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .category("business")
                        .country("pl")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        saveTXT.saveArticleList(response.getArticles());
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }
                }
        );
    }

}
