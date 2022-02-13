package pl.koziol;

public class Main {

    /**
     * This method is dedicated to starting the process.
     * The process is automatic due to the fact that the parameters are assumed in the task.
     */

    public static void main(String[] args) {
        SearchForArticles searchForArticles = new SearchForArticles();
        searchForArticles.startGenerate();

    }

}
