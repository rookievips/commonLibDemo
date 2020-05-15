package com.chen.api.widgets.tabhost;

public class TabEntity {
    private String title;
    private int selectedIcon;
    private int unSelectedIcon;

    public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    public String getTabTitle() {
        return title;
    }

    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}