package aakumykov.ru.fragmentsandviews.services;


import java.util.List;
import java.util.Map;

import aakumykov.ru.fragmentsandviews.models.Board.Board;
import aakumykov.ru.fragmentsandviews.models.BoardsList.BoardsTOCItem;
import aakumykov.ru.fragmentsandviews.models.Thread.OneThread;

public interface iDvachService {

    void getBoardsList(TOCReadCallbacks callbacks);

    void getBoard(String boardName, BoardReadCallbacks callbacks);

    void getThread(String boardName, String threadNum, ThreadReadCallbacks callbacks);


    interface TOCReadCallbacks {
        void onTOCReadSuccess(Map<String, List<BoardsTOCItem>> tocMap);
        void onTOCReadFail(String errorMsg);
    }

    interface BoardReadCallbacks {
        void onBardReadSuccess(Board board);
        void onBoardReadFail(String errorMsg);
    }

    interface ThreadReadCallbacks {
        void onThreadReadSuccess(OneThread oneThread);
        void onThreadReadFail(String errorMsg);
    }
}