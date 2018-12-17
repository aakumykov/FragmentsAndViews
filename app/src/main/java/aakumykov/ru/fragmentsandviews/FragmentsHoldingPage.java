package aakumykov.ru.fragmentsandviews;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import aakumykov.ru.fragmentsandviews.boards_list.BoardsList_Fragment;
import aakumykov.ru.fragmentsandviews.interfaces.iDvachPagesInteraction;
import aakumykov.ru.fragmentsandviews.thread_show.ThreadShow_Fragment;
import aakumykov.ru.fragmentsandviews.threads_list.ThreadsList_Fragment;

public class FragmentsHoldingPage extends BaseView implements
        iDvachPagesInteraction,
        FragmentManager.OnBackStackChangedListener
{
    private FragmentManager fragmentManager;
    private boolean firstRun = true;


    // Системные методы
    @SuppressLint("CommitTransaction") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_page_activity);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firstRun) {
            firstRun = false;
            showBoardsOnDvach();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        fragmentManager.removeOnBackStackChangedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                processUpButton();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackStackChanged() {
        BaseFragment currentFragment = (BaseFragment) fragmentManager.findFragmentById(R.id.fragment_place);

        if (null != currentFragment)
            currentFragment.onBringToFront();
    }


    // Интерфейсные методы
    @Override
    public void showBoardsOnDvach() {
        BoardsList_Fragment boardsListFragment = new BoardsList_Fragment();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_place, boardsListFragment);
        fragmentTransaction.addToBackStack(BoardsList_Fragment.TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void showThreadsInBoard(String boardId) {
        Bundle arguments = new Bundle();
        arguments.putString(Constants.BOARD_ID, boardId);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ThreadsList_Fragment threadsListFragment = new ThreadsList_Fragment();
        threadsListFragment.setArguments(arguments);

        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_place);
        if (null != currentFragment)
            fragmentTransaction.hide(currentFragment);

        fragmentTransaction.add(R.id.fragment_place, threadsListFragment);
        fragmentTransaction.addToBackStack(BoardsList_Fragment.TAG);

//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);

        fragmentTransaction.commit();
    }

    @Override
    public void showCommentsInThread(String boardId, String threadId) {
        Bundle arguments = new Bundle();
        arguments.putString(Constants.BOARD_ID, boardId);
        arguments.putString(Constants.THREAD_ID, threadId);

        ThreadShow_Fragment threadShowFragment = new ThreadShow_Fragment();
        threadShowFragment.setArguments(arguments);

        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_place);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (null != currentFragment)
            fragmentTransaction.hide(currentFragment);
        fragmentTransaction.add(R.id.fragment_place, threadShowFragment);
        fragmentTransaction.addToBackStack(ThreadShow_Fragment.TAG);
        fragmentTransaction.commit();
    }


    // Внутренние методы
    private void processUpButton() {
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_place);

        if (currentFragment instanceof BoardsList_Fragment) {
            return;
        }
        else {
            onBackPressed();
        }
    }
}
