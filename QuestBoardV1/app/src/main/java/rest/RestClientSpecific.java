package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import interfaces.KingdomsInterface;
import interfaces.SpecificKingdomInterface;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Shayla on 3/16/2015.
 *
 * {@Link RestClient} associated with the {@Link SpecificKingdomInterface}
 */
public class RestClientSpecific {
    private static final String BASE_URL = "https://challenge2015.myriadapps.com";
    private SpecificKingdomInterface specificKingdomInterface;

    public RestClientSpecific()
    {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        specificKingdomInterface = restAdapter.create(SpecificKingdomInterface.class);
    }

    public SpecificKingdomInterface getSpecificKingdomInterface()
    {
        return specificKingdomInterface;
    }
}

