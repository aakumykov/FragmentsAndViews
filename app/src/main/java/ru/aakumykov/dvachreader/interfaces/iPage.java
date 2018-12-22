package ru.aakumykov.dvachreader.interfaces;

public interface iPage {

    void setPageTitle(int titleId);
    void setPageTitle(String title);

    void activateUpButton();
    void disactivateUpButton();
}
