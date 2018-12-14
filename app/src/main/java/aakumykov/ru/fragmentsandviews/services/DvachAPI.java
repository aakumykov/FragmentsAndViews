package aakumykov.ru.fragmentsandviews.services;

import java.util.List;
import java.util.Map;

import aakumykov.ru.fragmentsandviews.models.Board.Board;
import aakumykov.ru.fragmentsandviews.models.BoardsList.BoardsTOCItem;
import aakumykov.ru.fragmentsandviews.models.Thread.OneThread;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DvachAPI {

    @GET("/makaba/mobile.fcgi?task=get_boards")
    Call<Map<String, List<BoardsTOCItem>>> getBoardsList();

    @GET("/{board_name}/catalog.json")
    Call<Board> getBoard(@Path("board_name") String board_name);

    @GET("/{board_name}/res/{thread_num}.json")
    Call<OneThread> getThread(@Path("board_name") String board_name, @Path("thread_num") String thread_num);
}
