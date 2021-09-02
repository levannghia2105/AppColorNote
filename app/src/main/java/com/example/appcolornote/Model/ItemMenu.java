package com.example.appcolornote.Model;

public class ItemMenu {
    private String nameMenu ;
    private int iconMenu ;

    public ItemMenu(String nameMenu, int iconMenu) {
        this.nameMenu = nameMenu;
        this.iconMenu = iconMenu;
    }

    public String getNameMenu() {
        return nameMenu;
    }

    public void setNameMenu(String nameMenu) {
        this.nameMenu = nameMenu;
    }

    public int getIconMenu() {
        return iconMenu;
    }

    public void setIconMenu(int iconMenu) {
        this.iconMenu = iconMenu;
    }
}
