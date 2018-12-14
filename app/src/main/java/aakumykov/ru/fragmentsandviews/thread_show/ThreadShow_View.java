package aakumykov.ru.fragmentsandviews.thread_show;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import aakumykov.ru.fragmentsandviews.BaseView;
import aakumykov.ru.fragmentsandviews.R;
import aakumykov.ru.fragmentsandviews.TEMPLATE.List_Fragment;
import aakumykov.ru.fragmentsandviews.threads_list.ThreadsList_Fragment;

public class ThreadShow_View extends BaseView implements ThreadShow_Fragment.iInteractionListener {

    public final static String THREAD_NUM = "threadNum";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thread_show_view);
        setPageTitle(R.string.THREAD_SHOW_page_title);
        activateUpButton();

        FragmentManager fragmentManager = getSupportFragmentManager();
        ThreadShow_Fragment threadShowFragment =
                (ThreadShow_Fragment) fragmentManager.findFragmentById(R.id.thread_show_fragment);

        if (null != threadShowFragment)
            threadShowFragment.processInputIntent(getIntent());
    }

    @Override
    public void onListItemClicked(int position) {
        setPageTitle("Нажата строка "+position);
    }

    @Override
    public void onListItemLongClicked(int position) {
        setPageTitle("Выбрана строка "+position);
    }

    @Override
    public void setPageTitleFromFragment(String threadName) {
//        String title = getResources().getString(R.string.THREAD_SHOW_page_title_extended, threadName);
//        setPageTitle(title);
        setPageTitle(threadName);
    }
}
