package testcases.tests.movie;


//*********************************************
//
//                Thumbnail Tests
//
//*********************************************

import edu.uci.ics.cs122b.test.common.Result;
import edu.uci.ics.cs122b.test.util.ServiceResponse;
import org.junit.Test;
import testcases.model.hw3.response.ThumbnailResponseModel;
import testcases.model.hw3.submodels.ThumbnailModel;
import testcases.socket.MovieSocket;

import static junit.framework.TestCase.assertEquals;

public class MovieTestThumbnail{
    @Test
    public  void validPrivilege_ThumbnailRequestThreeMovies()
    {
        Result expectedResult = Result.MOVIES_FOUND;

        String[] movie_ids = {"tt0007507","tt0018806","tt0019130"};

        ServiceResponse<ThumbnailResponseModel> response = MovieSocket.getThumbnail(movie_ids);
        ThumbnailModel[] respThumbnails = response.getEntity().getThumbnails();
        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(respThumbnails.length, 3);
        assertEquals(respThumbnails[0].getMovie_id(), "tt0007507");
        assertEquals(respThumbnails[2].getMovie_id(), "tt0019130");
        assertEquals(respThumbnails[1].getBackdrop_path(), "/l1VH0FCTMaeduwO1k78wQ1xBBm3.jpg");
    }


    @Test
    public void validPrivilege_ThumbnailRequestOneMovie()
    {
        Result expectedResult = Result.MOVIES_FOUND;
        String[] movie_ids = {"tt0037248"};

        ServiceResponse<ThumbnailResponseModel> response = MovieSocket.getThumbnail(movie_ids);
        ThumbnailModel[] respThumbnails = response.getEntity().getThumbnails();
        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(1, respThumbnails.length);
        assertEquals(respThumbnails[0].getTitle(), "The Scarlet Claw");
        assertEquals(respThumbnails[0].getPoster_path(),"/bxSNJPcF8dd1au7maOatScDPkEI.jpg");
    }

    @Test
    public void validPrivilege_ThumbnailRequestTwoMovieFound()
    {
        Result expectedResult = Result.MOVIES_FOUND;
        String[] movie_ids = {"tt0037303", "ii123213", "kk123123", "asd12323", "tt0046303"};

        ServiceResponse<ThumbnailResponseModel> response = MovieSocket.getThumbnail(movie_ids);
        ThumbnailModel[] respThumnails = response.getEntity().getThumbnails();
        assertEquals(expectedResult.getStatus(), response.getStatus());
        assertEquals(2,respThumnails.length);
        assertEquals(respThumnails[0].getPoster_path(), "/7SasjHkOK7QpqmRrOi0nqFcMLzf.jpg");
        assertEquals("The Spider Woman", respThumnails[0].getTitle());
    }

}
