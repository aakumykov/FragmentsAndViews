package aakumykov.ru.fragmentsandviews.threads_list;

import android.os.Bundle;

import aakumykov.ru.fragmentsandviews.BaseView;
import aakumykov.ru.fragmentsandviews.R;

public class ThreadsList_View extends BaseView implements ThreadsList_Fragment.iInteractionListener {

    public static final String BOARD_NAME = "boardName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_activity);
        setPageTitle(R.string.THREADS_LIST_page_title);
//        processInputIntent();
    }

    @Override
    public void onListItemClicked(int position) {
        setPageTitle("Нажата строка "+position);
    }

    @Override
    public void onListItemLongClicked(int position) {
        setPageTitle("Выбрана строка "+position);
    }


//    private void processInputIntent() {
//        Intent intent = getIntent();
//        if (null == intent) {
//            // TODO: сообщениеоб ошибке или просто игнорить?
//        }
//
//        if (null != intent) {
//            String boardName = intent.getStringExtra(ThreadsList_View.BOARD_NAME);
//            if (null != boardName) {
//
//            }
//        }
//    }
}
