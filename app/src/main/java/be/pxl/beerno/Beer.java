package be.pxl.beerno;

import android.net.Uri;

import java.nio.file.Path;

public class Beer {
    private String name;
    private Uri imageUri;
    private boolean selected;
    private int drinkCount;
    private String description;

    public Beer(String name, Uri imageUri) {
        this(name, imageUri, "");
    }

    public Beer(String name, Uri imageUri, String description) {
        this.name = name;
        this.imageUri = imageUri;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Uri getImageUri() {
        return imageUri;
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


