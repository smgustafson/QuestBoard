package entities;

import entities.Giver;

/**
 * Created by Shayla on 3/14/2015.
 *
 * Entity to represent a quest within a kingdom
 */
public class Quest {
    private int id;
    private String name;
    private String description;
    private String image;
    private Giver giver;

    public Quest() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Giver getGiver() {
        return giver;
    }

    public void setGiver(Giver giver) {
        this.giver = giver;
    }
}
