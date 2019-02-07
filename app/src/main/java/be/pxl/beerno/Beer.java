package be.pxl.beerno;

import android.net.Uri;

public class Beer {
    private String name;
    private Uri imagePath;
    private boolean selected;
    private int drinkCount;
    private String description;

    public Beer(String name, Uri imagePath) {
        this(name, imagePath, "");
    }

    public Beer(String name, Uri imagePath, String description) {
        this.name = name;
        this.imagePath = imagePath;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Uri getImagePath() {
        return imagePath;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean GetSelected() {
        return selected;
    }

    public void IncrementDrinkCount() {
        drinkCount++;
    }

    public void DecrementDrinkCount() {
        if (drinkCount > 0) {
            drinkCount--;
        }
    }

    @Override
    public String toString() {
        return "Beer{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}


