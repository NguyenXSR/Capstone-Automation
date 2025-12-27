package pages;

//import pages.components.HomeMovieListing;
import pages.components.HomeMovieSearch;
import pages.components.HomeMovieListingsSection;
import pages.components.HomeMovieSelectorTable;

public class HomePage extends CommonPage {

    private final HomeMovieSearch movieSearch = new HomeMovieSearch();

    public HomeMovieSearch getMovieSearch() {
        return movieSearch;
    }

    private final HomeMovieListingsSection movieListings = new HomeMovieListingsSection();
    public HomeMovieListingsSection movieListings() {
        return movieListings;
    }

    private final HomeMovieSelectorTable movieSelectorTable = new HomeMovieSelectorTable();
    public HomeMovieSelectorTable movieSelectorTable() {
        return movieSelectorTable;}

}
