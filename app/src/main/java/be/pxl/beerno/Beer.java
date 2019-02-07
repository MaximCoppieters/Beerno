package be.pxl.beerno;

public class Beer {
    private String name;
    private int imageId;
    private boolean selected;

    private String description;

    public Beer(String name, int imageId) {
        this(name, imageId, "");
    }

    public Beer(String name, int imageId, String description) {
        this.name = name;
        this.imageId = imageId;
        this.description = description;
    }

    public String getDescription() {
        return description;
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

    public boolean GetSelected() {
        return selected;
    }



    @Override
    public String toString() {
        return "Beer{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}


