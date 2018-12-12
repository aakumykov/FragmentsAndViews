package aakumykov.ru.fragmentsandviews.services;

import java.util.List;
import java.util.Map;

import aakumykov.ru.fragmentsandviews.models.Board.Board;
import aakumykov.ru.fragmentsandviews.models.BoardsList.BoardsTOCItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DvachAPI {

    @GET("/makaba/mobile.fcgi?task=get_boards")
    Call<Map<String, List<BoardsTOCItem>>> getBoardsList();

    @GET("/{board_name}/catalog.json")
    Call<Board> getBoard(@Path("board_name") String board_name);

}
