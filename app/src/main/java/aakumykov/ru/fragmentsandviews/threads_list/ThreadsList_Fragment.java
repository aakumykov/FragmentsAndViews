package aakumykov.ru.fragmentsandviews.threads_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import aakumykov.ru.fragmentsandviews.BaseFragment;
import aakumykov.ru.fragmentsandviews.R;
import aakumykov.ru.fragmentsandviews.models.Board.Board;
import aakumykov.ru.fragmentsandviews.models.Board.Thread;
import aakumykov.ru.fragmentsandviews.services.DvachService;
import aakumykov.ru.fragmentsandviews.services.iDvachService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

public class ThreadsList_Fragment extends BaseFragment {

    public interface iInteractionListener {
        void onListItemClicked(String boardName, String threadNum);
        void onListItemLongClicked(String boardName, String threadNum);
        void setPageTitleFromFragment(String title);
    }

    @BindView(R.id.listView) ListView listView;

    private static final String TAG = "ThreadsList_Fragment";
    private iDvachService dvachService;
    private ThreadsList_Adapter listAdapter;
    private List<Thread> list;
    private iInteractionListener interactionListener;
    private boolean firstRun = true;
    private String boardName;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.threads_list_fragment, container, false);
        ButterKnife.bind(this, view);

//        setRetainInstance(true);

        dvachService = DvachService.getInstance();
        list = new ArrayList<>();
        listAdapter = new ThreadsList_Adapter(getContext(), R.layout.threads_list_item, list);
        listView.setAdapter(listAdapter);

        Bundle arguments = getArguments();
        if (null != arguments) {
            Intent intent = (Intent) arguments.getParcelable("intnet");
            processInputIntent(intent);
        }

        Log.i(TAG, "onCreateView()");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView()");
    }

    @Override
    public void onStart() {
        super.onStart();
        if (firstRun) {
            loadThreadsList(boardName);
            firstRun = false;
        }
        Log.i(TAG, "onStart()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof iInteractionListener) {
            interactionListener = (iInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement iInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }


    @OnItemClick(R.id.listView)
    void onItemClicked(int position) {
        Thread thread = list.get(position);
        interactionListener.onListItemClicked(boardName, thread.getNum());
    }

    @OnItemLongClick(R.id.listView)
    boolean onItemLongClicked(int position) {
        Thread thread = list.get(position);
        interactionListener.onListItemLongClicked(boardName, thread.getNum());
        return true;
    }


    public void processInputIntent(@Nullable Intent intent) {

        if (null != intent) {
            String boardName = intent.getStringExtra(ThreadsList_View.BOARD_NAME);
            if (null != boardName) {
                this.boardName = boardName;
                loadThreadsList(boardName);
            }
        }
    }

    private void loadThreadsList(String boardName) {
        showProgressMessage(R.string.THREADS_LIST_loading_threads_list);

        dvachService.getBoard(boardName, new iDvachService.BoardReadCallbacks() {
            @Override
            public void onBardReadSuccess(Board board) {
                hideProgressMessage();
                displayThreadsList(board);
                interactionListener.setPageTitleFromFragment(board.getBoardName());
            }

            @Override
            public void onBoardReadFail(String errorMsg) {
                showErrorMsg(R.string.THREADS_LIST_error_loading_threads_list, errorMsg);
            }
        });
    }

    private void displayThreadsList(Board board) {

        List<Thread> threadsList = board.getThreads();

        for (int i=0; i<threadsList.size(); i++) {
            Thread thread = threadsList.get(i);
            list.add(thread);
            listAdapter.notifyDataSetChanged();
        }
    }

}
