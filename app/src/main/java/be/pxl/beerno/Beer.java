package be.pxl.beerno;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Beer implements Serializable {
    private String name;
    private int imageId;
    private boolean selected;
    private List<Establishment> establishmentsServedAt;
    private int id;
    private String description;

    public Beer(String name, int imageId) {
        this(name, imageId, "", new ArrayList<Establishment>());
    }

    public Beer(String name, int imageId, String description, List<Establishment> establishmentsServedAt) {
        this.name = name;
        this.imageId = imageId;
        this.description = description;
        this.establishmentsServedAt = establishmentsServedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
public void setDescription(String description){this.description = description; }

    public boolean getSelected() {
        return selected;
    }

    public void associateBeerWithEstablishment(Establishment establishment) {
        establishmentsServedAt.add(establishment);
        establishment.addBeerToMenu(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Beer{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}


