package aakumykov.ru.fragmentsandviews;

public interface iBaseFragment {

    void showProgressMessage(int messageId);

    void hideProgressMessage();

    void showToast(int messageId);

    void showErrorMsg(int messageId);
    void showErrorMsg(int messageId, String messageForConsole);
}
