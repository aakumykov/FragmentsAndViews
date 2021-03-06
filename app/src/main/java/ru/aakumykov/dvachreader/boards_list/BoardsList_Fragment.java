package ru.aakumykov.dvachreader.boards_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.aakumykov.dvachreader.BaseFragment;
import ru.aakumykov.dvachreader.interfaces.iDvachPagesInteraction;
import ru.aakumykov.dvachreader.models.BoardsList.BoardsTOCItem;
import ru.aakumykov.dvachreader.re_captcha.ReCaptchaTest;
import ru.aakumykov.dvachreader.services.DvachService;
import ru.aakumykov.dvachreader.services.iDvachService;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import ru.aakumykov.fragmentsandviews.R;

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

        setHasOptionsMenu(true);

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.boards_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionRecaptcha:
                Intent intent = new Intent(getContext(), ReCaptchaTest.class);
                startActivity(intent);
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
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
