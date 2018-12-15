package aakumykov.ru.fragmentsandviews;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import aakumykov.ru.fragmentsandviews.TEMPLATE.List_Fragment;
import aakumykov.ru.fragmentsandviews.boards_list.BoardsList_Fragment;
import aakumykov.ru.fragmentsandviews.interfaces.iDvachPagesInteraction;
import aakumykov.ru.fragmentsandviews.threads_list.ThreadsList_Fragment;

public class FragmentsHoldingPage extends BaseView implements
        iDvachPagesInteraction
{
    private FragmentManager fragmentManager;
    private boolean firstRun = true;

    @SuppressLint("CommitTransaction")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_page_activity);
        fragmentManager = getSupportFragmentManager();
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
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_EXIT_MASK);
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        fragmentTransaction.commit();
    }

    @Override
    public void showCommentsInThread(String boardId, String threadId) {

    }
}
