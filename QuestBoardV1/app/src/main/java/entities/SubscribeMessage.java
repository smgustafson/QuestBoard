package entities;

/**
 * Created by Shayla on 3/22/2015.
 *
 * Entity to represent the response of a POST to register a new user using the REST Api
 */
public class SubscribeMessage {
    private String message;

    public SubscribeMessage() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
