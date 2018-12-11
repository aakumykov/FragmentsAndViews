package aakumykov.ru.fragmentsandviews;

public interface iFragment {

    void showProgressMessage(int messageId);
    void hideProgressMessage();

    void showToast(int messageId);
}
