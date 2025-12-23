package pages;

//import pages.components.HomeMovieListing;
import pages.components.HomeMovieSearch;
import pages.components.MovieListingsSection;

public class HomePage extends CommonPage {

    private final HomeMovieSearch movieSearch = new HomeMovieSearch();

    public HomeMovieSearch getMovieSearch() {
        return movieSearch;
    }

    private final MovieListingsSection movieListings = new MovieListingsSection();
    public MovieListingsSection movieListings() { return movieListings; }

}
