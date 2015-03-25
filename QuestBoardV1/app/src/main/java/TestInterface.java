import java.util.List;

import entities.Kingdom;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Shayla on 3/16/2015.
 */
public class TestInterface {
        private static final String API_URL = "https://challenge2015.myriadapps.com";

        interface KingdomInter {
            @GET("/api/v1/kingdoms")
            List<Kingdom> allKingdoms();
        }

        interface SpecificInter {
            @GET("/api/v1/kingdoms/{id}")
            Kingdom kingdom(@Path("id") Integer id);
        }

        public static void main(String... args) {
            // Create a very simple REST adapter which points the myriad API endpoint.
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(API_URL)
                    .build();

            // Create an instance of our myriad API interface.
            KingdomInter kingdomInter = restAdapter.create(KingdomInter.class);

            // Fetch and print a list of the contributors to this library.
            List<Kingdom> allKingdoms = kingdomInter.allKingdoms();
            for (Kingdom  x: allKingdoms) {
                System.out.println(x.getName() + ", " + x.getId() + ", " + x.getImage());
            }

            SpecificInter spec = restAdapter.create(SpecificInter.class);

            Kingdom king = spec.kingdom(2);
            System.out.println(king.getName() + ", " + king.getQuests().size());
        }
}
