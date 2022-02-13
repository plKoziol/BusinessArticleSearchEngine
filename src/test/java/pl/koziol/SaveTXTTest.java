package pl.koziol;

import com.kwabenaberko.newsapilib.models.Article;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;



class SaveTXTTest {
    static SaveTXT saveTXT = new SaveTXT();
    static List<Article> articles = new ArrayList<>();
    @BeforeAll
    static void listArticleCreator(){
        for(int i = 0; i < 10; i++){
            Article article = new Article();
            article.setAuthor("author " + i);
            article.setDescription("description " + i);
            article.setTitle("title " + i);
            articles.add(article);
        }
    }


    @Test
    void saveArticleList() {
        File file = new File("result.txt");
        saveTXT.saveArticleList(articles);
        List<String> list = new ArrayList<>();
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()){
                list.add(reader.nextLine());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(articles.size(),list.size()-1);//-1 because method saveArticleList add 1 line about date and time
        for(int i = 0; i < 10; i++){
            String articleString = articles.get(i).getTitle() + ":"
                    + articles.get(i).getDescription() + ":"
                    + articles.get(i).getAuthor();
            assertTrue(list.get(i+1).equals(articleString));
        }
    }
}