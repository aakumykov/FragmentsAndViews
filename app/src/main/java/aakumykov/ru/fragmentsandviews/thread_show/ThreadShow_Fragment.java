package aakumykov.ru.fragmentsandviews.thread_show;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
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

import aakumykov.ru.fragmentsandviews.BaseFragment;
import aakumykov.ru.fragmentsandviews.Constants;
import aakumykov.ru.fragmentsandviews.R;
import aakumykov.ru.fragmentsandviews.interfaces.iDvachPagesInteraction;
import aakumykov.ru.fragmentsandviews.models.Thread.OneThread;
import aakumykov.ru.fragmentsandviews.models.Thread.Post;
import aakumykov.ru.fragmentsandviews.models.Thread.Thread;
import aakumykov.ru.fragmentsandviews.services.DvachService;
import aakumykov.ru.fragmentsandviews.services.iDvachService;
import aakumykov.ru.fragmentsandviews.utils.DvachUtils;
import aakumykov.ru.fragmentsandviews.utils.TTSReader;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class ThreadShow_Fragment extends BaseFragment {

    @BindView(R.id.listView) ListView listView;

    public static final String TAG = "ThreadShow_Fragment";

    private Menu menu;

    private iDvachService dvachService;
    private PostsList_Adapter postsListAdapter;
    private List<Post> postsList;

    private iDvachPagesInteraction dvachPagesInteraction;
    private TTSReader ttsReader;
    private Bundle ttsReaderState;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        ButterKnife.bind(this, view);

        getPage().activateUpButton();

        postsList = new ArrayList<>();
        postsListAdapter = new PostsList_Adapter(getContext(), R.layout.thread_show_item, postsList);
        listView.setAdapter(postsListAdapter);
        listView.setClickable(false);

        dvachService = DvachService.getInstance();

        ttsReader = new TTSReader(getContext(), new TTSReader.ReadingCallbacks() {
            @Override
            public void onReadingStart() {
                setSpeakIconMode(SpeakIconMode.SPEAK_ICON_MODE_PAUSE);
            }

            @Override
            public void onReadingPause() {
                setSpeakIconMode(SpeakIconMode.SPEAK_ICON_MODE_PLAY);
            }

            @Override
            public void onReadingStop() {
                setSpeakIconMode(SpeakIconMode.SPEAK_ICON_MODE_PLAY);
            }

            @Override
            public void onReadingError() {
                setSpeakIconMode(SpeakIconMode.SPEAK_ICON_MODE_ERROR);
            }
        });

        ttsReader.init();

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

//        if (null != savedInstanceState) {
//            Bundle ttsState = savedInstanceState.getBundle("TTS_STATE");
//            if (null != ttsState)
//                ttsReader.setState(ttsState);
//        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        Bundle ttsState = ttsReader.getState();
//        outState.putBundle("TTS_STATE", ttsState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dvachPagesInteraction = null;

        if (null != ttsReader) {
            ttsReader.shutdown();
            ttsReader = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        ttsReaderState = ttsReader.getState();
//        ttsReader.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
//        ttsReader.setState(ttsReaderState);
    }

    @OnItemClick(R.id.listView)
    void onItemClicked(int position) {
        Post post = postsList.get(position);
        String postText = DvachUtils.preProcessComment(post.getComment());

    }
//
//    @OnItemLongClick(R.id.listView)
//    boolean onItemLongClicked(int position) {
//        return true;
//    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.thread_show_menu, menu);
        this.menu = menu;
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
            }
            else
            {
                ttsReader.resume();
            }
        }
        else
        {
            ArrayList<String> postsToRead = getPostsToRead();
            ttsReader.setText(postsToRead);
            ttsReader.start();
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


    private enum SpeakIconMode {
        SPEAK_ICON_MODE_PLAY,
        SPEAK_ICON_MODE_PAUSE,
        SPEAK_ICON_MODE_ERROR
    }

    private void setSpeakIconMode(SpeakIconMode mode) {
        switch (mode) {
            case SPEAK_ICON_MODE_PLAY:
                setSpeakIcon(R.drawable.ic_play);
                break;

            case SPEAK_ICON_MODE_PAUSE:
                setSpeakIcon(R.drawable.ic_pause);
                break;

            case SPEAK_ICON_MODE_ERROR:
                showToast(R.string.MESSAGE_not_implemented_yet);
                break;
        }
    }

    private void setSpeakIcon(int iconResourceId) {
        if (null != menu) {
            MenuItem menuItem = menu.findItem(R.id.actionSpeak);
            Drawable newIcon = getResources().getDrawable(iconResourceId);
            menuItem.setIcon(newIcon);

            Activity activity = getActivity();
            if (null != activity) activity.invalidateOptionsMenu();
        }
    }
}
