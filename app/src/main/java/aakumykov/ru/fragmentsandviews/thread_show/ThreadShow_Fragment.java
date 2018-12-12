package aakumykov.ru.fragmentsandviews.thread_show;

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
import aakumykov.ru.fragmentsandviews.R;
import aakumykov.ru.fragmentsandviews.models.Thread.OneThread;
import aakumykov.ru.fragmentsandviews.models.Thread.Post;
import aakumykov.ru.fragmentsandviews.models.Thread.Thread;
import aakumykov.ru.fragmentsandviews.services.DvachService;
import aakumykov.ru.fragmentsandviews.services.iDvachService;
import aakumykov.ru.fragmentsandviews.threads_list.ThreadsList_View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

public class ThreadShow_Fragment extends BaseFragment {

    public interface iInteractionListener {
        void onListItemClicked(int position);
        void onListItemLongClicked(int position);
        void setPageTitleFromFragment(String title);
    }

    @BindView(R.id.listView) ListView listView;
    private iDvachService dvachService;
    private ThreadShow_Adapter listAdapter;
    private List<Post> list;
    private iInteractionListener interactionListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        ButterKnife.bind(this, view);

        dvachService = DvachService.getInstance();
        list = new ArrayList<>();
        listAdapter = new ThreadShow_Adapter(getContext(), R.layout.thread_show_item, list);
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

    @OnItemClick(R.id.listView)
    void onItemClicked(int position) {
        interactionListener.onListItemClicked(position);
    }

    @OnItemLongClick(R.id.listView)
    boolean onItemLongClicked(int position) {
        interactionListener.onListItemLongClicked(position);
        return true;
    }

    public void processInputIntent(@Nullable Intent intent) {
        if (null != intent) {

            // TODO: перенести в Константы?
            String threadNum = intent.getStringExtra(ThreadShow_View.THREAD_NUM);
            String boardName = intent.getStringExtra(ThreadsList_View.BOARD_NAME);

            if (null != boardName && null != threadNum) {
                loadThread(boardName, threadNum);
            }
        }
    }

    private void loadThread(String boardName, String threadNum) {
        showProgressMessage(R.string.THREAD_SHOW_loading_thread);

        dvachService.getThread(boardName, threadNum, new iDvachService.ThreadReadCallbacks() {
            @Override
            public void onThreadReadSuccess(OneThread oneThread) {
                interactionListener.setPageTitleFromFragment(oneThread.getTitle());
                hideProgressMessage();
                displayThread(oneThread);
            }

            @Override
            public void onThreadReadFail(String errorMsg) {
                showErrorMsg(R.string.THREAD_SHOW_error_loading_thread, errorMsg);
            }
        });
    }

    private void displayThread(OneThread oneThread) {
        List<Thread> threadList = oneThread.getThreads();
        if (threadList.size() > 0) {
            Thread thread = threadList.get(0);
            list.addAll(thread.getPosts());
            listAdapter.notifyDataSetChanged();
        }
    }
}
