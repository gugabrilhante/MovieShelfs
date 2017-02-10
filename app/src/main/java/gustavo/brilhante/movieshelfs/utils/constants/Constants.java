package gustavo.brilhante.movieshelfs.utils.constants;

/**
 * Created by Gustavo on 04/02/17.
 */

public class Constants {

    public static String baseUrl = "http://www.omdbapi.com/";

    public static String searchMock = "{\"Search\":[{\"Title\":\"Batman: Mask of the Phantasm\",\"Year\":\"1993\",\"imdbID\":\"tt0106364\",\"Type\":\"movie\",\"Poster\":\"https://images-na.ssl-images-amazon.com/images/M/MV5BODE0YTBiMjQtNWQyZC00YTNjLWJmYjAtMWUwNzI4NGVlZTAzL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_SX300.jpg\"},{\"Title\":\"Batman: The Killing Joke\",\"Year\":\"2016\",\"imdbID\":\"tt4853102\",\"Type\":\"movie\",\"Poster\":\"https://images-na.ssl-images-amazon.com/images/M/MV5BODcxYTc5NmQtZTZjNS00MjRiLTgxMjQtN2VhYjY2YjdmMzYzXkEyXkFqcGdeQXVyNjUwNzk3NDc@._V1_SX300.jpg\"},{\"Title\":\"Batman: The Movie\",\"Year\":\"1966\",\"imdbID\":\"tt0060153\",\"Type\":\"movie\",\"Poster\":\"https://images-na.ssl-images-amazon.com/images/M/MV5BMTkzODAyMjg2Ml5BMl5BanBnXkFtZTgwMzI4NzM1MjE@._V1_SX300.jpg\"},{\"Title\":\"Batman: Year One\",\"Year\":\"2011\",\"imdbID\":\"tt1672723\",\"Type\":\"movie\",\"Poster\":\"https://images-na.ssl-images-amazon.com/images/M/MV5BMjI0NjM0MDQxMF5BMl5BanBnXkFtZTgwNzQzOTAwNzE@._V1_SX300.jpg\"},{\"Title\":\"Batman: Gotham Knight\",\"Year\":\"2008\",\"imdbID\":\"tt1117563\",\"Type\":\"movie\",\"Poster\":\"https://images-na.ssl-images-amazon.com/images/M/MV5BMTQ1NjExODcyNl5BMl5BanBnXkFtZTcwMTk0MDc4MQ@@._V1_SX300.jpg\"},{\"Title\":\"Batman: Assault on Arkham\",\"Year\":\"2014\",\"imdbID\":\"tt3139086\",\"Type\":\"movie\",\"Poster\":\"https://images-na.ssl-images-amazon.com/images/M/MV5BMTllYjcyMjQtZGY0Mi00ZjI0LThiYmYtZWI2MDUyMmY0OWE5XkEyXkFqcGdeQXVyNTAyNDQ2NjI@._V1_SX300.jpg\"},{\"Title\":\"Batman Beyond\",\"Year\":\"1999–2001\",\"imdbID\":\"tt0147746\",\"Type\":\"series\",\"Poster\":\"https://images-na.ssl-images-amazon.com/images/M/MV5BMTA3MjkzNDM0OTBeQTJeQWpwZ15BbWU3MDk3ODM5MjE@._V1._CR1,0,208,299_SY132_CR1,0,89,132_AL_.jpg_V1_SX300.jpg\"},{\"Title\":\"Batman: Arkham City\",\"Year\":\"2011\",\"imdbID\":\"tt1568322\",\"Type\":\"game\",\"Poster\":\"http://ia.media-imdb.com/images/M/MV5BZDhjZTVkZGYtNWFmMC00MDU5LWIxZjEtNzQ4NjU1OTNlNTJlXkEyXkFqcGdeQXVyNTM3NzExMDQ@._V1_SX300.jpg\"},{\"Title\":\"Superman/Batman: Apocalypse\",\"Year\":\"2010\",\"imdbID\":\"tt1673430\",\"Type\":\"movie\",\"Poster\":\"https://images-na.ssl-images-amazon.com/images/M/MV5BMTYzMDU0MjEwOF5BMl5BanBnXkFtZTcwOTA5ODc2Mw@@._V1_SX300.jpg\"},{\"Title\":\"Batman Beyond: Return of the Joker\",\"Year\":\"2000\",\"imdbID\":\"tt0233298\",\"Type\":\"movie\",\"Poster\":\"https://images-na.ssl-images-amazon.com/images/M/MV5BMTQ4MDQwMDUxNF5BMl5BanBnXkFtZTcwODY2NDEyMQ@@._V1_SX300.jpg\"}],\"totalResults\":\"325\",\"Response\":\"True\"}";
    public static String serieMock = "{\"Title\":\"Game of Thrones\",\"Year\":\"2011–\",\"Rated\":\"TV-MA\",\"Released\":\"17 Apr 2011\",\"Runtime\":\"56 min\",\"Genre\":\"Adventure, Drama, Fantasy\",\"Director\":\"N/A\",\"Writer\":\"David Benioff, D.B. Weiss\",\"Actors\":\"Peter Dinklage, Lena Headey, Emilia Clarke, Kit Harington\",\"Plot\":\"Nine noble families fight for control over the mythical lands of Westeros. Meanwhile, a forgotten race hell-bent on destruction returns after being dormant for thousands of years.\",\"Language\":\"English\",\"Country\":\"USA, UK\",\"Awards\":\"Won 1 Golden Globe. Another 224 wins & 393 nominations.\",\"Poster\":\"https://images-na.ssl-images-amazon.com/images/M/MV5BMjM5OTQ1MTY5Nl5BMl5BanBnXkFtZTgwMjM3NzMxODE@._V1_SX300.jpg\",\"Metascore\":\"N/A\",\"imdbRating\":\"9.5\",\"imdbVotes\":\"1,113,297\",\"imdbID\":\"tt0944947\",\"Type\":\"series\",\"totalSeasons\":\"8\",\"Response\":\"True\"}";


    public static String getBaseUrl(String url){
        return baseUrl + url;
    }

}
