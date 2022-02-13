package pl.koziol;

import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.Source;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class SQLiteRepository {
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:identifier.sqlite";
    private Connection connection;
    private Statement statement;

    public SQLiteRepository(){
        try {
            Class.forName(SQLiteRepository.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC driver missing");
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(DB_URL);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.err.println("open connection problem");
            e.printStackTrace();
        }
        createTables();
    }
    public boolean createTables()  {
        String createTableArticles = "create table if not exists table_articles\n" +
                "(\n" +
                "    id           integer\n" +
                "        constraint table_articles_pk\n" +
                "            primary key,\n" +
                "    title        text,\n" +
                "    author       text,\n" +
                "    description  text,\n" +
                "    published_at text,\n" +
                "    url          text,\n" +
                "    url_image    text,\n" +
                "    category     text,\n" +
                "    country      text\n" +
                ");";

        try {
            statement.execute(createTableArticles);
        } catch (SQLException e) {
            System.err.println("Error creating table");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Set<Article> getArticlesForDB() {
        Set<Article> articles = new HashSet<>();
        try {
            ResultSet resultSet = statement.executeQuery(
                    "select * from table_articles");
            String title, author, description, publishedAt, url, urlImage, category, country;
            while (resultSet.next()){
                title =resultSet.getString("title");
                author =resultSet.getString("author");
                description =resultSet.getString("description");
                publishedAt =resultSet.getString("published_at");
                url =resultSet.getString("url");
                urlImage =resultSet.getString("url_image");
                category =resultSet.getString("category");
                country = resultSet.getString("country");
                Article a = new Article();
                a.setTitle(title);
                a.setAuthor(author);
                a.setDescription(description);
                a.setPublishedAt(publishedAt);
                a.setUrl(url);
                a.setUrlToImage(urlImage);
                Source s = new Source();
                s.setCategory(category);
                s.setCountry(country);
                a.setSource(s);
                articles.add(a);
            }
        } catch (SQLException e) {
            System.err.println("select completed processes error");
            e.printStackTrace();
            return null;
        }
        return articles;
    }

    public void insertArticle(Article article) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into table_articles values (?, ?, ?, ?, ?, ?, ?, ?, ?);");
            preparedStatement.setInt(1, article.hashCode());
            preparedStatement.setString(2, article.getTitle());
            preparedStatement.setString(3, article.getAuthor());
            preparedStatement.setString(4, article.getDescription());
            preparedStatement.setString(5, article.getPublishedAt());
            preparedStatement.setString(6, article.getUrl());
            preparedStatement.setString(7, article.getUrlToImage());
            preparedStatement.setString(8, article.getSource().getCategory());
            preparedStatement.setString(9, article.getSource().getCountry());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.err.println("input error");
            e.printStackTrace();
        }
    }
    public void cleanDB(){
        try {
            statement.execute("delete from table_articles");
        } catch (SQLException e) {
            System.err.println("delete error");
            e.printStackTrace();
        }


    }
}
