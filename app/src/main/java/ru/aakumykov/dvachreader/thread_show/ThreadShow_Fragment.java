package ru.aakumykov.dvachreader.thread_show;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ru.aakumykov.dvachreader.BaseFragment;
import ru.aakumykov.dvachreader.Constants;
import ru.aakumykov.dvachreader.R;
import ru.aakumykov.dvachreader.interfaces.iDvachPagesInteraction;
import ru.aakumykov.dvachreader.models.Thread.OneThread;
import ru.aakumykov.dvachreader.models.Thread.Post;
import ru.aakumykov.dvachreader.models.Thread.Thread;
import ru.aakumykov.dvachreader.services.DvachService;
import ru.aakumykov.dvachreader.services.iDvachService;
import ru.aakumykov.dvachreader.utils.DvachUtils;
import ru.aakumykov.dvachreader.utils.TTSReader;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class ThreadShow_Fragment extends BaseFragment {

    @BindView(R.id.listView) ListView listView;

    public static final String TAG = "ThreadShow_Fragment";

    private iDvachService dvachService;
    private PostsList_Adapter postsListAdapter;
    private List<Post> postsList;

    private iDvachPagesInteraction dvachPagesInteraction;
    private TTSReader ttsReader;
    private int currentCommentNum = 0;


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
        View view = inflater.inflate(R.layout.thread_show_fragment, container, false);
        ButterKnife.bind(this, view);

        getPage().activateUpButton();

        setHasOptionsMenu(true);

        postsList = new ArrayList<>();
        postsListAdapter = new PostsList_Adapter(getContext(), R.layout.thread_show_item, postsList);
        listView.setAdapter(postsListAdapter);
        listView.setClickable(false);

        dvachService = DvachService.getInstance();

        ttsReader = new TTSReader(getContext(), new TTSReader.ReadingCallbacks() {
            @Override
            public void onReadingStart() {
                int commentNum = ttsReader.getCurrentParagraphNum();
                if (currentCommentNum != commentNum) {
                    listView.smoothScrollToPosition(commentNum);
                    currentCommentNum = commentNum;
                }

            }

            @Override
            public void onReadingPause() {
            }

            @Override
            public void onReadingStop() {
            }

            @Override
            public void onReadingError() {
            }
        });

        Bundle arguments = getArguments();
        if (null != arguments) {
            String boardId = arguments.getString(Constants.BOARD_ID, null);
            String threadId = arguments.getString(Constants.THREAD_ID, null);

            try {
                loadThread(boardId, threadId);
            } catch (Exception e) {
                showErrorMsg(R.string.THREAD_SHOW_error_loading_thread);
            }
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (null != ttsReader) {
//            ttsReader.resume();
//            invalidateOptionsMenu();
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
//        if (null != ttsReader) {
//            ttsReader.pause();
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dvachPagesInteraction = null;
        if (null != ttsReader) {
            ttsReader.shutdown();
            ttsReader = null;
            invalidateOptionsMenu();
        }
    }


    @OnItemClick(R.id.listView)
    void onItemClicked(int position) {
        Post post = postsList.get(position);
        String postText = DvachUtils.preProcessComment(post.getComment());

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.thread_show_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.actionSpeak);

        if (null != ttsReader && ttsReader.hasText()) {
            if (ttsReader.isActive()) {
                menuItem.setIcon(getResources().getDrawable(R.drawable.ic_pause));
            } else {
                menuItem.setIcon(getResources().getDrawable(R.drawable.ic_play));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionSpeak:
                toggleReadingVithVoice();
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }


    @Override
    public void onBringToFront() {
        setDefaultPageTitle();
        getPage().activateUpButton();
    }

    @Override
    protected void setDefaultPageTitle() {
        getPage().setPageTitle(R.string.THREAD_SHOW_page_title);
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
                ttsReader.speak("Тред не найден");
            }

            @Override
            public void onThreadReadFail(String errorMsg) {
                showErrorMsg(R.string.THREAD_SHOW_error_loading_thread, errorMsg);
                ttsReader.speak("Ошибка загрузки треда");
            }
        });
    }

    private void displayThread(OneThread oneThread) {

        getPage().setPageTitle(oneThread.getTitle());

        List<Thread> threadList = oneThread.getThreads();

        if (threadList.size() > 0) {
            Thread thread = threadList.get(0);

            String title = oneThread.getTitle();

            postsList.addAll(thread.getPosts());
            postsListAdapter.notifyDataSetChanged();
        }
    }

    private void toggleReadingVithVoice() {

        if (ttsReader.hasText())
        {
            if (ttsReader.isActive())
            {
                ttsReader.pause();
                invalidateOptionsMenu();
            }
            else
            {
                ttsReader.resume();
                invalidateOptionsMenu();
            }
        }
        else
        {
            ArrayList<String> postsToRead = getPostsToRead();
            ttsReader.setText(postsToRead);
            ttsReader.start();
            invalidateOptionsMenu();
        }
    }

    private ArrayList<String> getPostsToRead() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < postsList.size(); i++) {
            Post post = postsList.get(i);
            String comment = DvachUtils.preProcessComment(post.getComment());
            list.add(comment);
        }
        return list;
    }

    private void invalidateOptionsMenu() {
        Activity activity = getActivity();
        if (null != activity) activity.invalidateOptionsMenu();
    }
}
