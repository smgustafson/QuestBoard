package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import interfaces.UsersInterface;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Shayla on 3/22/2015.
 *
 * {@Link RestClient} associated with the {@Link UsersInterface}
 */
public class RestClientUsers {
    private static final String BASE_URL = "https://challenge2015.myriadapps.com";
    private UsersInterface usersInterface;

    public RestClientUsers()
    {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        usersInterface = restAdapter.create(UsersInterface.class);
    }

    public UsersInterface getUsersInterface()
    {
        return usersInterface;
    }
}
