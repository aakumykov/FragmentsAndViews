package aakumykov.ru.fragmentsandviews.threads_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import aakumykov.ru.fragmentsandviews.BaseView;
import aakumykov.ru.fragmentsandviews.R;
import aakumykov.ru.fragmentsandviews.thread_show.ThreadShow_View;

public class ThreadsList_View extends BaseView implements ThreadsList_Fragment.iInteractionListener {

    public static final String BOARD_NAME = "boardId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.threads_list_view);
        setPageTitle(R.string.THREADS_LIST_page_title);
        activateUpButton();

        FragmentManager fragmentManager = getSupportFragmentManager();

        ThreadsList_Fragment threadsListFragment = new ThreadsList_Fragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("intent", getIntent());
        threadsListFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.threads_list_fragment, threadsListFragment);
        fragmentTransaction.commit();
    }

    // TODO: архитектурный косяк: класс не знает свой boardName
    @Override
    public void onListItemClicked(String boardName, String threadNum) {
        Intent intent = new Intent(this, ThreadShow_View.class);
        intent.putExtra(ThreadShow_View.THREAD_NUM, threadNum);
        intent.putExtra(ThreadsList_View.BOARD_NAME, boardName);
        startActivity(intent);
    }

    @Override
    public void onListItemLongClicked(String boardName, String threadNum) {
        setPageTitle("Выбран тред "+boardName+"/"+threadNum);
    }

    @Override
    public void setPageTitleFromFragment(String boardName) {
        String title = getResources().getString(R.string.THREADS_LIST_page_title_extended, boardName);
        setPageTitle(title);
    }
}
