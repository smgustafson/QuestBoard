package interfaces;

import entities.Kingdom;
import entities.SubscribeMessage;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Shayla on 3/22/2015.
 *
 * Interface to Post a new user to the database using REST Api
 */
public interface UsersInterface {
    @FormUrlEncoded
    @POST("/api/v1/subscribe/")
    public void createUser(@Field("email") String mEmail, Callback<SubscribeMessage> callback);
}
