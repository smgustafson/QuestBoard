package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import entities.Kingdom;
import interfaces.KingdomsInterface;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by Shayla on 3/16/2015.
 *
 * {@Link RestClient} associated with the {@Link KingdomsInterface}
 */
public class RestClientKingdoms {
    private static final String BASE_URL = "https://challenge2015.myriadapps.com";
    private KingdomsInterface kingdomsInterface;

    public RestClientKingdoms()
    {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        kingdomsInterface = restAdapter.create(KingdomsInterface.class);
    }

    public KingdomsInterface getKingdomsInterface()
    {
        return kingdomsInterface;
    }
}

