package interfaces;

import android.provider.SyncStateContract;

import java.util.List;
import java.util.Map;

import entities.Kingdom;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Shayla on 3/14/2015.
 *
 * Interface to access a list of all Kingdoms using the REST Api
 */
public interface KingdomsInterface {
    @GET("/api/v1/kingdoms")
    public void kingdoms(Callback<List<Kingdom>> callback);
}
