package aakumykov.ru.fragmentsandviews.thread_show;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    private TextToSpeech textToSpeech;


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

        dvachService = DvachService.getInstance();

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
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof iDvachPagesInteraction) {
            dvachPagesInteraction = (iDvachPagesInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement iDvachPagesInteraction");
        }

        initTTS();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dvachPagesInteraction = null;

        shutdownTTS();
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionReadWithVoice:
                readThreadWithVoice();
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
        textToSpeech.speak("Загружаю тред", TextToSpeech.QUEUE_FLUSH, null);

        dvachService.getThread(boardId, threadId, new iDvachService.ThreadReadCallbacks() {
            @Override
            public void onThreadReadSuccess(OneThread oneThread) {
                hideProgressMessage();
                displayThread(oneThread);
            }

            @Override
            public void onThreadMissing() {
                showErrorMsg(R.string.THREAD_SHOW_thread_missing);
                textToSpeech.speak("Тред не найден", TextToSpeech.QUEUE_FLUSH, null);
            }

            @Override
            public void onThreadReadFail(String errorMsg) {
                showErrorMsg(R.string.THREAD_SHOW_error_loading_thread, errorMsg);
                textToSpeech.speak("Ошибка загрузки треда", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    private void displayThread(OneThread oneThread) {

        getPage().setPageTitle(oneThread.getTitle());

        List<Thread> threadList = oneThread.getThreads();

        if (threadList.size() > 0) {
            Thread thread = threadList.get(0);

            String title = oneThread.getTitle();
            textToSpeech.speak(title, TextToSpeech.QUEUE_FLUSH, null);

            postsList.addAll(thread.getPosts());
            postsListAdapter.notifyDataSetChanged();
        }
    }

    private void initTTS() {
        textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    Locale locale = new Locale("ru");

                    int result = textToSpeech.setLanguage(locale);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e(TAG, "Извините, этот язык не поддерживается");
                    }

                } else {
                    Log.e("TTS", "Ошибка первичной настройки движка TTS.");
                }
            }
        });
    }

    private void shutdownTTS() {
        textToSpeech.stop();
        textToSpeech.shutdown();
    }

    private void readThreadWithVoice() {
//        String fullThreadText = "";

        for (int i = 0; i< postsList.size(); i++) {

            Post post = postsList.get(i);

            String comment = DvachUtils.preProcessComment(post.getComment());
//            fullThreadText += comment;
//            fullThreadText += "\n\n";

            textToSpeech.speak(comment, TextToSpeech.QUEUE_ADD, null);
        }

//        textToSpeech.speak(fullThreadText, TextToSpeech.QUEUE_ADD, null);
    }
}
