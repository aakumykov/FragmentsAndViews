package aakumykov.ru.fragmentsandviews.threads_list;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import aakumykov.ru.fragmentsandviews.BaseView;
import aakumykov.ru.fragmentsandviews.R;

public class ThreadsList_View extends BaseView implements ThreadsList_Fragment.iInteractionListener {

    public static final String BOARD_ID = "boardId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.threads_list_view);
        setPageTitle(R.string.THREADS_LIST_page_title);

        FragmentManager fragmentManager = getSupportFragmentManager();
        ThreadsList_Fragment threadsListFragment =
                (ThreadsList_Fragment) fragmentManager.findFragmentById(R.id.threads_list_fragment);

        if (null != threadsListFragment)
            threadsListFragment.processInputIntent(getIntent());
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
