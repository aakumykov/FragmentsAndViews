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
import aakumykov.ru.fragmentsandviews.Constants;
import aakumykov.ru.fragmentsandviews.R;
import aakumykov.ru.fragmentsandviews.interfaces.iDvachPagesInteraction;
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

    @BindView(R.id.listView) ListView listView;

    public static final String TAG = "ThreadShow_Fragment";
    private iDvachService dvachService;
    private ThreadShow_Adapter listAdapter;
    private List<Post> list;

    private iDvachPagesInteraction dvachPagesInteraction;


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

        Bundle arguments = getArguments();
        if (null != arguments) {
            String boardId = arguments.getString(Constants.BOARD_ID);
            String threadId = arguments.getString(Constants.THREAD_ID);

            try {
                loadThread(boardId, threadId);
            } catch (Exception e) {
                showErrorMsg(R.string.THREAD_SHOW_error_loading_thread);
            }
        }

        return view;
    }

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

    @Override
    public void onDetach() {
        super.onDetach();
        dvachPagesInteraction = null;
    }

    @OnItemClick(R.id.listView)
    void onItemClicked(int position) {
    }

    @OnItemLongClick(R.id.listView)
    boolean onItemLongClicked(int position) {
        return true;
    }

    private void loadThread(String boardId, String threadId) throws Exception {
        if (null == boardId && null == threadId)
            throw new IllegalArgumentException("Where is no boardId or threadId arguments.");

        showProgressMessage(R.string.THREAD_SHOW_loading_thread);

        dvachService.getThread(boardId, threadId, new iDvachService.ThreadReadCallbacks() {
            @Override
            public void onThreadReadSuccess(OneThread oneThread) {
                hideProgressMessage();
                displayThread(oneThread);
            }

            @Override
            public void onThreadMissing() {
                showErrorMsg(R.string.THREAD_SHOW_thread_missing);
            }

            @Override
            public void onThreadReadFail(String errorMsg) {
                showErrorMsg(R.string.THREAD_SHOW_error_loading_thread, errorMsg);
            }
        });
    }

    private void displayThread(OneThread oneThread) {

        getPage().setPageTitle(oneThread.getTitle());

        List<Thread> threadList = oneThread.getThreads();

        if (threadList.size() > 0) {
            Thread thread = threadList.get(0);
            list.addAll(thread.getPosts());
            listAdapter.notifyDataSetChanged();
        }
    }
}
