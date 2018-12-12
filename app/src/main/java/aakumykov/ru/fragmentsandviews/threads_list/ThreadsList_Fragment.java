package aakumykov.ru.fragmentsandviews.threads_list;

import android.content.Context;
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
        void onListItemClicked(int position);
        void onListItemLongClicked(int position);
    }

    @BindView(R.id.listView) ListView listView;

    private iDvachService dvachService;
    private ThreadsList_Adapter listAdapter;
    private List<Thread> list;
    private iInteractionListener interactionListener;
    private boolean firstRun = true;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        ButterKnife.bind(this, view);

        dvachService = DvachService.getInstance();
        list = new ArrayList<>();
        listAdapter = new ThreadsList_Adapter(getContext(), R.layout.threads_list_item, list);
        listView.setAdapter(listAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (firstRun) {
//            loadThreadsList();
            firstRun = false;
        }
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
        interactionListener.onListItemClicked(position);
    }

    @OnItemLongClick(R.id.listView)
    boolean onItemLongClicked(int position) {
        interactionListener.onListItemLongClicked(position);
        return true;
    }


    private void loadThreadsList(String boardName) {
        showProgressMessage(R.string.THREADS_LIST_loading_threads_list);

        dvachService.getBoard(boardName, new iDvachService.BoardReadCallbacks() {
            @Override
            public void onBardReadSuccess(Board board) {
                hideProgressMessage();
                displayThreadsList(board);
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
