package pl.koziol;

import com.kwabenaberko.newsapilib.models.Article;

import java.io.*;
import java.util.List;

public class SaveTXT {
    private void saveArticle(Article article){
        PrintWriter save = null;
        try {
            save = new PrintWriter(new FileWriter("result.txt", true));
            save.println(article.getTitle() + ":"
                    + article.getDescription() + ":"
                    + article.getAuthor());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(save!= null){
                save.close();
            }
        }

    }
    public void saveArticleList(List<Article> articleList){
        if(articleList.isEmpty()){
            System.out.println("brak artykułów w podanej tematyce");
        } else {
            articleList.stream().forEach(article -> saveArticle(article));
            System.out.println("artykuły zapisane");

        }

    }
}
