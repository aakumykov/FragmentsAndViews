
package aakumykov.ru.fragmentsandviews.models.Thread;

import com.google.gson.annotations.Expose;

public class Top {

    @Expose
    private String board;
    @Expose
    private String info;
    @Expose
    private String name;

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
