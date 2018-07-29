package aviee.footballscout.pojo;

public class MenuItem {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MenuItem()
    {
        this.name = "";
    }

    public MenuItem(String menuItem){
        this.name = menuItem;
    }
}
