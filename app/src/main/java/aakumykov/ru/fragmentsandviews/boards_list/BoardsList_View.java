package aakumykov.ru.fragmentsandviews.boards_list;

import android.os.Bundle;

import aakumykov.ru.fragmentsandviews.BaseView;
import aakumykov.ru.fragmentsandviews.R;

public class BoardsList_View extends BaseView implements BoardsList_Fragment.iInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boards_list_view);
        setPageTitle(R.string.BOARDS_LIST_page_title);
    }

    @Override
    public void onListItemClicked(int position) {
        setPageTitle("Нажата строка "+position);
    }

    @Override
    public void onListItemLongClicked(int position) {
        setPageTitle("Выбрана строка "+position);
    }
}
