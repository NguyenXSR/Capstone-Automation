package pages;

import pages.components.HomeMovieSearch;

public class HomePage extends CommonPage {

    private HomeMovieSearch movieSearch = new HomeMovieSearch();

    public HomeMovieSearch getMovieSearch() {
        return movieSearch;
    }

}
