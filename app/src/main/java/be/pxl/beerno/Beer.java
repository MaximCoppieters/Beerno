package be.pxl.beerno;

public class Beer {
    private String name;
    private String image;
    private boolean selected;
    private int drinkCount;
    private String description;

    public Beer(String name, String image, String description) {
        this.name = name;
        this.image = image;
        this.description = description;

    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
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


