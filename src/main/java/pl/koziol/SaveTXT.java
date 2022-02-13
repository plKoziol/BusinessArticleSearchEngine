package pl.koziol;

import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.Source;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SaveTXT {
    SQLiteRepository repository = new SQLiteRepository();
    /**
     * This method is responsible for saving the article in the assumed format "Title:Description:Author"
     * Moreover, this method add article to BD
     */
    private void saveArticle(Article article){
        PrintWriter save = null;
        try {
            save = new PrintWriter(new FileWriter("result.txt", true));
            save.println(article.getTitle() + ":"
                    + article.getDescription() + ":"
                    + article.getAuthor());
            Source s = article.getSource();
            s.setCountry("pl");
            s.setCategory("business");
            article.setSource(s);
            repository.insertArticle(article);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(save!= null){
                save.close();
            }
        }

    }
    /**
     * This method is responsible for removing all files from txt document and creating new head with current date and time
     */
    private void documentCleaner () {
        repository.cleanDB();
        FileWriter cleaner = null;
        try {
            cleaner = new FileWriter("result.txt", false);
            cleaner.write("Aktualizacja danych: "
                    + java.time.LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME).toLowerCase()
                    + "\n");
            cleaner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * The method takes a list of articles and saves them to a text file. It is also checked if the list contains at least 1 article
     */
    public void saveArticleList(List<Article> articleList){
        if(articleList.isEmpty()){
            System.out.println("brak artykułów w podanej tematyce");//english translate "no articles on given topic"
        } else {
            documentCleaner();
            articleList.stream().forEach(article -> saveArticle(article));
            System.out.println("artykuły zapisane");//english translate "articles saved"

        }

    }
}
