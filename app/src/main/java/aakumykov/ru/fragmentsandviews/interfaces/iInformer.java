package aakumykov.ru.fragmentsandviews.interfaces;

public interface iInformer {

    void showProgressMessage(int messageId);
    void hideProgressMessage();

    void showToast(int messageId);
    void showToast(String messageId);

    void showErrorMsg(int messageId);
    void showErrorMsg(int messageId, String messageForConsole);

}
