package ru.aakumykov.dvachreader.interfaces;

import ru.aakumykov.dvachreader.interfaces.iPage;

public interface iFragment {

    void showProgressMessage(int messageId);
    void hideProgressMessage();

    void showToast(int messageId);
    void showToast(String message);

    void showErrorMsg(int messageId);
    void showErrorMsg(int messageId, String messageForConsole);

    iPage getPage();
}
