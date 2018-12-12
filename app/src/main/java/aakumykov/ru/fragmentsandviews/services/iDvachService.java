package aakumykov.ru.fragmentsandviews.services;


import java.util.List;
import java.util.Map;

import aakumykov.ru.fragmentsandviews.models.Board.Board;
import aakumykov.ru.fragmentsandviews.models.BoardsList.BoardsTOCItem;

public interface iDvachService {

    void getBoardsList(TOCReadCallbacks callbacks);

    void getBoard(String boardName, BoardReadCallbacks callbacks);

    interface TOCReadCallbacks {
        void onTOCReadSuccess(Map<String, List<BoardsTOCItem>> tocMap);
        void onTOCReadFail(String errorMsg);
    }

    interface BoardReadCallbacks {
        void onBardReadSuccess(Board board);
        void onBoardReadFail(String errorMsg);
    }
}