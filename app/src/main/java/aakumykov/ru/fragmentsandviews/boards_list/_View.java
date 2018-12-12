package aakumykov.ru.fragmentsandviews.boards_list;

import android.content.Intent;
import android.os.Bundle;

import aakumykov.ru.fragmentsandviews.BaseView;
import aakumykov.ru.fragmentsandviews.R;

public class _View extends BaseView implements
        BoardsList_Fragment.iInteractionListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boards_list_view);
        setPageTitle(R.string.BOARDS_LIST_page_title);
    }

    @Override
    public void onListItemClicked(String boardId) {
        Intent intent = new Intent(this, aakumykov.ru.fragmentsandviews.threads_list._View.class);
        intent.putExtra(aakumykov.ru.fragmentsandviews.threads_list._View.BOARD_ID, boardId);
        startActivity(intent);
    }

    @Override
    public void onListItemLongClicked(String boardName) {

    }
}
