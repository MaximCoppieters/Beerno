package be.pxl.beerno;

import android.net.Uri;

import java.nio.file.Path;

public class Beer {
    private String name;
    private Path imagePath;
    private boolean selected;
    private int drinkCount;
    private String description;

    public Beer(String name, Path imagePath) {
        this(name, imagePath, "");
    }

    public Beer(String name, Path imagePath, String description) {
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

    public Path getImagePath() {
        return imagePath;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean GetSelected() {
        return selected;
    }

    public int getDrinkAmount(){
        return drinkCount;
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


