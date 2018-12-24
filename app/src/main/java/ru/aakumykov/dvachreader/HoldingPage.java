package ru.aakumykov.dvachreader;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import ru.aakumykov.dvachreader.base_classes.Fragment;
import ru.aakumykov.dvachreader.base_classes.BaseView;
import ru.aakumykov.dvachreader.boards_list.BoardsList_Fragment;
import ru.aakumykov.dvachreader.interfaces.iDvachPagesInteraction;
import ru.aakumykov.dvachreader.thread_show.ThreadShow_Fragment;
import ru.aakumykov.dvachreader.threads_list.ThreadsList_Fragment;
import ru.aakumykov.fragmentsandviews.R;

public class HoldingPage extends BaseView implements
        iDvachPagesInteraction,
        FragmentManager.OnBackStackChangedListener,
        AdapterView.OnItemSelectedListener
{
    private FragmentManager fragmentManager;
    private boolean firstRun = true;
    private int selectedSpinnerItem = 0;

    // Системные методы
    @SuppressLint("CommitTransaction") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.holding_page_activity);

        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar)
            actionBar.setDisplayShowTitleEnabled(false);

        fragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onStart() {
        super.onStart();

        fragmentManager.addOnBackStackChangedListener(this);

        if (firstRun) {
            firstRun = false;
            showBoardsOnDvach(false);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        fragmentManager.removeOnBackStackChangedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.holding_page_menu, menu);

        MenuItem item = menu.findItem(R.id.actionSpinner);
        Spinner spinner = (Spinner) item.getActionView();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

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
        Fragment currentFragment = (Fragment) fragmentManager.findFragmentById(R.id.fragment_place);

        if (null != currentFragment) {
            currentFragment.onBringToFront();
        } else {
            Log.d("onBackStackChanged", "null == currentFragment");
        }
    }

    @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position != selectedSpinnerItem) {

            selectedSpinnerItem = position;

            switch (position) {
                case 0:
                    break;
                case 1:
                    showBoardsOnDvach(true);
                    break;
                case 2:
                    showThreadsInBoard("sex");
                    break;
            }
        }
    }

    @Override public void onNothingSelected(AdapterView<?> parent) {

    }


    // Интерфейсные методы
    @Override
    public void showBoardsOnDvach(boolean clearHistory) {
        BoardsList_Fragment boardsListFragment = new BoardsList_Fragment();

        if (clearHistory) {
            for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                fragmentManager.popBackStack();
            }
        }

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

        android.support.v4.app.Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_place);
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

        android.support.v4.app.Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_place);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (null != currentFragment)
            fragmentTransaction.hide(currentFragment);
        fragmentTransaction.add(R.id.fragment_place, threadShowFragment);
        fragmentTransaction.addToBackStack(ThreadShow_Fragment.TAG);
        fragmentTransaction.commit();
    }


    // Внутренние методы
    private void processUpButton() {
        android.support.v4.app.Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_place);

        if (currentFragment instanceof BoardsList_Fragment) {
            return;
        }
        else {
            onBackPressed();
        }
    }
}
