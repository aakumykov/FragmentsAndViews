package aakumykov.ru.fragmentsandviews.interfaces;

public interface iPage {

    void setPageTitle(int titleId);
    void setPageTitle(String title);

    void activateUpButton();
    void disactivateUpButton();

    void changeMenuIcon(int menuId, int iconDrawableId);
}
