package ru.aakumykov.dvachreader.interfaces;

public interface iDvachPagesInteraction {

    void showBoardsOnDvach();
    void showThreadsInBoard(String boardId);
    void showCommentsInThread(String boardId, String threadId);

}
