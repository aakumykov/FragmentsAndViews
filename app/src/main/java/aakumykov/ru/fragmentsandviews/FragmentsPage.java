package aakumykov.ru.fragmentsandviews;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import aakumykov.ru.fragmentsandviews.TEMPLATE.List_Fragment;
import aakumykov.ru.fragmentsandviews.boards_list.BoardsList_Fragment;
import aakumykov.ru.fragmentsandviews.interfaces.iDvachPagesInteraction;
import aakumykov.ru.fragmentsandviews.threads_list.ThreadsList_Fragment;

public class FragmentsPage extends BaseView implements
        iDvachPagesInteraction
{
    private boolean firstRun = true;

    @SuppressLint("CommitTransaction")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_page_activity);
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

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_place, boardsListFragment);
//        fragmentTransaction.addToBackStack(BoardsList_Fragment.TAG);
        fragmentTransaction.commit();
    }

    @Override
    public void showThreadsInBoard(String boardId) {
        Bundle arguments = new Bundle();
        arguments.putString(Constants.BOARD_ID, boardId);

        ThreadsList_Fragment threadsListFragment = new ThreadsList_Fragment();
        threadsListFragment.setArguments(arguments);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(BoardsList_Fragment.TAG);
        fragmentTransaction.add(R.id.fragment_place, threadsListFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void showCommentsInThread(String boardId, String threadId) {

    }
}
