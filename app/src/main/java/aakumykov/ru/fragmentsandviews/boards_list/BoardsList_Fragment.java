package aakumykov.ru.fragmentsandviews.boards_list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import aakumykov.ru.fragmentsandviews.BaseFragment;
import aakumykov.ru.fragmentsandviews.R;
import aakumykov.ru.fragmentsandviews.interfaces.iDvachPagesInteraction;
import aakumykov.ru.fragmentsandviews.models.BoardsList.BoardsTOCItem;
import aakumykov.ru.fragmentsandviews.services.DvachService;
import aakumykov.ru.fragmentsandviews.services.iDvachService;
import aakumykov.ru.fragmentsandviews.utils.MyUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

public class BoardsList_Fragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener
{

//    @BindView(R.id.swipeRefresh) SwipeRefreshLayout swipeRefreshLayout;
//    private SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.listView) ListView listView;
    View headerView;
    TextView headerInfoView;

    public static final String TAG = "BoardsList_Fragment";
    private iDvachService dvachService;
    private BoardsList_Adapter listAdapter;
    private List<BoardsTOCItem> list;
    private boolean firstRun = true;

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
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        ButterKnife.bind(this, view);

//        getPage().disactivateUpButton();

        dvachService = DvachService.getInstance();
        list = new ArrayList<>();
        listAdapter = new BoardsList_Adapter(getContext(), R.layout.boards_list_item, list);
        listView.setAdapter(listAdapter);

//        headerView = getLayoutInflater().inflate(R.layout.boards_list_header, null);
//        headerInfoView = headerView.findViewById(R.id.headerInfoView);
//        listView.addHeaderView(headerView);

        setDefaultPageTitle();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (firstRun) {
            loadBoardsList();
            firstRun = false;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dvachPagesInteraction = null;
    }

    @Override
    public void onRefresh() {
        loadBoardsList();
    }

    @OnItemClick(R.id.listView)
    void onItemClicked(int position) {
        showToast(String.valueOf(position));

        BoardsTOCItem item = list.get(position);
        String boardId = item.getId();
        dvachPagesInteraction.showThreadsInBoard(boardId);
    }

    @OnItemLongClick(R.id.listView)
    boolean onItemLongClicked(int position) {
        BoardsTOCItem item = list.get(position);
        String boardName = item.getName();
        return true;
    }

    @Override
    public void onBringToFront() {
        setDefaultPageTitle();
        getPage().disactivateUpButton();
    }

    @Override
    protected void setDefaultPageTitle() {
        getPage().setPageTitle(R.string.BOARDS_LIST_page_title);
    }



    private void loadBoardsList() {
        showProgressMessage(R.string.BOARDS_LIST_refreshing_boards_list);
//        showLoadingIndicator();
        dvachService.getBoardsList(new iDvachService.TOCReadCallbacks() {
            @Override
            public void onTOCReadSuccess(Map<String, List<BoardsTOCItem>> tocMap) {
                hideProgressMessage();
//                hideLoadingIndicator();
                displayBoardsList(tocMap);
            }

            @Override
            public void onTOCReadFail(String errorMsg) {
                showErrorMsg(R.string.BOARDS_LIST_error_loading_boards_list, errorMsg);
            }
        });
    }

    private void displayBoardsList(Map<String, List<BoardsTOCItem>> tocMap) {
        List<BoardsTOCItem> systemCategories = new ArrayList<>();
        List<BoardsTOCItem> userCategories = new ArrayList<>();

        listAdapter.clear();

        for (Map.Entry entry : tocMap.entrySet()) {
            List<BoardsTOCItem> boardsInGroup = (List<BoardsTOCItem>) entry.getValue();

            for (int j=0; j < boardsInGroup.size(); j++) {
                BoardsTOCItem board = boardsInGroup.get(j);
                if (board.isUserCategory()) userCategories.add(board);
                else systemCategories.add(board);
            }

            list.addAll(systemCategories);
            listAdapter.notifyDataSetChanged();
//            headerInfoView.setText("Системных досок: "+systemCategories.size());

            list.addAll(userCategories);
            listAdapter.notifyDataSetChanged();
//            String headerText = headerInfoView.getText().toString();
//            headerInfoView.setText(headerText + "\n" + "Пользовательских досок: "+userCategories.size());

//            MyUtils.show(headerInfoView);
        }
    }

//    private void showLoadingIndicator() {
//        if (null != swipeRefreshLayout)
//            swipeRefreshLayout.setRefreshing(true);
//    }
//    private void hideLoadingIndicator() {
//        if (null != swipeRefreshLayout)
//            swipeRefreshLayout.setRefreshing(false);
//    }
}
