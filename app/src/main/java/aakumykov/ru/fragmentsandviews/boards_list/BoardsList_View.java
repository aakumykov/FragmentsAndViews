package aakumykov.ru.fragmentsandviews.boards_list;

import android.content.Intent;
import android.os.Bundle;

import aakumykov.ru.fragmentsandviews.BaseView;
import aakumykov.ru.fragmentsandviews.R;
import aakumykov.ru.fragmentsandviews.threads_list.ThreadsList_View;

public class BoardsList_View extends BaseView
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boards_list_view);
        setPageTitle(R.string.BOARDS_LIST_page_title);
    }
}
