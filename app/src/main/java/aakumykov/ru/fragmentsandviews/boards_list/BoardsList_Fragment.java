package aakumykov.ru.fragmentsandviews.boards_list;


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
import java.util.Map;

import aakumykov.ru.fragmentsandviews.BaseFragment;
import aakumykov.ru.fragmentsandviews.R;
import aakumykov.ru.fragmentsandviews.models.BoardsList.BoardsTOCItem;
import aakumykov.ru.fragmentsandviews.services.DvachService;
import aakumykov.ru.fragmentsandviews.services.iDvachService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

public class BoardsList_Fragment extends BaseFragment {

    public interface iInteractionListener {
        void onListItemClicked(String boardName);
        void onListItemLongClicked(String boardName);
    }

    @BindView(R.id.listView) ListView listView;

    private iDvachService dvachService;
    private BoardsList_Adapter listAdapter;
    private List<BoardsTOCItem> list;
    private iInteractionListener interactionListener;
    private boolean firstRun = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        ButterKnife.bind(this, view);

        dvachService = DvachService.getInstance();
        list = new ArrayList<>();
        listAdapter = new BoardsList_Adapter(getContext(), R.layout.boards_list_item, list);
        listView.setAdapter(listAdapter);

        return view;
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

    @Override
    public void onStart() {
        super.onStart();
        if (firstRun) {
            loadBoardsList();
            firstRun = false;
        }
    }

    @OnItemClick(R.id.listView)
    void onItemClicked(int position) {
        BoardsTOCItem item = list.get(position);
        String boardName = item.getName();
        interactionListener.onListItemClicked(boardName);
    }

    @OnItemLongClick(R.id.listView)
    boolean onItemLongClicked(int position) {
        BoardsTOCItem item = list.get(position);
        String boardName = item.getName();
        interactionListener.onListItemLongClicked(boardName);
        return true;
    }


    private void loadBoardsList() {
        showProgressMessage(R.string.BOARDS_LIST_loading_boards_list);

        dvachService.getBoardsList(new iDvachService.TOCReadCallbacks() {
            @Override
            public void onTOCReadSuccess(Map<String, List<BoardsTOCItem>> tocMap) {
                hideProgressMessage();
                displayBoardsList(tocMap);
            }

            @Override
            public void onTOCReadFail(String errorMsg) {
                showErrorMsg(R.string.BOARDS_LIST_error_loading_boards_list, errorMsg);
            }
        });
    }

    private void displayBoardsList(Map<String, List<BoardsTOCItem>> tocMap) {
        for (Map.Entry entry : tocMap.entrySet()) {
            String group = entry.getKey().toString();
            List<BoardsTOCItem> boardsInGroup = (List<BoardsTOCItem>) entry.getValue();
            list.addAll(boardsInGroup);
            listAdapter.notifyDataSetChanged();
        }
    }
}
