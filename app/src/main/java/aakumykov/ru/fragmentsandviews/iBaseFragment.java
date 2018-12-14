package aakumykov.ru.fragmentsandviews;

import aakumykov.ru.fragmentsandviews.interfaces.iPage;

public interface iBaseFragment {

    void showProgressMessage(int messageId);
    void hideProgressMessage();

    void showToast(int messageId);

    void showErrorMsg(int messageId);
    void showErrorMsg(int messageId, String messageForConsole);

    iPage getPage();
}
