package aakumykov.ru.fragmentsandviews.threads_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import aakumykov.ru.fragmentsandviews.BaseFragment;
import aakumykov.ru.fragmentsandviews.Constants;
import aakumykov.ru.fragmentsandviews.R;
import aakumykov.ru.fragmentsandviews.interfaces.iDvachPagesInteraction;
import aakumykov.ru.fragmentsandviews.models.Board.Board;
import aakumykov.ru.fragmentsandviews.models.Board.Thread;
import aakumykov.ru.fragmentsandviews.services.DvachService;
import aakumykov.ru.fragmentsandviews.services.iDvachService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

public class ThreadsList_Fragment extends BaseFragment {


    @BindView(R.id.listView) ListView listView;

    public static final String TAG = "ThreadsList_Fragment";
    private iDvachService dvachService;
    private ThreadsList_Adapter listAdapter;
    private List<Thread> list;
    private boolean firstRun = true;
    private String boardId;
    private String currentTitle;

    private iDvachPagesInteraction dvachPagesInteraction;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof iDvachPagesInteraction) {
            dvachPagesInteraction = (iDvachPagesInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement iDvachPagesInteraction");
        }
    }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.threads_list_fragment, container, false);
        ButterKnife.bind(this, view);

        dvachService = DvachService.getInstance();
        list = new ArrayList<>();
        listAdapter = new ThreadsList_Adapter(getContext(), R.layout.threads_list_item, list);
        listView.setAdapter(listAdapter);

        Bundle arguments = getArguments();
        if (null != arguments)
            boardId = arguments.getString(Constants.BOARD_ID);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (firstRun) {
            loadThreadsList(boardId);
            firstRun = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dvachPagesInteraction = null;
    }

    @OnItemClick(R.id.listView)
    void onItemClicked(int position) {
        Thread thread = list.get(position);
        String threadId = thread.getNum();
        dvachPagesInteraction.showCommentsInThread(boardId, threadId);
    }

    @OnItemLongClick(R.id.listView)
    boolean onItemLongClicked(int position) {
        Thread thread = list.get(position);
        return true;
    }

    @Override
    public void onBringToFront() {
        getPage().activateUpButton();

        if (null != currentTitle) getPage().setPageTitle(currentTitle);
        else setDefaultPageTitle();
    }

    @Override
    protected void setDefaultPageTitle() {
        getPage().setPageTitle(R.string.THREADS_LIST_page_title);
    }


    private void loadThreadsList(String boardName) {
        showProgressMessage(R.string.THREADS_LIST_loading_threads_list);

        dvachService.getBoard(boardName, new iDvachService.BoardReadCallbacks() {
            @Override
            public void onBardReadSuccess(Board board) {
                hideProgressMessage();
                displayThreadsList(board);
                currentTitle = getResources().getString(R.string.THREADS_LIST_page_title_extended, board.getBoardName());
                getPage().setPageTitle(currentTitle);
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
