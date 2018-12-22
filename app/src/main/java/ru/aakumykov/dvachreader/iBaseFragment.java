package ru.aakumykov.dvachreader;

import ru.aakumykov.dvachreader.interfaces.iPage;

public interface iBaseFragment {

    void showProgressMessage(int messageId);
    void hideProgressMessage();

    void showToast(int messageId);
    void showToast(String message);

    void showErrorMsg(int messageId);
    void showErrorMsg(int messageId, String messageForConsole);

    iPage getPage();
}
