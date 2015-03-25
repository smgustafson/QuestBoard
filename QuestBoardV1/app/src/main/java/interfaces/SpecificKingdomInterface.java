package interfaces;

import entities.Kingdom;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Shayla on 3/16/2015.
 *
 * Interface to a specific Kingdom using the REST Api
 */
public interface SpecificKingdomInterface {
    @GET("/api/v1/kingdoms/{id}")
    public void kingdoms(@Path("id") Integer id, Callback<Kingdom> callback);
}
